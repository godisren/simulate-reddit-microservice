package com.stone.backend.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stone.backend.domain.Post;
import com.stone.backend.event.CreatingPost;

@Component
public class PostClient {
	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange exchange;

	public Post createPost(String content, String createdBy) throws JsonProcessingException {
		CreatingPost post = new CreatingPost(content, createdBy);
		System.out.println(" [x] Requesting " + post);
		
		ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(post);
        
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(),messageProperties);
		
		Post response = (Post)template.convertSendAndReceive(exchange.getName(), "rpc", message);

		System.out.println(" [.] Got '" + response + ", class="+response.getClass().getName());
		
		return response;
	}
}
