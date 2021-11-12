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
package org.apache.zookeeper.inspector.gui.nodeviewer;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.apache.zookeeper.inspector.logger.LoggerFactory;
import org.apache.zookeeper.inspector.manager.ZooInspectorNodeManager;

/**
 * @author CGSmithe
 * 
 */
public class NodeViewerMetaData extends ZooInspectorNodeViewer
{
	private ZooInspectorNodeManager zooInspectorManager;
	private final JPanel metaDataPanel;
	private String selectedNode;

	/**
	 * 
	 */
	public NodeViewerMetaData()
	{
		this.setLayout(new BorderLayout());
		this.metaDataPanel = new JPanel();
		this.metaDataPanel.setBackground(Color.WHITE);
		JScrollPane scroller = new JScrollPane(this.metaDataPanel);
		this.add(scroller, BorderLayout.CENTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.zookeeper.inspector.gui.nodeviewer.ZooInspectorNodeViewer#
	 * getTitle()
	 */
	@Override
	public String getTitle()
	{
		return "Node Metadata";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.zookeeper.inspector.gui.nodeviewer.ZooInspectorNodeViewer#
	 * nodeSelectionChanged(java.util.Set)
	 */
	@Override
	public void nodeSelectionChanged(List<String> selectedNodes)
	{
		this.metaDataPanel.removeAll();
		if (selectedNodes.size() > 0)
		{
			this.selectedNode = selectedNodes.get(0);
			SwingWorker<Map<String, String>, Void> worker = new SwingWorker<Map<String, String>, Void>()
			{

				@Override
				protected Map<String, String> doInBackground() throws Exception
				{
					return NodeViewerMetaData.this.zooInspectorManager
							.getNodeMeta(NodeViewerMetaData.this.selectedNode);
				}

				@Override
				protected void done()
				{
					Map<String, String> data = null;
					try
					{
						data = get();
					}
					catch (InterruptedException e)
					{
						data = new HashMap<String, String>();
						LoggerFactory.getLogger().error(
								"Error retrieving meta data for node: "
										+ NodeViewerMetaData.this.selectedNode, e);
					}
					catch (ExecutionException e)
					{
						data = new HashMap<String, String>();
						LoggerFactory.getLogger().error(
								"Error retrieving meta data for node: "
										+ NodeViewerMetaData.this.selectedNode, e);
					}
					int numRows = data.size() * 2 + 1;
					double[] rows = new double[numRows];
					for (int i = 0; i < numRows; i++)
					{
						if (i % 2 == 0)
						{
							rows[i] = 5;
						}
						else
						{
							rows[i] = TableLayout.PREFERRED;
						}
					}
					NodeViewerMetaData.this.metaDataPanel.setLayout(new TableLayout(new double[] {
							10, TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 10 }, rows));
					int i = 0;
					for (Map.Entry<String, String> entry : data.entrySet())
					{
						int rowPos = 2 * i + 1;
						JLabel label = new JLabel(entry.getKey());
						JTextField text = new JTextField(entry.getValue());
						text.setEditable(false);
						NodeViewerMetaData.this.metaDataPanel.add(label, "1," + rowPos);
						NodeViewerMetaData.this.metaDataPanel.add(text, "3," + rowPos);
						i++;
					}
					NodeViewerMetaData.this.metaDataPanel.revalidate();
					NodeViewerMetaData.this.metaDataPanel.repaint();
				}
			};
			worker.execute();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.zookeeper.inspector.gui.nodeviewer.ZooInspectorNodeViewer#
	 * setZooInspectorManager
	 * (org.apache.zookeeper.inspector.manager.ZooInspectorNodeManager)
	 */
	@Override
	public void setZooInspectorManager(ZooInspectorNodeManager zooInspectorManager)
	{
		this.zooInspectorManager = zooInspectorManager;
	}

}
