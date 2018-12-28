package club.zhcs.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 王贵源<kerbores@gmail.com>
 *
 * @date 2018-12-27 16:19:42
 */
public class JwtToken implements AuthenticationToken {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
