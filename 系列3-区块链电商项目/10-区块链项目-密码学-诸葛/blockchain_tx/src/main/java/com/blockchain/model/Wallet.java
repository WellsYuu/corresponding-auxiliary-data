package com.blockchain.model;

import java.util.Map;

import com.blockchain.security.CryptoUtil;
import com.blockchain.security.RSACoder;

/**
 * 钱包
 * 
 * @author aaron
 *
 */
public class Wallet {

	/**
	 * 公钥
	 */
	private String publicKey;
	/**
	 * 私钥
	 */
	private String privateKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Wallet() {
    }
	
	/**
	 * 只包含公钥的钱包，用来给其他节点使用，其他节点在转账时需要用到
	 * 
	 * @param publicKey
	 */
	public Wallet(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public Wallet(String publicKey, String privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public static Wallet generateWallet() {
		Map<String, Object> initKey;
		try {
			// 本地生成公私钥对
			initKey = RSACoder.initKey();
			String publicKey = RSACoder.getPublicKey(initKey);
			String privateKey = RSACoder.getPrivateKey(initKey);
			return new Wallet(publicKey, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取钱包地址
	 * 
	 * @return
	 */
	public String getAddress() {
		String publicKeyHash = hashPubKey(publicKey);
		return CryptoUtil.MD5(publicKeyHash);   
	}

	/**
	 * 根据钱包公钥生成钱包地址
	 * 
	 * @param publicKey
	 * @return
	 */
	public static String getAddress(String publicKey) {
		String publicKeyHash = hashPubKey(publicKey);
		return CryptoUtil.MD5(publicKeyHash);
	}

	/**
	 * 获取钱包公钥hash
	 * 
	 * @return
	 */
	public String getHashPubKey() {
		return CryptoUtil.SHA256(publicKey);
	}

	/**
	 * 生成钱包公钥hash
	 * 
	 * @param publicKey
	 * @return
	 */
	public static String hashPubKey(String publicKey) {
		return CryptoUtil.SHA256(publicKey);
	}

}
