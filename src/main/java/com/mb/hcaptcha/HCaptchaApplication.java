package com.mb.hcaptcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HCaptchaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HCaptchaApplication.class, args);
    }

}
