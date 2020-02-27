package com.chinare.axe.auth;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public interface AuthService {

    /**
     * 登录信息
     * 
     * @author 王贵源(kerbores@gmail.com)
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class LoginDto {
        @NotNull(message = "用户名不能为空")
        @ApiModelProperty("用户名")
        String name;

        @NotNull(message = "密码不能为空")
        @ApiModelProperty("密码")
        String password;

        @ApiModelProperty("验证码")
        String captcha;

        @ApiModelProperty("uuid")
        String uuid;

        @Default
        @ApiModelProperty("记住我标识")
        boolean rememberMe = true;
    }

    /**
     * 获取当前用户角色列表
     * 
     * @return 角色列表
     */
    public List<String> roles();

    /**
     * 获取当前用户权限列表
     * 
     * @return 权限列表
     */
    public List<String> permissions();

    /**
     * 获取当前用户
     * 
     * @return 当前用户
     */
    public AuthUser user();

    /**
     * 获取当前请求的jwt token
     * 
     * @return token jwt token
     */
    public String token();

    /**
     * 获取当前用户用户名
     * 
     * @return 用户名
     */
    public String userName();

    /**
     * 执行登录操作
     * 
     * @param loginDto
     *            登录用户信息
     * @return 用户
     */
    public AuthUser login(LoginDto loginDto);

    /**
     * 当前请求是否跳过鉴权
     * 
     * @return 是否跳过标识
     */
    public boolean skip();

    /**
     * 认证检查
     * 
     * @param withoutAuthenticationUrlRegulars
     *            不需要检查的url正则表达式
     * @return 认证检查通过状态
     */
    public boolean authentication(List<String> withoutAuthenticationUrlRegulars);
}
