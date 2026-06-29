package com.jiaolong.cm.consu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.consu.api.dto.ProductDto;
import com.jiaolong.cm.consu.api.dto.ProductLabelDto;
import com.jiaolong.cm.consu.api.dto.ProductSpecDto;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.*;
import com.jiaolong.cm.consu.api.param.ProductParam;
import com.jiaolong.cm.consu.mapper.*;
import com.jiaolong.cm.consu.service.CmProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * desc: 商品管理服务类
 * user: pan
 * date: 2024-08-20 15:29
 */
@Service
public class CmProductServiceImpl extends ServiceImpl<CmProductMapper, CmProduct> implements CmProductService {

	@Autowired
	private CmProductMapper productMapper;
	@Autowired
	private CmTestInfoMapper testInfoMapper;
	@Autowired
	private CmProductSkuMapper productSkuMapper;
	@Autowired
	private CmProductSpecMapper productSpecMapper;
	@Autowired
	private CmProductLabelMapper productLabelMapper;
	@Autowired
	private DictItemMapper dictItemMapper;
	@Autowired
	private CmProductStatisticsMapper cmProductStatisticsMapper;

	@Autowired
	private CmUserViewProductMapper userViewProductMapper;
	@Autowired
	private CmProductStatisticsMapper productStatisticsMapper;
	@Autowired
	private SysFileMapper sysFileMapper;


	@Override
	public R<IPage<ProductDto>> getPage(Page page, ProductParam param) {
		IPage<ProductDto> pageList = productMapper.selectListPage(page, param);
		// 填充 SKU、价位
		List<ProductDto> records = pageList.getRecords();
		for (ProductDto dto : records) {
			fillingProductSkuList(dto);
		}
		return R.ok(pageList);
	}

	@Override
	@Transactional
	public R<ProductDto> getDetails(Long id) {
		/* 更新用户浏览记录 */
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}
		Long userId = cmUser.getId();
		LocalDateTime time = LocalDateTime.now();
		CmProduct product = productMapper.selectById(id);
		CmUserViewProduct record = userViewProductMapper.getRecord(userId, id);
		if (record == null) {
			record = new CmUserViewProduct(userId, id, time);
			userViewProductMapper.insertRecord(record);
		} else {
			record.setViewTime(time);
			userViewProductMapper.updateViewTime(record);
		}

		/* 商品浏览量 +1 */
		CmProductStatisticsTrend statisticsTrend = productStatisticsMapper.getByProductId(id);
		if (statisticsTrend == null) {
			statisticsTrend = new CmProductStatisticsTrend();
			statisticsTrend.setProductId(id);
			statisticsTrend.setViewAmount(1);
			productStatisticsMapper.insert(statisticsTrend);
		} else {
			int viewAmount = statisticsTrend.getViewAmount();
			statisticsTrend.setViewAmount(viewAmount + 1);
			productStatisticsMapper.updateViewAmount(statisticsTrend);
		}

		/* 回显商品详情数据 */
		ProductDto dto = productMapper.getDetailsById(id);
		fillingProductStatistic(dto);
		fillingProductSkuList(dto);
		fillingProductSpecs(dto);
		fillingProductLabels(dto);
		fillingProductGuaratees(dto);
		fillingProductTestInfo(dto);
		return R.ok(dto);
	}

	/**
	 * 填充商品统计数据
	 *
	 * @param dto
	 */
	private void fillingProductStatistic(ProductDto dto) {
		Long id = dto.getId();
		CmProductStatisticsTrend statisticsTrend = cmProductStatisticsMapper.getByProductId(id);
		if (statisticsTrend != null) {
			dto.setOrderAmount(statisticsTrend.getOrderAmount());
			dto.setStatisticsTrend(statisticsTrend);
		}
	}

	/**
	 * 填充商品 SKU 列表
	 *
	 * @param dto
	 */
	private void fillingProductSkuList(ProductDto dto) {
		Long id = dto.getId();
		String productSkus = dto.getProductSkus();
		if (StringUtils.isNotEmpty(productSkus)) {
			LambdaQueryWrapper<CmProductSku> wrapper = new LambdaQueryWrapper();
			wrapper.eq(CmProductSku::getProductId, id)
					.eq(CmProductSku::getDeleted, "0")
					.eq(CmProductSku::getState, "1");
			List<CmProductSku> skuList = productSkuMapper.selectList(wrapper);
			if (!CollectionUtils.isEmpty(skuList)) {
				dto.setSkuList(skuList);
				// 填充商品价格
				List<CmProductSku> resultList = skuList.stream().sorted(Comparator.comparing(CmProductSku::getCurrentPrice)).collect(Collectors.toList());
				CmProductSku productSku = resultList.get(0);
				dto.setShowedSkuId(productSku.getId());
				dto.setCurrentPrice(String.valueOf(productSku.getCurrentPrice()));
				dto.setOriginPrice(String.valueOf(productSku.getOriginPrice()));
			}
		}
	}

	/**
	 * 填充商品规格
	 *
	 * @param dto
	 */
	private void fillingProductSpecs(ProductDto dto) {
		Long id = dto.getId();
		List<ProductSpecDto> specList = productSpecMapper.selectListByProductId(id);
		dto.setSpecList(specList);
	}

	/**
	 * 填充商品标签
	 *
	 * @param dto
	 */
	private void fillingProductLabels(ProductDto dto) {
		Long id = dto.getId();
		List<ProductLabelDto> labelList = productLabelMapper.selectListByProductId(id);
		dto.setLabelList(labelList);
	}

	/**
	 * 填充商品保障
	 *
	 * @param dto
	 */
	private void fillingProductGuaratees(ProductDto dto) {
		String productGuarantees = dto.getProductGuarantees();
		if (StringUtils.isNotEmpty(productGuarantees)) {
			LambdaQueryWrapper<SysDictItem> wrapper = new LambdaQueryWrapper<>();
			wrapper.in(SysDictItem::getId, productGuarantees.split(StrUtil.COMMA));
			List<SysDictItem> productGuaranteeDicList = dictItemMapper.selectList(wrapper);
			dto.setProductGuaranteeDicList(productGuaranteeDicList);
		}
	}

	/**
	 * 填充商品质检信息
	 *
	 * @param dto
	 */
	private void fillingProductTestInfo(ProductDto dto) {
		Long id = dto.getId();
		Integer shelveArea = dto.getShelveArea();
		if (shelveArea != null && shelveArea.intValue() == BusinessConstants.GB_AREA) {
			TestInfoDto info = testInfoMapper.selectByProductId(id);
			if (info != null) {
				dto.setAgencyName(info.getAgencyName());
				dto.setTestTime(info.getCreateTime());
				dto.setTestResult(info.getRemarks());
				String relationFileIds = info.getRelationFileIds();
				if(StringUtils.isNotBlank(relationFileIds)){
					String[] fileListIds = relationFileIds.split(StrUtil.COMMA);
					List<SysFile> fileList = sysFileMapper.selectFileListByIds(fileListIds);
					dto.setFileList(fileList);
				}
			}
		}
	}


}
