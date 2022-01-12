package com.imooc.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.imooc.handlers.UserHandler;

@Configuration
public class AllRouters {

	@Bean
	RouterFunction<ServerResponse> userRouter(UserHandler handler) {
		return nest(
				// 相当于类上面的 @RequestMapping("/user")
				path("/user"),
				// 下面的相当于类里面的 @RequestMapping
				// 得到所有用户
				route(GET("/"), handler::getAllUser)
				// 创建用户
				.andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON_UTF8)),
								handler::createUser)
				// 删除用户
				.andRoute(DELETE("/{id}"), handler::deleteUserById));
	}

}
