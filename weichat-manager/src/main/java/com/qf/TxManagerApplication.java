package com.qf;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by 54110 on 2021/1/5.
 */
@SpringBootApplication
//标注为我们的tx-lcn管理服务端
@EnableTransactionManagerServer
public class TxManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxManagerApplication.class);
    }
}
