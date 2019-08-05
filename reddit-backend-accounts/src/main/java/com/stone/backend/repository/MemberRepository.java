package com.stone.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stone.backend.domain.Member;

public interface MemberRepository extends MongoRepository<Member, String>{
	
	public Member findByUsername(String username);
	
	public Long countByUsername(String username);
}
