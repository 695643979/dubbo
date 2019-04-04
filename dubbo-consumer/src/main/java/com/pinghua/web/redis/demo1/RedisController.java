package com.pinghua.web.redis.demo1;

import com.pinghua.utils.JsonUtil;
import com.pinghua.vo.request.HelloRequestVo;
import com.pinghua.vo.response.ApiResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/12/7.
 * @author: wenhuaping
 * reids控制类
 */
@Slf4j
@RestController
@RequestMapping("/redisController")
@Api(tags = "redis控制类")
public class RedisController {

    private static final String redisOperation = "redis 操作类";

    @Autowired
    private RedisService redisService;

    /**
     * 设置 redis 值
     * 访问路径：http://10.10.20.192:7002/redisController/helloRedisSet
     */
    @GetMapping("/helloRedisSet")
    @ResponseBody
    public ApiResponse helloRedisSetValue() {
        try {
            log.info("{}，请求参数", "redis");
            redisService.set(RedisConstans.prefix + RedisConstans.medianKey + RedisConstans.suffix, "redis 设置的值", 2*60*60L);
           
            ApiResponse response = ApiResponse.builder()
                    .code(1000)
                    .msg("redis值设置成功!")
                    .result(findData())
                    .build();

            log.info("{}，响应参数: {}", "", JsonUtil.objectToJsonString(response));
            return response;
        } catch (Exception e) {
            log.error("{}，发生业务异常，异常信息：", redisOperation, e);
        }

        return null;
    }

    /**
     * 获取 redis 值
     * 访问路径：http://10.10.20.192:7002/redisController/helloRedisGet
     * @return
     */
    @GetMapping("/helloRedisGet")
    @ResponseBody
    public ApiResponse helloRedisGetValue() {
        try {
            log.info("{}，请求参数", "redis");
            String value = redisService.get(RedisConstans.prefix + RedisConstans.medianKey + RedisConstans.suffix).toString();

            ApiResponse response = ApiResponse.builder()
                    .code(1000)
                    .msg("redis值获取成功!")
                    .result(value)
                    .build();

            log.info("{}，响应参数: {}", "", JsonUtil.objectToJsonString(response));
            return response;
        } catch (Exception e) {
            log.error("{}，发生业务异常，异常信息：", redisOperation, e);

        }

        return null;
    }

    /**
     * 查找数据
     * @return
     */
    private HelloRequestVo findData() {
        HelloRequestVo vo = HelloRequestVo.builder()
                .id(122222333333L)
                .account("13824797654")
                .age(19)
                .name("李白")
                .build();
        return  vo;
    }

}
