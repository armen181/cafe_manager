package net.ddns.armen181.cafe.cafe_manager.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    //==============  wiring Beans =================
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurity(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    //==============  wiring Beans =================


    //==============  Configure HttpSecurity =================
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //disabled for avoid conflict with h2 DB web console
        http.headers().frameOptions().disable(); //disabled for avoid conflict with h2 DB web console
        http.httpBasic().and().
                authorizeRequests()
                .antMatchers("/","/js/**", "/css/**").permitAll()
                .antMatchers("/rest/**").authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }
    //==============  Configure HttpSecurity =================

    //==============  Configure AuthenticationManagerBuilder =================
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }


    //==============  Configure PasswordEncoder =================
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
