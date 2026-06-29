package com.jiaolong.cm.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.mapper.CmDelistingProductMapper;
import com.jiaolong.cm.admin.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.admin.mapper.CmProductMapper;
import com.jiaolong.cm.admin.mapper.CmProductSkuMapper;
import com.jiaolong.cm.admin.service.CmProductService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.ProductSkuDto;
import com.jiaolong.cm.merch.api.entity.CmDelistingProduct;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * desc:
 * user: pan
 * date: 2024-10-28 10:21
 */
@Service
public class CmProductServiceImpl extends ServiceImpl<CmProductMapper, CmProduct> implements CmProductService {

	@Autowired
	private CmProductMapper productMapper;
	@Autowired
	private CmProductSkuMapper productSkuMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private CmDelistingProductMapper delistingProductMapper;

	@Override
	@Transactional
	public R shelveProduct(ProductShelveVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		/* 先校验商品是否存在 */
		Long id = vo.getId();
		CmProduct product = productMapper.selectById(id);
		if (product == null) {
			return R.failed(BusinessErrorMessage.NONE_PRODUCT);
		}

		String shelveState = vo.getShelveState();
		/* 严禁上架商品 */
		if (BusinessConstants.PRODUCT_ON_SHELF.equals(shelveState)) {
			return R.failed(BusinessErrorMessage.FORBIDDED);
		}
		/* 下架商品，同时锁定商户账号，限制商户端后续商品上架，同时记录下架商品 */
		if (BusinessConstants.PRODUCT_OFF_SHELF.equals(shelveState)) {
			// 锁定商户
			Long merchantId = product.getMerchantId();
			int result = merchantInfoMapper.disabled(merchantId, BusinessConstants.STATE_DISABLE);
			if (result <= 0) {
				return R.failed();
			}

			// 新增下架商品记录
			String reason = vo.getReason();
			CmDelistingProduct delistingProduct = new CmDelistingProduct();
			delistingProduct.setProductId(product.getId());
			delistingProduct.setMerchantId(merchantId);
			delistingProduct.setOperator(cmUser.getId());
			delistingProduct.setReason(reason);
			result = delistingProductMapper.insert(delistingProduct);
			if (result <= 0) {
				return R.failed();
			}

			// 下架商品
			result = productMapper.shelveProduct(vo);
			if (result <= 0) {
				return R.failed();
			}
		}
		return R.ok();
	}
}
