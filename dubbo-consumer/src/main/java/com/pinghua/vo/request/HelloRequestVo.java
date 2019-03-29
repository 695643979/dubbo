package com.pinghua.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2018/12/7.
 * @author: wenhuaping
 * @JsonIgnoreProperties(ignoreUnknown = true): 数据库查询的字段中，如果没有相关的字段，就忽略
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloRequestVo {

    @NotEmpty(message = "[id]请求用户id不能为空")
    private Long id;
    @NotEmpty(message = "[name]请求用户name不能为空")
    private String name;
    @NotEmpty(message = "[age]请求用户age不能为空")
    private Integer age;
    @Pattern(regexp="^1\\d{10}$",message = "[account]请输入正确的手机号码")
    private String account;

}
