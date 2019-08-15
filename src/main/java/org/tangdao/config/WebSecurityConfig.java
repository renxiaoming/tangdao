package org.tangdao.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.tangdao.modules.sys.model.domain.Log;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.impl.PasswordEncoderService;
import org.tangdao.modules.sys.utils.LogUtils;
import org.tangdao.modules.sys.utils.UserUtils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Configuration
	@Order(1)                                                        
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/api/**")                               
				.authorizeRequests()
					.anyRequest().hasRole("APP")
					.and()
				.httpBasic();
		}
	}
    
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    	
    	@Value("${security.loginUrl}")
    	private String loginUrl;
    	
    	@Value("${security.logoutUrl}")
    	private String logoutUrl;
    	
    	@Value("${security.successUrl}")
    	private String successUrl;
    	
    	@Value("${security.authorizeUrl}")
    	private String authorizeUrl;
    	
    	private final UserDetailsService userDetailsService;
    	
    	private final PasswordEncoderService passwordEncoderService;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(@Qualifier("userServiceImpl") UserDetailsService userDetailsService, PasswordEncoderService passwordEncoderService) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoderService = passwordEncoderService;
        }

    	@Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoderService);
        }
    	
    	@Override
        public void configure(WebSecurity web) {
            web
            	.ignoring()
            		.antMatchers("/static/**", "/webjars/**", "/v2/**", "/swagger**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	
        	http.headers().frameOptions().disable();
            http
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers(authorizeUrl).authenticated()
            .and().formLogin()
                    .loginPage(loginUrl).permitAll()
                    .successHandler(new SimpleUrlAuthenticationSuccessHandler() {
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {
							// TODO Auto-generated method stub
							User user = UserUtils.getUser();
							//权限和菜单信息
							user.setMenus(UserUtils.getMenuByParentCode(null));
							
						    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(  
						    		user, authentication.getCredentials(), user.getAuthorities());  
						    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  					    
							SecurityContextHolder.getContext().setAuthentication(authenticationToken);
							
							// 记录用户登录日志
							LogUtils.saveLog(user, request, "系统登录", Log.TYPE_LOGIN_LOGOUT);
							
							super.setAlwaysUseDefaultTargetUrl(true);
							super.setDefaultTargetUrl(successUrl);
//							super.setUseReferer(true);
							super.onAuthenticationSuccess(request, response, authentication);
						}
					})
                    .failureUrl(loginUrl+"?error=true")
            .and().logout()
            		.logoutUrl(logoutUrl).permitAll()
             		.logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler() {
             			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
             					Authentication authentication) throws IOException, ServletException {
         					if (authentication != null && authentication.getPrincipal() != null) {
         						LogUtils.saveLog((User)authentication.getPrincipal(), request, "系统退出", Log.TYPE_LOGIN_LOGOUT);
         					}
         					super.setAlwaysUseDefaultTargetUrl(true);
             				super.setDefaultTargetUrl(loginUrl+"?logout");
//             				super.setUseReferer(true);
             				super.onLogoutSuccess(request, response, authentication);
             			}
					});
        }
	}

}
