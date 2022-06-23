package com.mb.hcaptcha.client;

import com.mb.hcaptcha.client.request.HCaptchaRequest;
import com.mb.hcaptcha.client.response.HCaptchaResponse;
import com.mb.hcaptcha.config.CoreFeignConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "hcaptcha", url = "${feign.services.hcaptcha.url}", configuration = CoreFeignConfiguration.class)
public interface HCaptchaClient {

    @PostMapping(value = "/siteverify", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    HCaptchaResponse validateRequest(@RequestBody MultiValueMap<String, ?> hCaptchaRequest);

    @PostMapping(value = "/siteverify", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    HCaptchaResponse validateRequest(HCaptchaRequest hCaptchaRequest);

}
