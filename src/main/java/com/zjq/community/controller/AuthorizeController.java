package com.zjq.community.controller;

import com.zjq.community.adapter.GithubProvider;
import com.zjq.community.dto.AccessTokenDto;
import com.zjq.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

//    用户点击登录之后，授权给git，git会返回我们指定的页面，并携带code信息
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam("state")String state)
    {

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDto);

        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}