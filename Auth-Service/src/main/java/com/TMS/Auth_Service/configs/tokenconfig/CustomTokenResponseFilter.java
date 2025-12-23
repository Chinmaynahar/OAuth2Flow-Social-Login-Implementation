package com.TMS.Auth_Service.configs.tokenconfig;

import com.TMS.Auth_Service.models.entities.User;
import com.TMS.Auth_Service.repository.UserRepository;
import com.TMS.Auth_Service.services.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
public class CustomTokenResponseFilter extends OncePerRequestFilter {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if ("/oauth2/token".equals(request.getRequestURI()) && "POST".equals(request.getMethod())) {

            ResponseWrapper responseWrapper = new ResponseWrapper(response);
            filterChain.doFilter(request, responseWrapper);

            String responseBody = responseWrapper.getCaptureAsString();

            try {
                Map<String, Object> tokenResponse = objectMapper.readValue(responseBody, Map.class);

                if (tokenResponse.containsKey("access_token")) {
                    String accessToken = (String) tokenResponse.get("access_token");
                    String username = extractUsernameFromJWT(accessToken);

                    if (username != null) {
                        User user = userRepository.findByUsername(username)
                                .or(() -> userRepository.findByEmail(username))
                                .orElse(null);

                        if (user != null) {
                            String customRefreshToken = refreshTokenService
                                    .createRefreshToken(user.getId())
                                    .getToken();
                            tokenResponse.put("refresh_token", customRefreshToken);

                            System.out.println("Added custom refresh token to response");
                        }
                    }
                }

                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));

            } catch (Exception e) {
                response.getWriter().write(responseBody);
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String extractUsernameFromJWT(String jwt) {
        try {
            String[] parts = jwt.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            Map<String, Object> claims = objectMapper.readValue(payload, Map.class);
            return (String) claims.get("sub");
        } catch (Exception e) {
            return null;
        }
    }
}