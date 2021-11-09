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

/**
 * @author CGSmithe
 * 
 */
public interface ZooInspectorNodeManager extends ZooInspectorReadOnlyManager
{
	/**
	 * @param nodePath
	 * @param data
	 * @return true if the data for the node was successfully updated
	 */
	public boolean setData(String nodePath, String data);
}
