package cn.com.taiji.service.impl;

import cn.com.taiji.domain.Sms;
import cn.com.taiji.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {
    private List<Sms> smsList;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<Sms> login(String phone) {
        Sms sms=new Sms();
        sms.setPhone(phone);
        Random random = new Random();
        String code="";
        for (int i=0;i<6;i++)
        {
            code+=random.nextInt(10);
        }
        System.out.println(code);
        sms.setCode(code);
        smsList=new ArrayList<>();
        smsList.add(sms);
        return smsList;
    }
}
