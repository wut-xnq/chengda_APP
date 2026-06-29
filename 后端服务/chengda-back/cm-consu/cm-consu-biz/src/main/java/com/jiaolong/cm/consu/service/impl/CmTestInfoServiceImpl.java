package com.jiaolong.cm.consu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.ProductVerifiedDto;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.CmProduct;
import com.jiaolong.cm.consu.api.entity.CmTestInfo;
import com.jiaolong.cm.consu.api.entity.SysFile;
import com.jiaolong.cm.consu.api.param.TestInfoParam;
import com.jiaolong.cm.consu.convert.CmTestInfoConvert;
import com.jiaolong.cm.consu.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.consu.mapper.CmProductMapper;
import com.jiaolong.cm.consu.mapper.CmTestInfoMapper;
import com.jiaolong.cm.consu.mapper.SysFileMapper;
import com.jiaolong.cm.consu.service.CmTestInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc: 质检管理服务实现类
 * user: pan
 * date: 2024-08-21 10:46
 */
@Service
public class CmTestInfoServiceImpl extends ServiceImpl<CmTestInfoMapper, CmTestInfo> implements CmTestInfoService {

	@Autowired
	private CmTestInfoMapper testInfoMapper;
	@Autowired
	private CmProductMapper productMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private SysFileMapper sysFileMapper;

	@Override
	public R<IPage<TestInfoDto>> getPage(Page page, TestInfoParam param) {
		IPage<TestInfoDto> pageList = testInfoMapper.selectListPage(page, param);
		List<TestInfoDto> records = pageList.getRecords();
		for (TestInfoDto dto : records) {
			fillTestInfoDetails(dto);
		}
		return R.ok(pageList);
	}

	@Override
	public R<TestInfoDto> getDetails(Long id) {
		CmTestInfo cmTestInfo = testInfoMapper.selectById(id);
		TestInfoDto dto = CmTestInfoConvert.INSTANCE.convertToDto(cmTestInfo);
		fillTestInfoDetails(dto);
		return R.ok(dto);
	}

	@Override
	public R<TestInfoDto> getProductExamination(Long productId) {
		CmProduct product = productMapper.selectById(productId);
		if (product == null) {
			return R.failed(BusinessErrorMessage.NONE_PRODUCT);
		}
		Integer shelveArea = product.getShelveArea();
		if (shelveArea == null || shelveArea.intValue() != BusinessConstants.GB_AREA) {
			return R.failed(BusinessErrorMessage.NOT_GB_PRODUCT);
		}
		TestInfoDto dto = testInfoMapper.selectByProductId(productId);
		return R.ok(dto);
	}

	@Override
	public R<List<TestInfoDto>> getMerchantExamList(Long merchantId) {
		List<TestInfoDto> list = testInfoMapper.selectListByMerchantId(merchantId);
		return R.ok(list);
	}

	/**
	 * 填充质检信息中的商户名称、商品名称、关联文件等
	 *
	 * @param dto
	 */
	private void fillTestInfoDetails(TestInfoDto dto) {
		String merchantIds = dto.getTestMerchantIds();
		String productIds = dto.getTestProductIds();
		String relationFileIds = dto.getRelationFileIds();

		if (StringUtils.isNotBlank(merchantIds)) {
			String[] merchantIdList = merchantIds.split(StrUtil.COMMA);
			List<String> merchantNames = merchantInfoMapper.selectNameList(merchantIdList);
			dto.setMerchantNames(merchantNames);
		}
		if (StringUtils.isNotBlank(productIds)) {
			String[] productIdList = productIds.split(StrUtil.COMMA);
			List<ProductVerifiedDto> verifiedProducts = productMapper.selectVerifiedProductList(productIdList);
			for (ProductVerifiedDto product : verifiedProducts) {
				String productPics = product.getProductPics();
				if (StringUtils.isNotBlank(productPics) && productPics.contains(StrUtil.COMMA)) {
					String[] split = productPics.split(StrUtil.COMMA);
					product.setProductPics(split[0]);
				}
			}
			dto.setVerifiedProduct(verifiedProducts);
		}
		if (StringUtils.isNotBlank(relationFileIds)) {
			String[] fileListIds = relationFileIds.split(StrUtil.COMMA);
			List<SysFile> fileList = sysFileMapper.selectFileListByIds(fileListIds);
			dto.setFileList(fileList);
		}
	}
}
