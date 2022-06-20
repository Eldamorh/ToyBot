package com.eldamorh.toybot.configuration;

import com.eldamorh.toybot.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private MyUserDetailsService userDetailsService;

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new SuccessHandler();
    }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            String loginPage = "/login";
            String logoutPage = "/logout";
            http.
                    authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers(loginPage).permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated()
                    .and().csrf().disable()
                    .formLogin()
                    .loginPage(loginPage)
                    .failureUrl("/login?error=true")
                    .successHandler(successHandler())
                    .usernameParameter("user_name")
                    .passwordParameter("password")
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                    .logoutSuccessUrl(loginPage).and().exceptionHandling();

//            http
//                    .authorizeRequests()
//                    .antMatchers("/anonymous*").anonymous()
//                    .antMatchers("/login*").permitAll()
//                    .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
//                    .antMatchers(loginPage).hasAnyAuthority("USER", "ADMIN")
//                    .antMatchers("/registration").hasAnyAuthority("USER", "ADMIN")
//                    .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
//                    .antMatchers("/admin/**").hasAuthority("ADMIN")
//                    .anyRequest().authenticated()
//
//                    .and()
//                    .formLogin()
//                    .loginPage(loginPage)
//                    .loginProcessingUrl("/login")
//                    .successHandler(successHandler())
//                                        .usernameParameter("user_name")
//                    .passwordParameter("password")
//                    .failureUrl("/login?error=true")
//
//                    .and()
//
//                    .logout().deleteCookies("JSESSIONID")
//                    .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
//                    .logoutSuccessUrl(loginPage).and().exceptionHandling()
//
//                    .and()
//                    .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
//
//                    .and()
//                    .csrf().disable();


        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
        }


}
