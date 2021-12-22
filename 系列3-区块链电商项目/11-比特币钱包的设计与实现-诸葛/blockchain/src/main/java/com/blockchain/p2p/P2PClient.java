package com.blockchain.p2p;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * p2p客户端
 * 
 * @author aaron
 *
 */
public class P2PClient {
	
	private P2PService p2pService;

	public P2PClient(P2PService p2pService) {
		this.p2pService = p2pService;
    }

	public void connectToPeer(String peer) {
		try {
			final WebSocketClient socketClient = new WebSocketClient(new URI(peer)) {
				@Override
				public void onOpen(ServerHandshake serverHandshake) {
					p2pService.write(this, p2pService.queryLatestBlockMsg());
					p2pService.write(this, p2pService.queryTransactionMsg());
					p2pService.write(this, p2pService.queryPackedTransactionMsg());
					p2pService.write(this, p2pService.queryWalletMsg());
					p2pService.getSockets().add(this);
				}

				@Override
				public void onMessage(String msg) {
					p2pService.handleMessage(this, msg, p2pService.getSockets());
				}

				@Override
				public void onClose(int i, String msg, boolean b) {
					System.out.println("connection failed");
					p2pService.getSockets().remove(this);
				}

				@Override
				public void onError(Exception e) {
					System.out.println("connection failed");
					p2pService.getSockets().remove(this);
				}
			};
			socketClient.connect();
		} catch (URISyntaxException e) {
			System.out.println("p2p connect is error:" + e.getMessage());
		}
	}

}
