package com.wly.secondkill.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // protect all endpoints in this app
        http.authorizeRequests()
                // protect the endpoint. only accessible to authenticated users
                .antMatchers("api/")
                .authenticated()
                .and()
                // configute OAuth2 Resoucer Server support
                .oauth2ResourceServer()
                // enable JWT-encoded bearer token support
                .jwt();

        // add CORS filters
        http.cors();

        // force a non-empty response body for 401's to make the response more friendly
        Okta.configureResourceServer401ResponseBody(http);

        // disable CSRF since we are not using Cookies for session tracking
        http.csrf().disable();
    }
}
