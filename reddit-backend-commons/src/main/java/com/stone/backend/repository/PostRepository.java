package com.stone.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stone.backend.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
	public List<Post> findAllByOrderByCreatedDateAsc();
}
