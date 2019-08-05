package com.stone.backend;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.stone.backend.domain.Post;
import com.stone.backend.event.CreatingPost;
import com.stone.backend.repository.PostRepository;

public class PostHandler {
	
	@Autowired
	private PostRepository repo;

	@RabbitListener(queues="reddit.rpc.post")
	public Post createPost(CreatingPost creatingPost) {
		System.out.println("create post:"+creatingPost);
		
		return repo.save(new Post(creatingPost.getContent(), creatingPost.getCreatedBy()));
	}
}
