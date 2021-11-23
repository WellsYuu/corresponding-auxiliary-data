package com.gupaoedu.rpc.provider;

import com.gupaoedu.rpc.api.IRpcHello;

public class RpcHello implements IRpcHello {

	@Override
	public String hello(String name) {
		return "Hello , " + name + "!";
	}

}
