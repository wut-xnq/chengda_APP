/*
 * Copyright (c) 2020 cm4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiaolong.cm.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.jiaolong.cm.admin.api.dto.UserInfo;
import com.jiaolong.cm.admin.api.entity.SysUser;
import com.jiaolong.cm.admin.api.feign.RemoteCustomerService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.constant.CacheConstants;
import com.jiaolong.cm.common.core.constant.CommonConstants;
import com.jiaolong.cm.common.core.constant.SecurityConstants;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.core.util.RetOps;
import com.jiaolong.cm.common.core.util.WebUtils;
import com.jiaolong.cm.common.security.util.CustomerException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

import java.util.*;

/**
 * desc: 客户详细信息
 * user: pan
 * date: 2024-08-20 12:44
 */
@Slf4j
@RequiredArgsConstructor
public class CmCustomerDetailsServiceImpl implements CmUserDetailsService {

	private final RemoteCustomerService remoteCustomerService;

	/**
	 * 用户名密码登录
	 *
	 * @param username 实际上可能为用户手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		String inviteCode = WebUtils.getRequest().get().getParameter("inviteCode");
		R<UserInfo> result = remoteCustomerService.info(username, inviteCode, SecurityConstants.FROM_IN);
		UserInfo info = RetOps.of(result).getData().orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

		Set<String> dbAuthsSet = new HashSet<>();
		Long[] roles = info.getRoles();
		if (ArrayUtil.isNotEmpty(roles)) {
			// 校验角色
			if (roles[0].intValue() == BusinessConstants.MERCHANT_USER) {
				throw new CustomerException("当前账号为商户管理员用户。");
			}
			// 获取角色
			Arrays.stream(roles).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));
		}

		Collection<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthsSet.toArray(new String[0]));


		SysUser user = info.getSysUser();


		/* 构造security用户 */
		CmUser cmUser = new CmUser(user.getUserId(), user.getDeptId(), user.getUsername(),
				SecurityConstants.BCRYPT + user.getPassword(), user.getPhone(), true, true, true,
				StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), authorities);

		/* 扩展字段 */
		Map<String, Object> attributes = cmUser.getAttributes();
		String ifGiftScore = info.getIfGiftScore();
		String inviter = info.getInviter();
		if (ifGiftScore != null) {
			attributes.put("ifGiftScore", ifGiftScore);
		}
		if (inviter != null) {
			attributes.put("inviter", inviter);
		}
		return cmUser;
	}

	/**
	 * 是否支持此客户端校验
	 *
	 * @param clientId  目标客户端
	 * @param grantType
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.CUSTOMER.equals(clientId);
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
