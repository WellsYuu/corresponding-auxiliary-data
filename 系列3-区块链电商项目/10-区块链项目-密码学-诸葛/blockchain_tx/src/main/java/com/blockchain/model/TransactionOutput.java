package com.blockchain.model;

/**
 * 交易输出
 * 
 * @author aaron
 *
 */
public class TransactionOutput {

	/**
	 * 交易金额
	 */
	private int value;
	/**
	 * 交易接收方的钱包公钥的hash值
	 */
	private String publicKeyHash;

	public TransactionOutput() {
		super();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getPublicKeyHash() {
		return publicKeyHash;
	}

	public void setPublicKeyHash(String publicKeyHash) {
		this.publicKeyHash = publicKeyHash;
	}

	public TransactionOutput(int value, String publicKeyHash) {
		this.value = value;
		this.publicKeyHash = publicKeyHash;
	}
}
