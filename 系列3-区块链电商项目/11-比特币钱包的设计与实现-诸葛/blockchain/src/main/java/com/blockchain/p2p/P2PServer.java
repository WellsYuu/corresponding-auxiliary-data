package com.blockchain.p2p;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * p2p服务端
 * 
 * @author aaron
 *
 */
public class P2PServer {
	
	private P2PService p2pService;
	
	public P2PServer(P2PService p2pService) {
	    this.p2pService = p2pService;
    }

	public void initP2PServer(int port) {
		final WebSocketServer socketServer = new WebSocketServer(new InetSocketAddress(port)) {
			public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
				p2pService.getSockets().add(webSocket);
			}

			public void onClose(WebSocket webSocket, int i, String s, boolean b) {
				System.out.println("connection failed to peer:" + webSocket.getRemoteSocketAddress());
				p2pService.getSockets().remove(webSocket);
			}

			public void onMessage(WebSocket webSocket, String msg) {
				p2pService.handleMessage(webSocket, msg, p2pService.getSockets());
			}

			public void onError(WebSocket webSocket, Exception e) {
				System.out.println("connection failed to peer:" + webSocket.getRemoteSocketAddress());
				p2pService.getSockets().remove(webSocket);
			}

			public void onStart() {

			}
		};
		socketServer.start();
		System.out.println("listening websocket p2p port on: " + port);
	}

}
