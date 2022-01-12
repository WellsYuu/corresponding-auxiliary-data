package com.imooc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.imooc.domain.User;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Webflux2ApplicationTests {

	private WebTestClient client;

	@Before
	public void init() {
		this.client = WebTestClient.bindToServer().baseUrl("http://localhost:8080/user").build();
	}
	
	@Test
	public void testGetAll() {
		this.client.get().uri("/")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(User.class);
	}

	@Test
	public void testCreateUser() {   
		this.client.post().uri("/")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(User.builder().name("builder").age(22).build()), User.class)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody(User.class);
	}
}
