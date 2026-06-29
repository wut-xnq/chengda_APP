package com.jiaolong.cm.merch;

import com.jiaolong.cm.common.feign.annotation.EnableCmFeignClients;
import com.jiaolong.cm.common.security.annotation.EnableCmResourceServer;
import com.jiaolong.cm.common.swagger.annotation.EnableCmDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
* @author pig archetype
* <p>
* 项目启动类
*/
@EnableCmDoc("cm")
@EnableCmFeignClients
@EnableCmResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class MerchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchApplication.class, args);
    }

}
