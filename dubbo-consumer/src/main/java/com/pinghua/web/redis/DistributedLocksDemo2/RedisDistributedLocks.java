package com.pinghua.web.redis.DistributedLocksDemo2;

import com.alibaba.fastjson.JSON;
import com.pinghua.web.redis.demo1.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by wenhuaping on 2019/3/29.
 * @author: wenhuaping
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisDistributedLocks {

    private static final String APPLY_LOAN_PAGE = "测试redis分布式锁";
    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // 获取锁 尝试次数 0.2 秒一次 默认4 秒
    public static int LOCK_TRY_OVERTIME = 5 * 4;

    @Autowired
    private RedisService redisService;

    @ResponseBody
    @PostMapping(value = "/applyApi")
    public RedisLocksResponse applyApi(HttpServletRequest request, @Validated @RequestBody RedisLocksRequest req) {
        boolean acquire = false;
        String lockKey = "lockKey:one:";
        String userId = "12345664";
        RedisLocksResponse resp = null;
        Long dailyLoanCount = 200022L;

        try {
            acquire = redisService.lock(lockKey + userId);
            if(acquire){
                log.info("获取锁：{}",lockKey + userId);

                //请求业务方法 TODO
                doBiz();

                String today = format.format(LocalDate.now());
                String key = dailyLoanCount + "typeCode" + "_" + today;
                if (dailyLoanCount == null || dailyLoanCount <= 0) {
                    log.error("当前产品大类{}可进件量为0", "P0001");
                    // TODO
                }

                //日进件量 缓存有效时长 48小时，只更新当天进件量
                Integer count = (Integer) redisService.get(key);
                if (count == null) {
                    redisService.set(key, 1, 48 * 60 * 60 * 1L);
                } else if (count < dailyLoanCount) {
                    redisService.incr(key, 1);
                } else {
                    log.info("已到本日进件量控制上限:" + dailyLoanCount);
                }



                log.info("用户{} 返回信息:{}",userId, JSON.toJSONString(resp));
                return resp;

            }else{
                log.error("{}没有获取锁！",userId);

            }
        } catch (Exception e) {
            log.error("{}访问异常;{}", userId, e.getMessage());
        } finally {
            if(acquire){
                log.info("释放锁：{}",lockKey + userId);
                try{
                    redisService.releaseLock(lockKey + userId);
                }catch (Exception e) {
                    log.error("释放锁异常：{}", lockKey + userId, e);
                }
            }
        }

        return null;
    }

    private void doBiz() {
        String lockKey = "lockKey:second:";


        boolean acquire = redisService.lock(lockKey);
        // CAS 自旋 获取N次 --> 看readme.txt
        if (!acquire) {
            for (int i = 0; i < LOCK_TRY_OVERTIME; i++) {
                acquire = redisService.lock(lockKey);
                if (acquire) {
                    break;
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    log.error("获取锁异常，线程中断!");
                }
            }
        }

        // 成功获取锁
        if (acquire) {
            try {
                log.info(" 获取锁： ");

                // toBiz(); TODO


            } catch (Exception e) {
                log.error("生成放款单异常", e);

            } finally {
                log.info("释放锁：{}"  );
                redisService.releaseLock(lockKey);
            }
        }



    }

}
