package com.zjq.community.adapter;

import com.zjq.community.dto.AccessTokenDto;
import com.zjq.community.dto.GithubUser;

public interface GithubProvider {
    String getAccessToken(AccessTokenDto accessTokenDto);

    GithubUser getUser(String accessToken);
}
