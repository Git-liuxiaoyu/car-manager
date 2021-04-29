package com.woniu.realm;


import com.woniu.jwt.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/15/15:09
 * @Description:
 */
public class JwtRealm extends AuthorizingRealm {


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String token = authenticationToken.getPrincipal().toString();
        try {
            JwtTokenUtil.checkSign(token);
        } catch (Exception ex) {
            System.out.println("jwt 解密失败。jwt 已过期，或被篡改。");
            throw new IncorrectCredentialsException(ex);
        }
        //
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token, token, this.getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取Token账号
        String principal = (String) principalCollection.getPrimaryPrincipal();
        //获得用户名
        String name = JwtTokenUtil.getUserId(principal);
        //资源的权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        return info;
    }
}
