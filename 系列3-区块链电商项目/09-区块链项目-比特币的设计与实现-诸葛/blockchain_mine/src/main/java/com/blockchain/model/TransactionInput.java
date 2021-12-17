package com.blockchain.model;

/**
 * 交易输入
 * 
 * @author aaron
 *
 */
public class TransactionInput {

	/**
	 * 前一次交易id
	 */
	private String txId;
	/**
	 * 交易金额
	 */
	private int value;
	/**
	 * 交易签名
	 */
	private String signature;
	/**
	 * 交易发送方的钱包公钥
	 */
	private String publicKey;

	public TransactionInput() {
		super();
	}

	public TransactionInput(String txId, int value, String signature, String publicKey) {
		super();
		this.txId = txId;
		this.value = value;
		this.signature = signature;
		this.publicKey = publicKey;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
