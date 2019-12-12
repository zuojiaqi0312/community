package com.zjq.community.controller;

import com.zjq.community.adapter.GithubProvider;
import com.zjq.community.dto.AccessTokenDto;
import com.zjq.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

//    用户点击登录之后，授权给git，git会返回我们指定的页面，并携带code信息
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam("state")String state)
    {

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDto.setClient_id("f2f363d8baeb8b251fc7");
        accessTokenDto.setClient_secret("3649bf20e17af82400f337ce6425d506823e4a27");
        accessTokenDto.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDto);

        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}