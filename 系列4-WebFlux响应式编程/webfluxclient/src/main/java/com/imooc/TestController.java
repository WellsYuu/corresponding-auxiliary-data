package com.imooc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

	// 直接注入定义的接口
	@Autowired
	IUserApi userApi;

	@GetMapping("/")
	public void test() {
		// 测试信息提取
		// 不订阅, 不会实际发出请求, 但会进入我们的代理类
		// userApi.getAllUser();
		// userApi.getUserById("11111111");
		// userApi.deleteUserById("222222222");
		// userApi.createUser(
		// Mono.just(User.builder().name("xfq").age(33).build()));

		// 直接调用调用 实现调用rest接口的效果
		// Flux<User> users = userApi.getAllUser();
		// users.subscribe(System.out::println);

		String id = "5ac7d44714fb94522c5dde9c";
		userApi.getUserById(id).subscribe(user -> {
			System.out.println("找到用户:" + user);
		}, e -> {
			System.err.println("找不到用户:" + e.getMessage());
		});

		id = "5ad1b77560a0791f046e425c";
		userApi.getUserById(id).subscribe(user -> {
			System.out.println("找到用户:" + user);
		}, e -> {
			System.err.println("找不到用户:" + e.getMessage());
		});
		//
		// userApi.deleteUserById(id).subscribe();

		// 创建用户
		// userApi.createUser(
		// Mono.just(User.builder().name("晓风轻").age(33).build()))
		// .subscribe(System.out::println);

	}

}
