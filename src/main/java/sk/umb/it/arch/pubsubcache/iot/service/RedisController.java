package sk.umb.it.arch.pubsubcache.iot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/api/redis")
    public List<String> readIotData() {
        return redisTemplate.opsForList().range("iot-data", 0, 20);
    }
}
