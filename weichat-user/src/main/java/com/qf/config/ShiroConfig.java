package com.qf.config;

import com.qf.realm.MyRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 54110 on 2020/12/7.
 */
@Configuration
public class ShiroConfig {



    //声明shiro的bean工厂。将核心进行设置
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //告诉shiro 访问什么接口需要什么权限
      /*  HashMap map = new HashMap<>();
        map.put("/findAll","perms[findAll]");
        map.put("/update","perms[update]");
        //将权限告诉shiro 拥有那个权限可以访问

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);*/
        //设置未登录情况下返回的页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //设置没有权限跳转的方法
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");
        return shiroFilterFactoryBean;
    }


    //shiro核心控制器，将自定义验证交给核心
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myRealm")MyRealm myRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        return defaultWebSecurityManager;
    }


    //声明定义好的Realm
    @Bean(name = "myRealm") //把当前声明出来的对象，交给了springioc
    public MyRealm myRealm(){
        return new MyRealm();
    }

    //开启shiro权限的注解模式

    @Bean
    public AuthorizationAttributeSourceAdvisor authAdvisor(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator deaultAdvisorProxy(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
