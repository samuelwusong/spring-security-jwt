package com.samuel.spring.jwt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		if ("username".equals(username)) {
			return new User("username", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", roles);
		} else if ("admin".equals(username)) {
			roles=Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
			return new User("admin","$2a$10$0Nv/Qa7m8DdjWpBS2XRZWeP8rWDB7OdScb2grQSRDS9I9fWWlBNG2",roles);
		}else if ("user".equals(username)) {
			roles=Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
			return new User("user","$2a$10$5VikX1NNQFL9f.N7Ta5wVuBL5HuPi7ro5Q3UZYGVOCURwiotGrVCS",roles);
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
		
	}

}