package com.jiaolong.cm.merch.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.*;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmMerchantInfo;
import com.jiaolong.cm.merch.api.entity.CmProduct;
import com.jiaolong.cm.merch.api.vo.ProductSearchVo;
import com.jiaolong.cm.merch.api.vo.ProductShelveVo;
import com.jiaolong.cm.merch.api.vo.ProductVo;
import com.jiaolong.cm.merch.convert.CmProductConvert;
import com.jiaolong.cm.merch.mapper.*;
import com.jiaolong.cm.merch.service.CmProductService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * desc: 商品服务实现
 * user: pan
 * date: 2024-09-05 18:26
 */
@Service
public class CmProductServiceImpl extends ServiceImpl<CmProductMapper, CmProduct> implements CmProductService {

	@Autowired
	private CmProductMapper productMapper;
	@Autowired
	private CmProductSkuMapper productSkuMapper;
	@Autowired
	private CmLabelMapper labelMapper;
	@Autowired
	private CmProductLabelMapper productLabelMapper;
	@Autowired
	private CmSpecMapper specMapper;
	@Autowired
	private CmProductSpecMapper productSpecMapper;
	@Autowired
	private CmCustomerMapper cmCustomerMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;

	@Override
	public R<IPage<ProductDto>> getPage(Page page, ProductSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = cmCustomerMapper.selectById(cmUserId);
		if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<ProductDto> dtoPage = productMapper.getPageList(page, vo);
		List<ProductDto> records = dtoPage.getRecords();
		for (ProductDto dto : records) {
			Long id = dto.getId();

			/* 商品SKU */
			List<ProductSkuDto> productSkuList = productSkuMapper.getSkuListByProductId(id);
			dto.setProductSkuList(productSkuList);

			/* 商品标签Label */
			List<LabelDto> labelList = labelMapper.getLabelListByProductId(id);
			dto.setProductLabelList(labelList);

			/* 商品规格 */
			List<SpecDto> specList = specMapper.getSpecListByProductId(id);
			dto.setProductSpecList(specList);
		}
		dtoPage.setRecords(records);
		return R.ok(dtoPage);
	}

	@Override
	public R<ProductDto> getDetail(Long id) {
		ProductDto dto = productMapper.getProductDetailById(id);
		if (dto == null) {
			return R.failed(BusinessErrorMessage.NONE_PRODUCT);
		}

		/* 商品SKU */
		List<ProductSkuDto> productSkuList = productSkuMapper.getSkuListByProductId(id);
		dto.setProductSkuList(productSkuList);

		/* 商品标签Label */
		List<LabelDto> labelList = labelMapper.getLabelListByProductId(id);
		dto.setProductLabelList(labelList);

		/* 商品规格 */
		List<SpecDto> specList = specMapper.getSpecListByProductId(id);
		dto.setProductSpecList(specList);
		return R.ok(dto);
	}

	@Override
	@Transactional
	public R addProduct(ProductVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long merchantId = vo.getMerchantId();
		if (merchantId == null) {
			Long cmUserId = cmUser.getId();
			CmCustomer customer = cmCustomerMapper.selectById(cmUserId);
			if (customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()) {
				vo.setMerchantId(customer.getMerchantId());
			}
		}

		/* 商品 */
		CmProduct product = CmProductConvert.INSTANCE.create(vo);
		product.setCreateTime(LocalDateTime.now());
		product.setShelveState(BusinessConstants.PRODUCT_OFF_SHELF);
		product.setDeleted(BusinessConstants.DELETED_NO);
		int result = productMapper.insert(product);
		if (result <= 0) {
			return R.failed();
		}

		/* 商品绑定标签 */
		Long productId = product.getId();
		String labels = product.getLabels();
		if (StringUtils.isNotBlank(labels)) {
			Set<Long> labelSet = Arrays.stream(labels.split(StrUtil.COMMA)).map(e -> Long.valueOf(e)).collect(Collectors.toSet());
			result = productLabelMapper.insertBatch(productId, labelSet);
			if (result <= 0) {
				return R.failed(BusinessErrorMessage.PRODUCT_LABEL_ERROR);
			}
		}

		/* 商品绑定规格 */
		String productSpecs = product.getProductSpecs();
		if (StringUtils.isNotBlank(productSpecs)) {
			Set<Long> specSet = Arrays.stream(productSpecs.split(StrUtil.COMMA)).map(e -> Long.valueOf(e)).collect(Collectors.toSet());
			result = productSpecMapper.insertBatch(productId, specSet);
			if (result <= 0) {
				return R.failed(BusinessErrorMessage.PRODUCT_SPEC_ERROR);
			}
		}
		return R.ok(product);
	}

