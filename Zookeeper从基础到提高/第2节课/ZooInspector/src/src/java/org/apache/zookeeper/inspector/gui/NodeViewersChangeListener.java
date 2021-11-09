/**
 * 
 */
package org.apache.zookeeper.inspector.gui;

import java.util.List;

import org.apache.zookeeper.inspector.gui.nodeviewer.ZooInspectorNodeViewer;

/**
 * @author CGSmithe
 * 
 */
public interface NodeViewersChangeListener
{
	/**
	 * @param newViewers
	 */
	public void nodeViewersChanged(List<ZooInspectorNodeViewer> newViewers);
}
