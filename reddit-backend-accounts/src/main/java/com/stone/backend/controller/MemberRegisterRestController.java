package com.stone.backend.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stone.backend.domain.Member;
import com.stone.backend.dto.CreateMemberRequest;
import com.stone.backend.dto.CreatedMemberResponse;
import com.stone.backend.exception.MemberDuplicatedException;
import com.stone.backend.repository.MemberRepository;

@RestController()
public class MemberRegisterRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberRepository mbrRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Environment environment;
	
	@PostMapping(path = "${register.url.path}")
    public CreatedMemberResponse register(@RequestBody CreateMemberRequest createMember) {
		
		if(createMember.getUsername()==null || createMember.getPassword()==null) {
			throw new IllegalArgumentException("the username or password can not be empty.");
		}
		
		if(mbrRepo.findByUsername(createMember.getUsername())!=null) {
			throw new MemberDuplicatedException();
		}
		
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			Member member = modelMapper.map(createMember, Member.class);
			
			member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
			member = mbrRepo.save(member);
			return new CreatedMemberResponse(member.getId(), member.getUsername());
		} catch (Exception e) {
			logger.error("registration failed",e);
			throw new RuntimeException("create member failed.");
		}
    }
}
