package com.jiaolong.cm.merch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.merch.api.dto.LabelDto;
import com.jiaolong.cm.merch.api.entity.CmCustomer;
import com.jiaolong.cm.merch.api.entity.CmLabel;
import com.jiaolong.cm.merch.api.vo.LabelSearchVo;
import com.jiaolong.cm.merch.api.vo.LabelVo;
import com.jiaolong.cm.merch.convert.CmLabelConvert;
import com.jiaolong.cm.merch.mapper.CmCustomerMapper;
import com.jiaolong.cm.merch.mapper.CmLabelMapper;
import com.jiaolong.cm.merch.service.CmLabelService;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc: 商户标签服务实现
 * user: pan
 * date: 2024-09-02 15:49
 */
@Service
public class CmLabelServiceImpl extends ServiceImpl<CmLabelMapper, CmLabel> implements CmLabelService {

	@Autowired
	private CmLabelMapper labelMapper;
	@Autowired
	private CmCustomerMapper cmCustomerMapper;

	@Override
	public R<IPage<LabelDto>> getPage(Page page, LabelSearchVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if(cmUser == null){
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long cmUserId = cmUser.getId();
		CmCustomer customer = cmCustomerMapper.selectById(cmUserId);
		if(customer != null && BusinessConstants.MERCHANT_USER == customer.getRoleId().intValue()){
			vo.setMerchantId(customer.getMerchantId());
		}

		IPage<LabelDto> dtoPage = labelMapper.getLabelPage(page, vo);
		return R.ok(dtoPage);
	}

	@Override
	public R<LabelDto> getDetail(Long id) {
		CmLabel label = labelMapper.selectById(id);
		LabelDto dto = CmLabelConvert.INSTANCE.convertToDto(label);
		return R.ok(dto);
	}

	@Override
	public R addLabel(LabelVo vo) {
		CmLabel cmLabel = CmLabelConvert.INSTANCE.convertYoEntity(new CmLabel(), vo);
		int result = labelMapper.insert(cmLabel);
		if (result > 0) {
			return R.ok(cmLabel);
		}
		return R.failed();
	}

	@Override
	public R editLabel(LabelVo vo) {
		Long id = vo.getId();
		CmLabel label = labelMapper.selectById(id);
		if (label == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		CmLabel cmLabel = CmLabelConvert.INSTANCE.convertYoEntity(label, vo);
		int result = labelMapper.updateById(cmLabel);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R deleteLabel(Long[] ids) {
		int result = labelMapper.deleteLabelByIds(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<List<LabelDto>> getMerchantLabelList(Long merchantId) {
		List<LabelDto> list = labelMapper.getMerchantLabelList(merchantId);
		return R.ok(list);
	}
}
