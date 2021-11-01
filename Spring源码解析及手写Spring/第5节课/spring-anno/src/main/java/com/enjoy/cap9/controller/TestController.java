package com.enjoy.cap9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.enjoy.cap9.service.TestService;

@Controller
public class TestController {
	@Autowired
	private TestService testService;
}
