package com.mb.hcaptcha.service;

import com.mb.hcaptcha.client.response.HCaptchaResponse;

public interface HCaptchaService {

    HCaptchaResponse validateRequestWithHttpClient(String captchaResponse);

    HCaptchaResponse validateRequestWithMultiValueMap(String captchaResponse);

    HCaptchaResponse validateRequest(String captchaResponse);

}
