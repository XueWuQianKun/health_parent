package com.itheima.job;

import com.itheima.constant.RedisConstant;
import com.itheima.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class CleanImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void doJob(){
        Jedis jedis = jedisPool.getResource();
        // 1. 获取redis中所有图片的集合减去数据库存储的集合 得到减去后的结果集
        Set<String> need2Delete = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        // 需要删除的图片名称
        String[] need2DeleteArr = need2Delete.toArray(new String[]{});
        // 2. 调用七牛批量删除图片
        QiNiuUtil.removeFiles(need2DeleteArr);
        // 3. redis的所有图片的集合要删除已经删除的图片
        // 删除集合中的元素，第一个参数：集合的key, 第二个参数，要删除的元素数组
        //jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES, need2DeleteArr);
        jedis.del(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
    }
}
