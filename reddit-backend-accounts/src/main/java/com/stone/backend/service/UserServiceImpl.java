package com.stone.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stone.backend.domain.Member;
import com.stone.backend.repository.MemberRepository;

@Service
public class UserServiceImpl implements UserService {

	private MemberRepository memberRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Environment environment;

	@Autowired
	public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment, MemberRepository memberRepository) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
		this.memberRepository = memberRepository; 
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Member user = memberRepository.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException(username);
				
//		String userName = "stone";
//		String pwd = bCryptPasswordEncoder.encode("stone");

		return new User(user.getUsername(), user.getPassword(), true, true, true, true, new ArrayList<>());
	}

}
