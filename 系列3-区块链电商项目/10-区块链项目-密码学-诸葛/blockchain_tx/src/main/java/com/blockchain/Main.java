package com.blockchain;

import com.blockchain.block.BlockService;
import com.blockchain.http.HTTPService;
import com.blockchain.p2p.P2PService;

/**
 * 区块链节点启动入口
 * 
 * @author aaron
 *
 */
public class Main {
	public static void main(String[] args) {
		if (args != null && (args.length == 1 || args.length == 2 || args.length == 3)) {
			try {
				BlockService blockService = new BlockService();
				P2PService p2pService = new P2PService(blockService);
				HTTPService httpService = new HTTPService(blockService, p2pService);
				int httpPort = Integer.valueOf(args[0]);
				httpService.initHTTPServer(httpPort);
			} catch (Exception e) {
				System.out.println("startup is error:" + e.getMessage());
			}
		} else {
			System.out.println("usage: java -jar blockchain.jar 8081 7001");
		}
	}

}
