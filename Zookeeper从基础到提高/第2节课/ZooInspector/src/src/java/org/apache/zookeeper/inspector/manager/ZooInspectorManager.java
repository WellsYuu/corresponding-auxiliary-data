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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * @author CGSmithe
 * 
 */
public interface ZooInspectorManager extends ZooInspectorNodeManager, ZooInspectorNodeTreeManager
{

	/**
	 * @param connectionProps
	 * @return true if successfully connected
	 */
	public boolean connect(Properties connectionProps);

	/**
	 * @return true if successfully disconnected
	 */
	public boolean disconnect();

	/**
	 * @return a {@link Pair} containing the following:
	 *         <ul>
	 *         <li>a {@link Map} of property keys to list of possible values. If
	 *         the list size is 1 the value is taken to be the default value for
	 *         a {@link JTextField}. If the list size is greater than 1, the
	 *         values are taken to be the possible options to show in a
	 *         {@link JComboBox} with the first selected as default.</li>
	 *         <li>a {@link Map} of property keys to the label to show on the UI
	 *         </li>
	 *         <ul>
	 * 
	 */
	public Pair<Map<String, List<String>>, Map<String, String>> getConnectionPropertiesTemplate();

	/**
	 * @param selectedNodes
	 * @param nodeListener
	 */
	public void addWatchers(List<String> selectedNodes, NodeListener nodeListener);

	/**
	 * @param selectedNodes
	 */
	public void removeWatchers(List<String> selectedNodes);

	/**
	 * @param selectedFile
	 * @return nodeViewers
	 * @throws IOException
	 */
	public List<String> loadNodeViewersFile(File selectedFile) throws IOException;

	/**
	 * @param selectedFile
	 * @param nodeViewersClassNames
	 * @throws IOException
	 */
	public void saveNodeViewersFile(File selectedFile, List<String> nodeViewersClassNames)
			throws IOException;

	/**
	 * @param nodeViewersClassNames
	 * @throws IOException
	 */
	public void setDefaultNodeViewerConfiguration(List<String> nodeViewersClassNames)
			throws IOException;

	/**
	 * @return nodeViewers
	 * @throws IOException
	 */
	List<String> getDefaultNodeViewerConfiguration() throws IOException;

}
