package com.zjq.community.adapter.impl;

import com.alibaba.fastjson.JSON;
import com.zjq.community.adapter.GithubProvider;
import com.zjq.community.dto.AccessTokenDto;
import com.zjq.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//Component仅仅注入到spring的容器里面
@Component
public class OkHttpAdapter implements GithubProvider {

    @Override
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        //toJSONString把实体类转化为JSON的格式
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String access_token=split[0].split("=")[1];
            System.out.println(access_token);
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            //返回的是JSON格式的string
            String string= response.body().string();
            //通过JSON的parseObject，可以获取到传入的对象实体
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
