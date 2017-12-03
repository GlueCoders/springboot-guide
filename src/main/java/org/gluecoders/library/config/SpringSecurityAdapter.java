package org.gluecoders.library.config;

import org.gluecoders.library.security.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@EnableWebSecurity(debug = false)
public class SpringSecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalService principalService;

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> Optional.ofNullable(principalService.findUser(username))
                .map(credential -> User.withUsername(credential.getUsername())
                        .password(credential.getSaltedPwd())
                        .roles(credential.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/unsecured/register").permitAll()
                .mvcMatchers(HttpMethod.GET, "/books").hasRole("USER")
                .mvcMatchers(HttpMethod.GET, "/books/*").hasRole("USER")
                .and()
                .httpBasic().and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()))
                .and()
                .csrf().disable()
                ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
