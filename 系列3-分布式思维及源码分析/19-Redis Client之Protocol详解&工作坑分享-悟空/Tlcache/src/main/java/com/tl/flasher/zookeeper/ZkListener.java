package com.tl.flasher.zookeeper;

import org.apache.zookeeper.WatchedEvent;


public interface ZkListener {
	
	public void dataEvent(WatchedEvent event);
}

