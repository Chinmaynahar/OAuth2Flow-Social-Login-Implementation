package com.TMS.Auth_Service.services.OAuth2UserInfo;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;

public class GoogleOAuth2UserInfoService implements OAuth2UserInfo {


        private final Map<String, Object> attributes;

        public GoogleOAuth2UserInfoService(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String getId() {
            return (String) attributes.get("sub");
        }

        @Override
        public String getName() {
            return (String) attributes.get("name");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

        @Override
        public String getImageUrl() {
            return (String) attributes.get("picture");
        }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public String getEmailFromGithub(OAuth2UserRequest oauth2UserRequest) {
        return "";
    }
}

