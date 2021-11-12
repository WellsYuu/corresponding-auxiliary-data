/*
 * ZooInspector
 * 
 * Copyright 2010 Colin Goodheart-Smithe

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.apache.zookeeper.inspector.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.inspector.encryption.BasicDataEncryptionManager;
import org.apache.zookeeper.inspector.encryption.DataEncryptionManager;
import org.apache.zookeeper.inspector.logger.LoggerFactory;
import org.apache.zookeeper.retry.ZooKeeperRetry;

/**
 * @author CGSmithe
 * 
 */
public class ZooInspectorManagerImpl implements ZooInspectorManager
{
	private static final String A_VERSION = "ACL Version";
	private static final String C_TIME = "Creation Time";
	private static final String C_VERSION = "Children Version";
	private static final String CZXID = "Creation ID";
	private static final String DATA_LENGTH = "Data Length";
	private static final String EPHEMERAL_OWNER = "Ephemeral Owner";
	private static final String M_TIME = "Last Modified Time";
	private static final String MZXID = "Modified ID";
	private static final String NUM_CHILDREN = "Number of Children";
	private static final String PZXID = "Node ID";
	private static final String VERSION = "Data Version";
	private static final String ACL_PERMS = "Permissions";
	private static final String ACL_SCHEME = "Scheme";
	private static final String ACL_ID = "Id";
	private static final String SESSION_STATE = "Session State";
	private static final String SESSION_ID = "Session ID";
	/**
	 * 
	 */
	public static final String CONNECT_STRING = "hosts";
	/**
	 * 
	 */
	public static final String SESSION_TIMEOUT = "timeout";
	/**
	 * 
	 */
	public static final String DATA_ENCRYPTION_MANAGER = "encryptionManager";

	private static final File defaultsFile = new File("./config/defaultNodeVeiwers.cfg");

	private DataEncryptionManager encryptionManager;
	private String connectString;
	private int sessionTimeout;
	private ZooKeeper zooKeeper;
	private final Map<String, NodeWatcher> watchers = new HashMap<String, NodeWatcher>();

