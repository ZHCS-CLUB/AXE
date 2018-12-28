package club.zhcs.shiro.jwt;

import java.util.Set;

/**
 * @author 王贵源<kerbores@gmail.com>
 *
 * @date 2018-12-28 09:36:37
 */
public interface ShiroUserService {

    UserDetail fetch(String userName);

    /**
     * @param username
     * @return
     */
    Set<String> roles(String username);

    /**
     * @param username
     * @return
     */
    Set<String> permissions(String username);

}
