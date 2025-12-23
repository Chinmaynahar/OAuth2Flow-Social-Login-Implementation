package com.TMS.Auth_Service.services.OAuth2UserInfo;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

public interface OAuth2UserInfo {
    String getId();
    String getName();
    String getEmail();
    String getImageUrl();
    String getUsername();
    //for github
    String getEmailFromGithub(OAuth2UserRequest oauth2UserRequest);
}
