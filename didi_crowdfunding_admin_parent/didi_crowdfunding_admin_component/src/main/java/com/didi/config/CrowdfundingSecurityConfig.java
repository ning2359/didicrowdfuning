package com.didi.config;

import com.didi.crowd.CrowdConstant;
import com.didi.util.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService detailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication()
//                .withUser("aa")
//                .password("123123")
//                .roles("ADMIN");
        builder.userDetailsService(detailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests() // 对请求进行授权
                .antMatchers("/admin/to/login/page.html") // 针对登录页进行设置
                .permitAll() // 无条件访问
                .antMatchers("/bootstrap/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/crowd/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/css/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/fonts/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/img/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/js/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/layer/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/script/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/ztree/**") // 针对静态资源进行设置， 无条件访问
                .permitAll()
                .antMatchers("/admin/get/page.html")
                .access("hasRole('经理') or hasAnyAuthority('user:get') ")
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/admin/to/login/page.html")
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .loginProcessingUrl("/security/do/login.html")
                .defaultSuccessUrl("/admin/to/main/page.html")
                .and()
                .logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception", CrowdConstant.MESSAGE_ACCESS_DENIED);
                        request.getRequestDispatcher("/WEB-INF/system_error.jsp").forward(request,response);
                    }
                })
        ;
    }
}
