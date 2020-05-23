package com.example.oauth.controller;

import com.example.oauth.param.KakaoUserInfo;
import com.example.oauth.param.SocialToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth/kakao/")
public class OauthKakaoController {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/redirect_kakao")
    public RedirectView redirect_kakao(String code){

        RestTemplate restTemplate = restTemplateBuilder.build();

        /**
         * Access token 받기
         */
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("grant_type","authorization_code");
        params.add("client_id", "0274a24d1bb2fdfd9dbb9a2c659150af");
        params.add("redirect_uri","http://localhost:8080/oauth/kakao/redirect_kakao");
        params.add("code",code);
        params.add("client_secret","MOSPdPa6Bs0ndQ80El0NdXlKsdHDoxAs");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "x-www-form-urlencoded", StandardCharsets.UTF_8));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        SocialToken socialToken = restTemplate.postForObject("https://kauth.kakao.com/oauth/token", request, SocialToken.class);

        /**
         * 사용자 정보 받기
         */
        Map map = new HashMap();
        map.put("Authorization",socialToken.getAccess_token());
        headers.set("Authorization","Bearer "+socialToken.getAccess_token());
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<KakaoUserInfo> res = restTemplate.exchange("https://kapi.kakao.com/v1/user/access_token_info", HttpMethod.GET, entity, KakaoUserInfo.class, map);

        String id = res.getBody().getId();
        System.out.println(id);

        map.clear();
        map.put("target_id_type", "user_id");
        map.put("target_id", id);
        ResponseEntity<String> resInfo = restTemplate.exchange("https://kapi.kakao.com//v2/user/me", HttpMethod.GET, entity, String.class, map);
        System.out.println(resInfo);

        return new RedirectView("/");
    }


}
