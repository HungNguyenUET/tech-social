package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Remove field injection to avoid circular dependency; inject via bean method parameters instead

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // Cho phép register/login
                        .anyRequest().authenticated()  // Các endpoint khác cần auth
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(AbstractHttpConfigurer::disable)  // Tắt form login mặc định
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService(userService))
                        )
                        .successHandler((request, response, authentication) -> {
                            // Xử lý sau login OAuth2 thành công, ví dụ redirect hoặc trả token
                            // Tham chiếu các biến để tránh cảnh báo unused
                            request.getRequestURI();
                            authentication.getName();
                            response.sendRedirect("/");  // Redirect về trang chính
                        })
                );
        return http.build();
    }

    @Bean
    public DefaultOAuth2UserService oAuth2UserService(UserService userService) {
        return new DefaultOAuth2UserService() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oauth2User = super.loadUser(userRequest);

                String provider = userRequest.getClientRegistration().getRegistrationId();
                Map<String, Object> attributes = oauth2User.getAttributes();

                Object idAttr = attributes.get("sub") != null ? attributes.get("sub") : attributes.get("id");
                String providerId = idAttr != null ? String.valueOf(idAttr) : null;

                String email = attributes.get("email") != null ? String.valueOf(attributes.get("email")) : null;
                String username = attributes.get("name") != null ? String.valueOf(attributes.get("name")) :
                        (attributes.get("login") != null ? String.valueOf(attributes.get("login")) : null);
                String imageUrl = attributes.get("picture") != null ? String.valueOf(attributes.get("picture")) :
                        (attributes.get("avatar_url") != null ? String.valueOf(attributes.get("avatar_url")) : null);

                User user = userService.findOrCreateOAuthUser(provider, providerId, email, username, imageUrl);

                Collection<GrantedAuthority> authorities = Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole())
                );

                String nameAttributeKey = userRequest
                        .getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName();

                return new DefaultOAuth2User(authorities, attributes, nameAttributeKey);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}