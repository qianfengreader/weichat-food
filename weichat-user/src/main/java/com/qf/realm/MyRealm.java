package com.qf.realm;

import com.qf.dao.PermissionMapper;
import com.qf.dao.UserMapper;
import com.qf.pojo.Permission;
import com.qf.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

/**
 * Created by 54110 on 2020/12/7.
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PermissionMapper permissionMapper;

    //鉴权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String)principalCollection.getPrimaryPrincipal();
        //通过用户名查询楚用户的权限
        List<Permission> permissionByUserName = permissionMapper.findPermissionByUserName(username);

        HashSet<String> set = new HashSet<>();
        //去重将权限放置在set中
        for (Permission per:permissionByUserName
        ) {
            set.add(per.getPermissionName());
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(set);
        return simpleAuthorizationInfo;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        //使用前端的用户名查询用户密码
        User byUserName = userMapper.findByUsername(username);
        SimpleAuthenticationInfo simpleAuthenticationInfo =null;
        if (byUserName!=null){
            simpleAuthenticationInfo=  new SimpleAuthenticationInfo(username,byUserName.getPassword(),getName());
        }

        return simpleAuthenticationInfo;
    }
}
