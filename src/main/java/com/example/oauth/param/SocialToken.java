package com.example.oauth.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Setter
@Getter
public class SocialToken {
    private String access_token;
    private String refresh_token;
}
