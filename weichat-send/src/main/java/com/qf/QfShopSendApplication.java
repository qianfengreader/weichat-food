package com.qf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by 54110 on 2020/12/31.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class QfShopSendApplication {

    public static void main(String[] args) {
        SpringApplication.run(QfShopSendApplication.class);
    }
}
