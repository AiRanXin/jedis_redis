package com.example.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {
    public static void main(String[] args) {
        //模拟验证码发送
        verifyCode("1239900114");
        //getRedisCode("1239900114","11111");
    }
    //3.验证码校验
    public static void getRedisCode(String phone,String code){
        //从redis获取验证码
        Jedis jedis = new Jedis("192.168.10.101",6379);
        jedis.auth("974836yue");
        //验证码key
        String codetKey = "verifyCode"+phone+":code";
        String redisCode = jedis.get(codetKey);
        //判断
        if (redisCode.equals(code)){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
        jedis.close();
    }
    //2.每个手机每天只能发送3次，验证码放到redis中，设置过期时间
    public static void  verifyCode(String phone){
        //链接redis
        Jedis jedis = new Jedis("192.168.10.101",6379);
        jedis.auth("974836yue");
        //拼接key
        //手机发送次数key
        String countKey = "verifyCode"+phone+":count";
        //验证码key
        String codetKey = "verifyCode"+phone+":code";
        String count = jedis.get(countKey);
        if (count == null){
           //没有发送次数，第一次发送，设置发送次数为1
           jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count) <= 2){
           //发送次数+1
           jedis.incr(countKey);
        }else if (Integer.parseInt(count) > 2){
            //发送3次，不能在发送
            System.out.println("今天发送次数已经超过3次");
            jedis.close();
            return;
        }
       //发送验证码到redis
        String vcode = getCode();
        jedis.setex(codetKey,120,vcode);

    }
    //1.生成6位数字验证码
    public static String getCode(){
        Random random = new Random();
        String code = "";
        for (int i=0; i<6; i++){
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }

}
