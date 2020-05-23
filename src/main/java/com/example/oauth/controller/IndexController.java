package com.example.oauth.controller;

import com.example.oauth.enums.UserType;
import com.example.oauth.param.UserInfo;
import com.example.oauth.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    HomeService homeService;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/")
    public String index(HttpSession session){
        return "index";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(String id, String password, Model model, HttpSession session){
        UserInfo userInfo = homeService.join(id, password, UserType.NORMAL);
        session.setAttribute("userInfo", userInfo);
        return "redirect:/home";
    }

    @PostMapping("/login")
    public String login(@RequestBody String email, String password){
//        homeService.join(email,password);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }



//    @GetMapping("/redirect_naver")
//    public String redirect_naver(String code){
//
//        RestTemplate restTemplate = restTemplateBuilder.build();
////
//        MultiValueMap<String, String> params = new LinkedMultiValueMap();
//        params.add("grant_type","authorization_code");
//        params.add("client_id", "a3GDL_8xKua76zNi1pOl");
//        params.add("client_secret","xsF5ASk5aA");
//        params.add("code",code);
//        params.add("state","123");
//
//
//
////        String value = "grant_type=authorization_code&client_id=0274a24d1bb2fdfd9dbb9a2c659150af&redirect_uri=http://localhost:8080/redirect_kakao&code="+code+"&client_secret=MOSPdPa6Bs0ndQ80El0NdXlKsdHDoxAs";
//
//        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//        headers.setContentType(new MediaType("application", "x-www-form-urlencoded", StandardCharsets.UTF_8));
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//
//        String response = restTemplate.postForObject("https://nid.naver.com/oauth2.0/token", request, String.class);
////        String response = restTemplate.postForObject("http://localhost:8080/login", request, String.class);
//        System.out.println(response);
//        return "redirect:/";
//    }
}
