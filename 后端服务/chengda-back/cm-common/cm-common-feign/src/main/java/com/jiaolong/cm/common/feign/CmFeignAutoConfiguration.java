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

package com.jiaolong.cm.common.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.jiaolong.cm.common.feign.core.CmFeignInnerRequestInterceptor;
import com.jiaolong.cm.common.feign.core.CmFeignRequestCloseInterceptor;
import com.jiaolong.cm.common.feign.sentinel.ext.CmSentinelFeign;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.CmFeignClientsRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * sentinel 配置
 *
 * @author lengleng
 * @date 2020-02-12
 */
@Configuration(proxyBeanMethods = false)
@Import(CmFeignClientsRegistrar.class)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class CmFeignAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return CmSentinelFeign.builder();
	}

	/**
	 * add http connection close header
	 * @return
	 */
	@Bean
	public CmFeignRequestCloseInterceptor cmFeignRequestCloseInterceptor() {
		return new CmFeignRequestCloseInterceptor();
	}

	/**
	 * add inner request header
	 * @return CmFeignInnerRequestInterceptor
	 */
	@Bean
	public CmFeignInnerRequestInterceptor cmFeignInnerRequestInterceptor() {
		return new CmFeignInnerRequestInterceptor();
	}

}
