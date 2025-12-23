package com.TMS.Auth_Service.services.OAuth2UserInfo;


import com.TMS.Auth_Service.models.entities.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(User.AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfoService(attributes);
        } else if (registrationId.equalsIgnoreCase(User.AuthProvider.GITHUB.toString())) {
            return new GithubOAuth2UserInfoService(attributes);
        } else {
            throw new OAuth2AuthenticationException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
