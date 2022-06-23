package com.mb.hcaptcha.service.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mb.hcaptcha.client.HCaptchaClient;
import com.mb.hcaptcha.client.request.HCaptchaRequest;
import com.mb.hcaptcha.client.response.HCaptchaResponse;
import com.mb.hcaptcha.config.HCaptchaProperties;
import com.mb.hcaptcha.service.HCaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.OffsetDateTime;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class HCaptchaServiceImpl implements HCaptchaService {

    private final HCaptchaClient hCaptchaClient;
    private final HCaptchaProperties hCaptchaProperties;

    @Override
    public HCaptchaResponse validateRequestWithHttpClient(String captchaResponse) {
        try {
            HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
            String hCaptchaRequest = "response=" + captchaResponse + "&secret=" + hCaptchaProperties.getSecret() + "&sitekey=" + hCaptchaProperties.getSiteKey();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(hCaptchaProperties.getUrl() + "/siteverify"))
                    .header("Content-Type", APPLICATION_FORM_URLENCODED_VALUE)
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(hCaptchaRequest)).build();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setPrettyPrinting()
                    .registerTypeAdapter(OffsetDateTime.class, (JsonDeserializer<OffsetDateTime>) (json, type, context) -> OffsetDateTime.parse(json.getAsString()))
                    .create();

            HCaptchaResponse hCaptchaResponse = gson.fromJson(httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body(), HCaptchaResponse.class);
            log.info("hCaptchaResponse from validateRequestWithHttpClient : {} ", hCaptchaResponse);
            return hCaptchaResponse;
        } catch (Exception e) {
            log.error("Exception occurred while validating request with HttpClient. Exception: {}", ExceptionUtils.getStackTrace(e));
        }
        return new HCaptchaResponse();
    }

    @Override
    public HCaptchaResponse validateRequestWithMultiValueMap(String captchaResponse) {
        try {
            MultiValueMap<String, Object> hCaptchaRequest = new LinkedMultiValueMap<>();
            hCaptchaRequest.add("response", captchaResponse);
            hCaptchaRequest.add("secret", hCaptchaProperties.getSecret());
            hCaptchaRequest.add("sitekey", hCaptchaProperties.getSiteKey());
            HCaptchaResponse hCaptchaResponse = hCaptchaClient.validateRequest(hCaptchaRequest);
            log.info("hCaptchaResponse from validateRequestWithMultiValueMap : {} ", hCaptchaResponse);
            return hCaptchaResponse;
        } catch (Exception e) {
            log.error("Exception occurred while validating request with MultiValueMap. Exception: {}", ExceptionUtils.getStackTrace(e));
        }
        return new HCaptchaResponse();
    }

    @Override
    public HCaptchaResponse validateRequest(String captchaResponse) {
        try {
            HCaptchaRequest captchaRequest = new HCaptchaRequest();
            captchaRequest.setResponse(captchaResponse);
            captchaRequest.setSecret(hCaptchaProperties.getSecret());
            captchaRequest.setSiteKey(hCaptchaProperties.getSiteKey());
            HCaptchaResponse hCaptchaResponse = hCaptchaClient.validateRequest(captchaRequest);
            log.info("hCaptchaResponse from validateRequest : {} ", hCaptchaResponse);
            return hCaptchaResponse;
        } catch (Exception e) {
            log.error("Exception occurred while validating request. Exception: {}", ExceptionUtils.getStackTrace(e));
        }
        return new HCaptchaResponse();
    }


}