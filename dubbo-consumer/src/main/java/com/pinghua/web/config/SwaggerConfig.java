package com.pinghua.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhuaping on 2019/4/4.
 * @author: wenhuaping
 */
//@Profile({"dev", "test","testa","testb","testd"})  //指定对应的环境，swagger不应用于生产环境
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        //header中增加 token_id参数，没有可以去掉
        // 添加head参数start
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder().name("imei").description("手机唯一标示").modelRef(new ModelRef("string")).parameterType("header").required(false).build());
        parameters.add(new ParameterBuilder().name("version").description("接口版本号").modelRef(new ModelRef("string")).defaultValue("1.0").parameterType("header").required(true).build());
        parameters.add(new ParameterBuilder().name("client").description("客户端来源（1-安卓；2-苹果；3-H5）").modelRef(new ModelRef("int")).defaultValue("1").parameterType("header").required(true).build());
        parameters.add(new ParameterBuilder().name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定controller存放的目录路径,注意包的位置
                .apis(RequestHandlerSelectors.basePackage("com.pinghua.web"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title("这里是文档标题")
                // 文档描述
                .termsOfServiceUrl("http://www.baidu.com/")
                .version("1.0")
                .build();
    }

//    /**
//     * 取消 security 对接口的拦截，只在 swaggerui 进行拦截
//     *
//     * @param web
//     * @throws Exception
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        //这里填写需要过滤的路径
//        web.ignoring().antMatchers("/api/v1/admin/**");
//    }
}
