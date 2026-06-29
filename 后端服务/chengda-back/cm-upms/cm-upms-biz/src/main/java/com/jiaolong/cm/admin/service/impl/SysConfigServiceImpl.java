package com.jiaolong.cm.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.mapper.SysConfigMapper;
import com.jiaolong.cm.admin.service.SysConfigService;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.SysConfiguration;
import com.jiaolong.cm.admin.api.vo.SettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 系统配置服务实现
 * user: pan
 * date: 2024-09-24 14:54
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfiguration> implements SysConfigService {

	@Autowired
	private SysConfigMapper configMapper;

	@Override
	public R<List<SysConfiguration>> getAllList() {
		List<SysConfiguration> list = configMapper.getList();
		return R.ok(list);
	}

	@Override
	public R editSetting(SettingVo vo) {
		Long id = vo.getId();
		SysConfiguration setting = configMapper.selectById(id);
		if (setting == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		Integer gbRate = vo.getGbRate();
		Integer zpRate = vo.getZpRate();
		Integer yhRate = vo.getYhRate();
		Integer molecule = vo.getMolecule();
		Integer denominator = vo.getDenominator();

		if (gbRate != null) {
			setting.setGbRate(gbRate);
		}
		if (zpRate != null) {
			setting.setZpRate(zpRate);
		}
		if (yhRate != null) {
			setting.setYhRate(yhRate);
		}
		if (molecule != null) {
			setting.setConversionMolecule(molecule);
		}
		if (denominator != null) {
			setting.setConversionDenominator(denominator);
		}

		setting.setUpdateTime(LocalDateTime.now());
		int result = configMapper.updateById(setting);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}
}
