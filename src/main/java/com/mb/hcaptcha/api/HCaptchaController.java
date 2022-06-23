package com.mb.hcaptcha.api;

import com.mb.hcaptcha.client.response.HCaptchaResponse;
import com.mb.hcaptcha.service.HCaptchaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class HCaptchaController {
    private final HCaptchaService hCaptchaService;

    /**
     * Validate request with HCaptcha client
     *
     * @param captchaResponse gets captcha response.
     */
    @PostMapping("/me/signup")
    public HCaptchaResponse signup(@RequestParam(value = "h-captcha-response", defaultValue = "10000000-aaaa-bbbb-cccc-000000000001") String captchaResponse) {
        if (StringUtils.hasText(captchaResponse)) {
            hCaptchaService.validateRequestWithHttpClient(captchaResponse);
            hCaptchaService.validateRequestWithMultiValueMap(captchaResponse);
            return hCaptchaService.validateRequest(captchaResponse);
        }
        return new HCaptchaResponse();
    }
}
