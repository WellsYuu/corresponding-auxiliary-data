package com.enjoylearning.mybatis.build;

import java.math.BigDecimal;
import java.util.Date;

public class RedPacket {
	
	private String publisherName; //发包人

    private String acceptName; //手包人

    private BigDecimal packetAmount; //红包金额

    private int packetType; //红包类型

    private Date pulishPacketTime; //发包时间

    private Date openPacketTime; //抢包时间

    public RedPacket(String publisherName, String acceptName, BigDecimal packetAmount, int packetType, Date pulishPacketTime, Date openPacketTime) {
        this.publisherName = publisherName;
        this.acceptName = acceptName;
        this.packetAmount = packetAmount;
        this.packetType = packetType;
        this.pulishPacketTime = pulishPacketTime;
        this.openPacketTime = openPacketTime;
    }

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getAcceptName() {
		return acceptName;
	}

	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	public BigDecimal getPacketAmount() {
		return packetAmount;
	}

	public void setPacketAmount(BigDecimal packetAmount) {
		this.packetAmount = packetAmount;
	}

	public int getPacketType() {
		return packetType;
	}

	public void setPacketType(int packetType) {
		this.packetType = packetType;
	}

	public Date getPulishPacketTime() {
		return pulishPacketTime;
	}

	public void setPulishPacketTime(Date pulishPacketTime) {
		this.pulishPacketTime = pulishPacketTime;
	}

	public Date getOpenPacketTime() {
		return openPacketTime;
	}

	public void setOpenPacketTime(Date openPacketTime) {
		this.openPacketTime = openPacketTime;
	}

	@Override
	public String toString() {
		return "RedPacket [publisherName=" + publisherName + ", acceptName="
				+ acceptName + ", packetAmount=" + packetAmount
				+ ", packetType=" + packetType + ", pulishPacketTime="
				+ pulishPacketTime + ", openPacketTime=" + openPacketTime + "]";
	}
    
    
    

}
