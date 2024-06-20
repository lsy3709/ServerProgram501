package com.busanit501.boot501.config;

import com.busanit501.boot501.security.CustomUserDetailsService;
import com.busanit501.boot501.security.handler.Custom403Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
// 어노테이션을 이용해서, 특정 권한 있는 페이지 접근시, 구분가능.
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableMethodSecurity
@EnableWebSecurity
public class CustomSecurityConfig {
    private final DataSource dataSource;
    private final CustomUserDetailsService customUserDetailsService;

    // 평문 패스워드를 해시 함수 이용해서 인코딩 해주는 도구 주입.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("시큐리티 동작 확인 ====CustomSecurityConfig======================");
        // 로그인 없이 자동 로그인 확인
        // 빈 설정.
        // 인증 관련된 설정.
        http.formLogin(
                formLogin -> formLogin.loginPage("/member/login").permitAll()
        );
        // 기본은 csrf 설정이 on, 작업시에는 끄고 작업하기.
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        // 특정 페이지에 접근 권한 설정.
        http.authorizeRequests()
                // 정적 자원 모두 허용.
                .requestMatchers("/css/**", "/js/**","/image/**").permitAll()
                // 리스트는 기본으로 다 들어갈수 있게., 모두 허용
                .requestMatchers("/", "/board/list", "/login", "/joinUser","/joinForm","/findAll","/images/**").permitAll()
                // 로그인 후 확인 하기. 권한 예제) hasRole("USER"),hasRole("ADMIN")
                .requestMatchers("/board/register","/board/read","/board/update" ).authenticated()
                // 권한  관리자만, 예제로 , 수정폼은 권한이 관리자여야 함.
                .requestMatchers("/admin","/images").hasRole("ADMIN")
                // 위의 접근 제어 목록 외의 , 다른 어떤 요청이라도 반드시 인증이 되어야 접근이 된다.
                .anyRequest().authenticated();

        //403 핸들러 적용하기.
        http.exceptionHandling(
                accessDeny -> {
                    accessDeny.accessDeniedHandler(accessDeniedHandler());
        }
        );

        // 자동로그인 설정.1
        http.rememberMe(
                httpSecurityRememberMeConfigurer ->
                        httpSecurityRememberMeConfigurer
                                // 토큰 생성시 사용할 암호
                                .key("12345678")
                                // 스프링 시큐리티에서 정의해둔 Repository
                        .tokenRepository(persistentTokenRepository())
                                // UserDetail를 반환하는 사용자가 정의한 클래스
                        .userDetailsService(customUserDetailsService)
                                // 토큰의 만료 시간.
                        .tokenValiditySeconds(60*60*24*30)
        );



        return http.build();
    }

    // 자동로그인 설정 2
    // 시스템에서 정의해둔 기본 약속.
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 시큐리에서 정의 해둔 구현체
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }


    //정적 자원 시큐리티 필터 항목에 제외하기.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("시큐리티 동작 확인 ====webSecurityCustomizer======================");
        return (web) ->
                web.ignoring()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //사용자 정의한 403 예외 처리
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }


}
