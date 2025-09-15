package com.socialdemo.socialdemo.config;

import com.socialdemo.socialdemo.service.UserService;
import com.socialdemo.socialdemo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Remove field injection to avoid circular dependency

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DefaultOAuth2UserService oAuth2UserService) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                .successHandler((ignoredRequest, response, ignoredAuth) -> {
                    Objects.requireNonNull(ignoredRequest);
                    Objects.requireNonNull(ignoredAuth);
                    response.sendRedirect("/");
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
                String providerUserNameAttribute = userRequest.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName();

                String providerId = oauth2User.getAttribute("sub") != null
                        ? oauth2User.getAttribute("sub")
                        : String.valueOf(oauth2User.getAttribute("id"));
                String email = oauth2User.getAttribute("email");
                String username = oauth2User.getAttribute("name");
                String imageUrl = oauth2User.getAttribute("picture");

                User user = userService.findOrCreateOAuthUser(provider, providerId, email, username, imageUrl);

                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
                return new DefaultOAuth2User(
                        java.util.Set.of(authority),
                        oauth2User.getAttributes(),
                        providerUserNameAttribute
                );
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
