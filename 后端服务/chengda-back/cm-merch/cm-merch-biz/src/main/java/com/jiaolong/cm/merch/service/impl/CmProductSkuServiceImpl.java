package com.jiaolong.cm.merch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.ProductSkuDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.entity.CmProductSku;
import com.jiaolong.cm.merch.api.vo.ProductSkuSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductSkuVo;
import com.jiaolong.cm.merch.convert.CmProductSkuConvert;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmProductMapper;
import com.jiaolong.cm.merch.mapper.CmProductSkuMapper;
import com.jiaolong.cm.merch.service.CmProductSkuService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * desc: 商品SKU服务实现
 * user: pan
 * date: 2024-09-01 16:02
 */
@Service
public class CmProductSkuServiceImpl extends ServiceImpl<CmProductSkuMapper, CmProductSku> implements CmProductSkuService {

	@Autowired
	private CmProductSkuMapper productSkuMapper;
	@Autowired
	private CmCustomerMapper cmCustomerMapper;
	@Autowired
	private CmProductMapper productMapper;

	@Override
	public R<IPage<ProductSkuDto>> getPage(Page page, ProductSkuSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = cmCustomerMapper.selectById(cmUserId);
		if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<ProductSkuDto> dtoPage = productSkuMapper.getDtoPage(page, vo);
		return R.ok(dtoPage);
	}

	@Override
	public R<ProductSkuDto> getDetail(Long id) {
		CmProductSku productSku = productSkuMapper.selectById(id);
		ProductSkuDto dto = CmProductSkuConvert.INSTANCE.convertToDto(productSku);
		return R.ok(dto);
	}

	@Override
	@Transactional
	public R addSku(ProductSkuVo vo) {
		Long productId = vo.getProductId();
		CmProductSku productSku = CmProductSkuConvert.INSTANCE.convertToEntity(new CmProductSku(), vo);
		productSku.setCreateTime(LocalDateTime.now());
		productSku.setDeleted(BusinessConstants.DELETED_NO);
		// 处理默认SKU
		int count = productSkuMapper.countSkuByProductId(productId);
		if (count == 0) {
			productSku.setIfDefault(BusinessConstants.YES);
		}

		String ifDefault = productSku.getIfDefault();
		if (BusinessConstants.YES.equals(ifDefault) && count > 0) {
			// 先将该产品其它 SKU 设定为“非默认”
			productSkuMapper.updateIfDefaultByProductId(productId);
		}

		/* 新增SKU */
		int result = productSkuMapper.insert(productSku);
		if (result <= 0) {
			return R.failed();
		}

		/* 绑定商品和SKU */
		CmProduct product = productMapper.selectById(productId);
		String productSkus = product.getProductSkus();
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(productSkus)) {
			sb.append(productSkus).append(StrUtil.COMMA);
		}
		String newProductSkus = sb.append(productSku.getId()).toString();
		result = productMapper.updateSkuStr(productId, newProductSkus);
		if (result <= 0) {
			return R.failed();
		}
		return R.ok(productSku);
	}

	@Override
	public R editSku(ProductSkuVo vo) {
		Long id = vo.getId();
		CmProductSku productSku = productSkuMapper.selectById(id);

		// 校验商品售卖状态
		CmProduct product = productMapper.selectById(productSku.getProductId());
		String shelveState = product.getShelveState();
		if (BusinessConstants.PRODUCT_ON_SHELF.equals(shelveState)) {
			return R.failed(BusinessErrorMessage.PRODUCT_ON_SHELF);
		}

		CmProductSku entity = CmProductSkuConvert.INSTANCE.convertToEntity(productSku, vo);
		entity.setUpdateTime(LocalDateTime.now());
		int result = productSkuMapper.updateById(productSku);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R deleteSku(Long[] ids) {
		int result = productSkuMapper.deleteSkuById(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}
}
