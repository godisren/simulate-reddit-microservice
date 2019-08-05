package com.stone.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stone.backend.domain.Post;
import com.stone.backend.mq.PostClient;
import com.stone.backend.repository.PostRepository;

@RestController()
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private PostClient postClient;
	
	@PostMapping()
	public Post create(@RequestHeader("username") String username, @RequestBody Post content) throws JsonProcessingException {
		return postClient.createPost(content.getContent(), username);
	}
	
	@GetMapping()
	public List<Post> list(){
		return postRepo.findAllByOrderByCreatedDateAsc();
	}
}
