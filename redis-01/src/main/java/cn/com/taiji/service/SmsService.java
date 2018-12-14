package cn.com.taiji.service;

import cn.com.taiji.domain.Sms;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

public interface SmsService {

    @CachePut(value="phone")
    List<Sms> login(String phone);
}
