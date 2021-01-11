package com.qf;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//分布式事务客户端的依赖
@EnableDistributedTransaction
public class WeichatShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeichatShopApplication.class);
    }
}
