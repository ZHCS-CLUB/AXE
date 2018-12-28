package club.zhcs.shiro.jwt;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import club.zhcs.shiro.jwt.utils.JwtUtil;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        JwtToken token = (JwtToken) authcToken;
        Object cred = getCredentials(info);
        return JwtUtil.verify(Strings.safeToString(token.getPrincipal(), ""),
                              Strings.safeToString(info.getPrincipals(), ""),
                              Strings.safeToString(cred, ""));
    }

    /**
     * 加密
     * 
     * @param userName
     *            用户名
     * @param password
     *            密码
     * @return 密码密文
     */
    public String password(char[] password, String userName) {
        return password(userName, new String(password));
    }

    /**
     * 加密
     * 
     * @param userName
     *            用户名
     * @param password
     *            密码
     * @return 密码密文
     */
    public String password(String userName, String password) {
        return password(password.getBytes(), userName.getBytes());
    }

    /**
     * 
     * @param p
     *            密码
     * @param salt
     *            盐
     * @return 密码密文
     */
    public String password(byte[] p, byte[] salt) {
        return Lang.digest("MD5", p, salt, 2);
    }

}
