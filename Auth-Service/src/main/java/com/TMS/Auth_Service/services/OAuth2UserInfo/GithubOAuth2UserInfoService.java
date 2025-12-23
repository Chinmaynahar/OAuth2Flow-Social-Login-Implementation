package com.TMS.Auth_Service.services.OAuth2UserInfo;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class GithubOAuth2UserInfoService implements OAuth2UserInfo{
        private String email;
        private final Map<String, Object> attributes;
        public GithubOAuth2UserInfoService(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String getId() {
            return String.valueOf(attributes.get("id"));
        }

        @Override
        public String getName() {
            return getId();
        }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
        public String getEmailFromGithub(OAuth2UserRequest oauth2UserRequest) {
            if(email!=null)return email;
            email=fetchPrimaryEmail(oauth2UserRequest);
            return email;
        }

        @Override
        public String getImageUrl() {
            return (String) attributes.get("avatar_url");
        }
        @Override
        public String getUsername(){
            return (String) attributes.get("login");
        }


        private String fetchPrimaryEmail(OAuth2UserRequest userRequest) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userRequest.getAccessToken().getTokenValue());
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(
                        "https://api.github.com/user/emails",
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {}
                );

        List<Map<String, Object>> emails = response.getBody();
        if (emails == null) return null;

        return emails.stream()
                .filter(e -> Boolean.TRUE.equals(e.get("primary")))
                .filter(e -> Boolean.TRUE.equals(e.get("verified")))
                .map(e -> (String) e.get("email"))
                .findFirst()
                .orElse(null);
    }



}