	public boolean connect(Properties connectionProps)
	{
		try
		{
			if (this.zooKeeper == null)
			{
				String connectString = connectionProps.getProperty(CONNECT_STRING);
				String sessionTimeout = connectionProps.getProperty(SESSION_TIMEOUT);
				String encryptionManager = connectionProps.getProperty(DATA_ENCRYPTION_MANAGER);
				if (connectString == null || sessionTimeout == null)
				{
					throw new IllegalArgumentException(
							"Both connect string and session timeout are required.");
				}
				if (encryptionManager == null)
				{
					this.encryptionManager = new BasicDataEncryptionManager();
				}
				else
				{
					Class<?> clazz = Class.forName(encryptionManager);

					if (Arrays.asList(clazz.getInterfaces()).contains(DataEncryptionManager.class))
					{
						this.encryptionManager = (DataEncryptionManager) Class.forName(
								encryptionManager).newInstance();
					}
					else
					{
						throw new IllegalArgumentException(
								"Data encryption manager must implement DataEncryptionManager interface");
					}
				}
				this.connectString = connectString;
				this.sessionTimeout = Integer.valueOf(sessionTimeout);
				this.zooKeeper = new ZooKeeperRetry(connectString, Integer.valueOf(sessionTimeout),
						null);
				((ZooKeeperRetry) this.zooKeeper).setRetryLimit(10);
				return ((ZooKeeperRetry) this.zooKeeper).testConnection();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean disconnect()
	{
		try
		{
			if (this.zooKeeper != null)
			{
				this.zooKeeper.close();
				this.zooKeeper = null;
				return true;
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error(
					"Error occurred while disconnecting from ZooKeeper server", e);
		}
		return false;
	}

	public List<String> getChildren(String nodePath)
	{
		try
		{

			return zooKeeper.getChildren(nodePath, false);
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error(
					"Error occurred retrieving children of node: " + nodePath, e);
		}
		return null;
	}

	public String getData(String nodePath)
	{
		try
		{
			if (nodePath.length() == 0)
			{
				nodePath = "/";
			}
			Stat s = zooKeeper.exists(nodePath, false);
			if (s != null)
			{
				return this.encryptionManager.decryptData(zooKeeper.getData(nodePath, false, s));
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error("Error occurred getting data for node: " + nodePath, e);
		}
		return null;
	}

	public String getNodeChild(String nodePath, int childIndex)
	{
		try
		{
			Stat s = zooKeeper.exists(nodePath, false);
			if (s != null)
			{
				return this.zooKeeper.getChildren(nodePath, false).get(childIndex);
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error(
					"Error occurred retrieving child " + childIndex + " of node: " + nodePath, e);
		}
		return null;
	}

	public int getNodeIndex(String nodePath)
	{
		int index = nodePath.lastIndexOf("/");
		if (index == -1 || (!nodePath.equals("/") && nodePath.charAt(nodePath.length() - 1) == '/'))
		{
			throw new IllegalArgumentException("Invalid node path: " + nodePath);
		}
		String parentPath = nodePath.substring(0, index);
		String child = nodePath.substring(index + 1);
		if (parentPath != null && parentPath.length() > 0)
		{
			List<String> children = this.getChildren(parentPath);
			if (children != null)
			{
				return children.indexOf(child);
			}
		}
		return -1;
	}

	public List<Map<String, String>> getACLs(String nodePath)
	{
		List<Map<String, String>> returnACLs = new ArrayList<Map<String, String>>();
		try
		{
			if (nodePath.length() == 0)
			{
				nodePath = "/";
			}
			Stat s = zooKeeper.exists(nodePath, false);
			if (s != null)
			{
				List<ACL> acls = zooKeeper.getACL(nodePath, s);
				for (ACL acl : acls)
				{
					Map<String, String> aclMap = new LinkedHashMap<String, String>();
					aclMap.put(ACL_SCHEME, acl.getId().getScheme());
					aclMap.put(ACL_ID, acl.getId().getId());
					StringBuilder sb = new StringBuilder();
					int perms = acl.getPerms();
					boolean addedPerm = false;
					if ((perms & Perms.READ) == Perms.READ)
					{
						sb.append("Read");
						addedPerm = true;
					}
					if (addedPerm)
					{
						sb.append(", ");
					}
					if ((perms & Perms.WRITE) == Perms.WRITE)
					{
						sb.append("Write");
						addedPerm = true;
					}
					if (addedPerm)
					{
						sb.append(", ");
					}
					if ((perms & Perms.CREATE) == Perms.CREATE)
					{
						sb.append("Create");
						addedPerm = true;
					}
					if (addedPerm)
					{
						sb.append(", ");
					}
					if ((perms & Perms.DELETE) == Perms.DELETE)
					{
						sb.append("Delete");
						addedPerm = true;
					}
					if (addedPerm)
					{
						sb.append(", ");
					}
					if ((perms & Perms.ADMIN) == Perms.ADMIN)
					{
						sb.append("Admin");
						addedPerm = true;
					}
					aclMap.put(ACL_PERMS, sb.toString());
					returnACLs.add(aclMap);
				}
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error("Error occurred retrieving ACLs of node: " + nodePath,
					e);
		}
		return returnACLs;
	}

	public Map<String, String> getNodeMeta(String nodePath)
	{
		Map<String, String> nodeMeta = new LinkedHashMap<String, String>();
		try
		{
			if (nodePath.length() == 0)
			{
				nodePath = "/";
			}
			Stat s = zooKeeper.exists(nodePath, false);
			if (s != null)
			{
				nodeMeta.put(A_VERSION, String.valueOf(s.getAversion()));
				nodeMeta.put(C_TIME, String.valueOf(s.getCtime()));
				nodeMeta.put(C_VERSION, String.valueOf(s.getCversion()));
				nodeMeta.put(CZXID, String.valueOf(s.getCzxid()));
				nodeMeta.put(DATA_LENGTH, String.valueOf(s.getDataLength()));
				nodeMeta.put(EPHEMERAL_OWNER, String.valueOf(s.getEphemeralOwner()));
				nodeMeta.put(M_TIME, String.valueOf(s.getMtime()));
				nodeMeta.put(MZXID, String.valueOf(s.getMzxid()));
				nodeMeta.put(NUM_CHILDREN, String.valueOf(s.getNumChildren()));
				nodeMeta.put(PZXID, String.valueOf(s.getPzxid()));
				nodeMeta.put(VERSION, String.valueOf(s.getVersion()));
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error(
					"Error occurred retrieving meta data for node: " + nodePath, e);
		}
		return nodeMeta;
	}

	public int getNumChildren(String nodePath)
	{
		try
		{
			Stat s = zooKeeper.exists(nodePath, false);
			if (s != null)
			{
				return s.getNumChildren();
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error(
					"Error occurred getting the number of children of node: " + nodePath, e);
		}
		return -1;
	}

	public boolean hasChildren(String nodePath)
	{
		return getNumChildren(nodePath) > 0;
	}

	public boolean isAllowsChildren(String nodePath)
	{
		try
		{
			Stat s = zooKeeper.exists(nodePath, false);
			if (s != null)
			{
				return s.getEphemeralOwner() == 0;
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error(
					"Error occurred determining whether node is allowed children: " + nodePath, e);
		}
		return false;
	}

	public Map<String, String> getSessionMeta()
	{
		Map<String, String> sessionMeta = new LinkedHashMap<String, String>();
		try
		{
			if (zooKeeper != null)
			{

				sessionMeta.put(SESSION_ID, String.valueOf(zooKeeper.getSessionId()));
				sessionMeta.put(SESSION_STATE, String.valueOf(zooKeeper.getState().toString()));
				sessionMeta.put(CONNECT_STRING, this.connectString);
				sessionMeta.put(SESSION_TIMEOUT, String.valueOf(this.sessionTimeout));
			}
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error("Error occurred retrieving session meta data.", e);
		}
		return sessionMeta;
	}

	public boolean createNode(String parent, String nodeName)
	{
		try
		{
			String[] nodeElements = nodeName.split("/");
			for (String nodeElement : nodeElements)
			{
				String node = parent + "/" + nodeElement;
				Stat s = zooKeeper.exists(node, false);
				if (s == null)
				{
					zooKeeper.create(node, this.encryptionManager.encryptData(null),
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					parent = node;
				}
			}
			return true;
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error(
					"Error occurred creating node: " + parent + "/" + nodeName, e);
		}
		return false;
	}

	public boolean deleteNode(String nodePath)
	{
		try
		{
			Stat s = zooKeeper.exists(nodePath, false);
			if (s != null)
			{
				List<String> children = zooKeeper.getChildren(nodePath, false);
				for (String child : children)
				{
					String node = nodePath + "/" + child;
					deleteNode(node);
				}
				zooKeeper.delete(nodePath, -1);
			}
			return true;
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error("Error occurred deleting node: " + nodePath, e);
		}
		return false;
	}

	public boolean setData(String nodePath, String data)
	{
		try
		{
			zooKeeper.setData(nodePath, this.encryptionManager.encryptData(data), -1);
			return true;
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger().error("Error occurred setting data for node: " + nodePath, e);
		}
		return false;
	}

	public Pair<Map<String, List<String>>, Map<String, String>> getConnectionPropertiesTemplate()
	{
		Map<String, List<String>> template = new LinkedHashMap<String, List<String>>();
		template.put(CONNECT_STRING, Arrays.asList(new String[] { "localhost:2181" }));
		template.put(SESSION_TIMEOUT, Arrays.asList(new String[] { "5000" }));
		template.put(DATA_ENCRYPTION_MANAGER, Arrays.asList(new String[] { "org.apache.zookeeper."
				+ "inspector.encryption.BasicDataEncryptionManager" }));
		Map<String, String> labels = new LinkedHashMap<String, String>();
		labels.put(CONNECT_STRING, "Connect String");
		labels.put(SESSION_TIMEOUT, "Session Timeout");
		labels.put(DATA_ENCRYPTION_MANAGER, "Data Encryption Manager");
		return new Pair<Map<String, List<String>>, Map<String, String>>(template, labels);
	}

	public void addWatchers(List<String> selectedNodes, NodeListener nodeListener)
	{
		// add watcher for each node and add node to collection of
		// watched nodes
		for (String node : selectedNodes)
		{
			if (!watchers.containsKey(node))
			{
				try
				{
					watchers.put(node, new NodeWatcher(node, nodeListener, zooKeeper));
				}
				catch (Exception e)
				{
					LoggerFactory.getLogger().error(
							"Error occured adding node watcher for node: " + node, e);
				}
			}
		}
	}

	public void removeWatchers(List<String> selectedNodes)
	{
		// remove watcher for each node and remove node from
		// collection of watched nodes
		for (String node : selectedNodes)
		{
			if (watchers.containsKey(node))
			{
				NodeWatcher watcher = watchers.remove(node);
				if (watcher != null)
				{
					watcher.stop();
				}
			}
		}
	}

	/**
	 * @author Colin
	 * 
	 */
	public class NodeWatcher implements Watcher
	{

		private final String nodePath;
		private final NodeListener nodeListener;
		private final ZooKeeper zookeeper;
		private boolean closed = false;

		/**
		 * @param nodePath
		 * @param nodeListener
		 * @param zookeeper
		 * @throws InterruptedException
		 * @throws KeeperException
		 */
		public NodeWatcher(String nodePath, NodeListener nodeListener, ZooKeeper zookeeper)
				throws KeeperException, InterruptedException
		{
			this.nodePath = nodePath;
			this.nodeListener = nodeListener;
			this.zookeeper = zookeeper;
			Stat s = zooKeeper.exists(nodePath, this);
			if (s != null)
			{
				zookeeper.getChildren(nodePath, this);
			}
		}

		public void process(WatchedEvent event)
		{
			if (!closed)
			{
				try
				{
					if (event.getType() != EventType.NodeDeleted)
					{

						Stat s = zooKeeper.exists(nodePath, this);
						if (s != null)
						{
							zookeeper.getChildren(nodePath, this);
						}
					}
				}
				catch (Exception e)
				{
					LoggerFactory.getLogger().error(
							"Error occured re-adding node watcherfor node " + nodePath, e);
				}
				nodeListener.processEvent(event.getPath(), event.getType().name(), null);
			}
		}

		/**
		 * 
		 */
		public void stop()
		{
			this.closed = true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.zookeeper.inspector.manager.ZooInspectorManager#
	 * loadNodeViewersFile(java.io.File)
	 */
	public List<String> loadNodeViewersFile(File selectedFile) throws IOException
	{
		List<String> result = new ArrayList<String>();
		if (defaultsFile.exists())
		{
			FileReader reader = new FileReader(selectedFile);
			try
			{
				BufferedReader buff = new BufferedReader(reader);
				try
				{
					while (buff.ready())
					{
						String line = buff.readLine();
						if (line != null && line.length() > 0)
						{
							result.add(line);
						}
					}
				}
				finally
				{
					buff.close();
				}
			}
			finally
			{
				reader.close();
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.zookeeper.inspector.manager.ZooInspectorManager#
	 * saveNodeViewersFile(java.io.File, java.util.List)
	 */
	public void saveNodeViewersFile(File selectedFile, List<String> nodeViewersClassNames)
			throws IOException
	{
		if (!selectedFile.exists())
		{
			selectedFile.createNewFile();
		}
		FileWriter writer = new FileWriter(selectedFile);
		try
		{
			BufferedWriter buff = new BufferedWriter(writer);
			try
			{
				for (String nodeViewersClassName : nodeViewersClassNames)
				{
					buff.append(nodeViewersClassName);
					buff.append("\n");
				}
			}
			finally
			{
				buff.flush();
				buff.close();
			}
		}
		finally
		{
			writer.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.zookeeper.inspector.manager.ZooInspectorManager#
	 * setDefaultNodeViewerConfiguration(java.io.File, java.util.List)
	 */
	public void setDefaultNodeViewerConfiguration(List<String> nodeViewersClassNames)
			throws IOException
	{
		File defaultDir = defaultsFile.getParentFile();
		if (!defaultDir.exists())
		{
			defaultDir.mkdirs();
		}
		saveNodeViewersFile(defaultsFile, nodeViewersClassNames);
	}

	public List<String> getDefaultNodeViewerConfiguration() throws IOException
	{
		return loadNodeViewersFile(defaultsFile);
	}
}
