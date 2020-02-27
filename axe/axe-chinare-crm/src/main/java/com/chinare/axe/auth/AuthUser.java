package com.chinare.axe.auth;

import java.util.List;

import org.nutz.lang.util.NutMap;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {
    @ApiModelProperty(required = true, value = "用户名")
    String userName;

    @ApiModelProperty(required = true, value = "密码")
    String password;

    @ApiModelProperty(required = true, value = "jwt Token")
    String token;

    @ApiModelProperty(required = true, value = "角色列表")
    List<String> roles;

    @ApiModelProperty(required = true, value = "权限列表")
    List<String> permissions;

    @Default
    @ApiModelProperty(required = true, value = "扩展信息")
    NutMap extInfo = NutMap.NEW();

    /**
     * 添加扩展信息
     * 
     * @param key
     *            key
     * @param value
     *            value
     * @return 用户信息
     */
    public AuthUser addExt(String key, Object value) {
        getExtInfo().setv(key, value);
        return this;
    }

    public AuthUser token() {
        setToken(JwtUtil.sign(userName, password, true));
        return this;
    }

}
