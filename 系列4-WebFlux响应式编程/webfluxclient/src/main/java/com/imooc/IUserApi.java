package com.imooc;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiServer("http://localhost:8080/user")
public interface IUserApi {

	@GetMapping("/")
	Flux<User> getAllUser();

	@GetMapping("/{id}")
	Mono<User> getUserById(@PathVariable("id") String id);

	@DeleteMapping("/{id}")
	Mono<Void> deleteUserById(@PathVariable("id") String id);

	@PostMapping("/")
	Mono<User> createUser(@RequestBody Mono<User> user);
}