	@Override
	@Transactional
	public R editProduct(ProductVo vo) {
		Long productId = vo.getId();
		CmProduct product0 = productMapper.selectById(productId);

		/* 商家中的商品禁止修改 */
		if (BusinessConstants.PRODUCT_ON_SHELF.equals(product0.getShelveState())) {
			return R.failed(BusinessErrorMessage.PRODUCT_ON_SHELF);
		}

		CmProduct product = CmProductConvert.INSTANCE.update(vo);
		int result = productMapper.updateById(product);
		if (result <= 0) {
			return R.failed();
		}

		/* 商品标签 */
		String labels = vo.getLabels();
		String labels0 = product0.getLabels();
		boolean f1 = StringUtils.isBlank(labels) && StringUtils.isBlank(labels0);
		boolean f2 = StringUtils.isNotBlank(labels) && StringUtils.isNotBlank(labels0) && labels.equals(labels0);
		if (!f1 || !f2) {
			// 删除商品原绑定数据后新增
			productLabelMapper.deleteByProductId(productId);
			if (StringUtils.isNotBlank(labels)) {
				Set<Long> labelSet = Arrays.stream(labels.split(StrUtil.COMMA)).map(e -> Long.valueOf(e)).collect(Collectors.toSet());
				result = productLabelMapper.insertBatch(productId, labelSet);
				if (result <= 0) {
					return R.failed(BusinessErrorMessage.PRODUCT_LABEL_ERROR);
				}
			}
		}

		/* 商品规格 */
		String productSpecs = vo.getProductSpecs();
		String productSpecs0 = product0.getProductSpecs();
		boolean f3 = StringUtils.isBlank(productSpecs) && StringUtils.isBlank(productSpecs0);
		boolean f4 = StringUtils.isNotBlank(productSpecs) && StringUtils.isNotBlank(productSpecs0) && productSpecs.equals(productSpecs0);
		if (!f3 || !f4) {
			// 删除商品原绑定数据后新增
			productSpecMapper.deleteByProductId(productId);
			if (StringUtils.isNotBlank(productSpecs)) {
				Set<Long> specSet = Arrays.stream(productSpecs.split(StrUtil.COMMA)).map(e -> Long.valueOf(e)).collect(Collectors.toSet());
				result = productSpecMapper.insertBatch(productId, specSet);
				if (result <= 0) {
					return R.failed(BusinessErrorMessage.PRODUCT_SPEC_ERROR);
				}
			}
		}
		return R.ok();
	}

	@Override
	public R shelveProduct(ProductShelveVo vo) {
		/* 先校验商品是否存在 */
		Long id = vo.getId();
		CmProduct product = productMapper.selectById(id);
		if (product == null) {
			return R.failed(BusinessErrorMessage.NONE_PRODUCT);
		}

		String shelveState = vo.getShelveState();
		if (BusinessConstants.PRODUCT_ON_SHELF.equals(shelveState)) {
			/* 上架前，先校验商户状态 */
			CmMerchantInfo merchantInfo = merchantInfoMapper.selectById(product.getMerchantId());
			if (BusinessConstants.STATE_DISABLE.equals(merchantInfo.getState())) {
				return R.failed(BusinessErrorMessage.MERCHANT_DISABLED);
			}

			/* 上架前，校验是否存在商品SKU */
			List<ProductSkuDto> productSkuList = productSkuMapper.getSkuListByProductId(id);
			if (CollectionUtils.isEmpty(productSkuList)) {
				return R.failed(BusinessErrorMessage.NONE_PRODUCT_SKUS);
			}
		}

		int result = productMapper.shelveProduct(vo);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R deleteProduct(Long[] ids) {
		int result = productMapper.deleteProduct(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<List<ProductListDto>> getMerchantProductList(Long merchantId) {
		List<ProductListDto> list = productMapper.getProductByMerchantId(merchantId);
		return R.ok(list);
	}
}
