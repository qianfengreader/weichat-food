package com.qf.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by 54110 on 2020/12/25.
 */
@Component
@RefreshScope
@Data
public class MyConfig {

    @Value("${weichat.securite}")
    private String dataBase;
}
