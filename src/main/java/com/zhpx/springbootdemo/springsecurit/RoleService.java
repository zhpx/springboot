package com.zhpx.springbootdemo.springsecurit;

import com.zhpx.springbootdemo.dao.UserDao;
import com.zhpx.springbootdemo.dao.pojo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {


		UserEntity match = new UserEntity();
		match.setUserName(userName);

		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("username", ExampleMatcher.GenericPropertyMatchers.exact()).withIgnorePaths("userId","num","password");

		Example example = Example.of(match,matcher);
		//根据用户名去查询用户权限
		Optional<UserEntity> result = userDao.findOne(example);
		UserEntity userEntity = result.isPresent()?result.get():null;
		if(userEntity == null){
			return null;
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		GrantedAuthority auth1 = new SimpleGrantedAuthority("/user/info");
		GrantedAuthority auth2 = new SimpleGrantedAuthority("/user/add");
		//此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
		grantedAuthorities.add(auth1);
		grantedAuthorities.add(auth2);
		User user = new User(userEntity.getUserName(),userEntity.getPassword(),grantedAuthorities);
		return user;
	}




}
