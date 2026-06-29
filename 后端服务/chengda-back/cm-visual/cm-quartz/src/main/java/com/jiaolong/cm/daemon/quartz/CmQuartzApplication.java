package com.jiaolong.cm.daemon.quartz;

import com.jiaolong.cm.common.feign.annotation.EnableCmFeignClients;
import com.jiaolong.cm.common.security.annotation.EnableCmResourceServer;
import com.jiaolong.cm.common.swagger.annotation.EnableCmDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author frwcloud
 * @date 2023-07-05
 */
@EnableCmDoc("job")
@EnableCmFeignClients
@EnableCmResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class CmQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmQuartzApplication.class, args);
	}

}
