package com.stone.redditbackend.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stone.backend.domain.Post;
import com.stone.backend.repository.PostRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {
	
	@Autowired
	private PostRepository postRepository;
	
	@Test
	public void testInsertAndFetch() {
		
		postRepository.deleteAll();
		
		Post post = new Post();
		post.setContent("test");
		post.setCreatedBy("vitual_user");
		postRepository.save(post);
		
		Assert.assertNotNull(post.getId());
		Assert.assertNotNull(post.getCreatedDate());
		
		postRepository.findAll().forEach(System.out::println);
		
	}
	
}
