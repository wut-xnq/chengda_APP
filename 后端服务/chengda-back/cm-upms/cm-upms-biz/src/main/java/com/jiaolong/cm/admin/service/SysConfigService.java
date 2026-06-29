package com.jiaolong.cm.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.merch.api.entity.SysConfiguration;
import com.jiaolong.cm.admin.api.vo.SettingVo;

import java.util.List;

/**
 * desc: 系统配置服务接口
 * user: pan
 * date: 2024-09-24 14:53
 */
public interface SysConfigService extends IService<SysConfiguration> {
	R<List<SysConfiguration>> getAllList();

	R editSetting(SettingVo vo);
}
