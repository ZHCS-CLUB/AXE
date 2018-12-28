package club.zhcs.shiro.jwt;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.lang.Strings;

import club.zhcs.shiro.jwt.utils.JwtUtil;

/**
 * @author 王贵源<kerbores@gmail.com>
 *
 * @date 2018-12-27 16:24:18
 */
public class JwtRealm extends AuthorizingRealm {

    private ShiroUserService userService;

    /**
     * 
     */
    public JwtRealm(ShiroUserService userService) {
        this.userService = userService;
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = Strings.safeToString(principals, "");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(userService.roles(username));
        simpleAuthorizationInfo.setStringPermissions(userService.permissions(username));
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }
        UserDetail userDetail = userService.fetch(username);
        if (userDetail == null) {
            throw new AuthenticationException("用户不存在!");
        }

        if (!JwtUtil.verify(token, username, userDetail.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        return new SimpleAuthenticationInfo(username, userDetail.getPassword(), "jwtRealm");
    }
}
