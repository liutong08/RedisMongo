package cn.com.taiji.domain;

import java.io.Serializable;

public class Sms implements Serializable {
    private String phone;
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Sms(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }

    public Sms() {
    }
}
