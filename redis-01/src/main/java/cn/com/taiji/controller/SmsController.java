package cn.com.taiji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
public class SmsController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //登录操作
    @GetMapping("/login")
    public String login(String phone,String inputCode){
        String page="";
        //输出前台输入的手机号和密码
        System.out.println(phone+"---"+inputCode);
        //查看redis中是否有以phone为key的list
        if(redisTemplate.hasKey(phone)) {
            //获取redis中以phone为key的最后一个value值
            String serverCode=redisTemplate.opsForList().index(phone, redisTemplate.opsForList().size(phone)-1);
            System.out.println(serverCode);
            //判断前台的验证码和redis中的验证码是否相同
            if (inputCode.equals(serverCode)) {
                page = "success";
            } else {
                page = "error";
            }
        }
        return page;
    }

    //发送验证码
    @GetMapping("/sendCode")
    public String sendCode(String phone, Model model) {
        String msg="";
        String serverCode="";
        Random random = new Random();
        //判断redis中以phone为key的value的数据个数
        if(redisTemplate.opsForList().size(phone)>2){
            msg="同一账号1分钟内只允许发送三次验证码，请稍后再试";
        }else {
            //随机生成6位验证码
            for (int i = 0; i < 6; i++) {
                serverCode += random.nextInt(10);
            }
            System.out.println(serverCode);
            //将phone为key，将serverCode为value新增到list中
            redisTemplate.opsForList().rightPush(phone, serverCode);
            //设定list的过期时间为1分钟
            redisTemplate.expire(phone, 60000, TimeUnit.MILLISECONDS);
            msg="发送成功"+serverCode;
        }
        model.addAttribute("phone",phone);
        model.addAttribute("msg",msg);
        return "index";
    }
}
