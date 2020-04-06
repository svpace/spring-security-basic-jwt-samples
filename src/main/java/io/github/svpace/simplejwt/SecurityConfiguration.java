package io.github.svpace.simplejwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
@Order(2)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	public static final String SECRET = "K1Lc299vXlD9w3/m64ligzjelT04FiyD4nbvYROo4Tzq4gs5wS7n04GEidSUaFZz/CJfBJ32LeDQMhlvVM6tKA==";

    @Bean
    public SecretKey secretKey()  {
        var secret = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(secret, 0, secret.length, "AES");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
    	return NimbusJwtDecoder.withSecretKey(secretKey()).build();
    }
  
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	http.oauth2ResourceServer().jwt();
    	http.authorizeRequests().anyRequest().authenticated();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        var encoder = passwordEncoder();
        auth.inMemoryAuthentication()
        	.withUser("user").password(encoder.encode("password")).authorities("USER")
        	.and()
        	.withUser("manager").password("password").authorities("ADMIN");
    }


}
