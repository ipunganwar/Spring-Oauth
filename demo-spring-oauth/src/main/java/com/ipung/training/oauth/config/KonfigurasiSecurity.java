package com.ipung.training.oauth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class KonfigurasiSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource ds;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		/*auth.inMemoryAuthentication()
		.withUser("ipung")
		.password("123")
		.roles("ADMIN"); 
		*/
		auth
		.jdbcAuthentication()
		.dataSource(ds)
		.usersByUsernameQuery("select username, password, "
				+ "active as enable from s_users where username=?")
		.authoritiesByUsernameQuery("select u.username, p.user_role from s_users u "
				+ "inner join s_permisions p on u.id = p.id_users "
				+ "where u.username=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.defaultSuccessUrl("/halo")
		.and()
		.logout();
	}
	
	

	
}
