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
package org.apache.zookeeper.inspector.gui;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.zookeeper.inspector.gui.nodeviewer.ZooInspectorNodeViewer;
import org.apache.zookeeper.inspector.logger.LoggerFactory;
import org.apache.zookeeper.inspector.manager.ZooInspectorManager;

/**
 * @author CGSmithe
 * 
 */
public class ZooInspectorNodeViewersDialog extends JDialog implements ListSelectionListener
{

	private final JButton upButton;
	private final JButton downButton;
	private final JButton removeButton;
	private final JButton addButton;
	private final JList viewersList;
	private final JButton saveFileButton;
	private final JButton loadFileButton;
	private final JButton setDefaultsButton;
	private final JFileChooser fileChooser = new JFileChooser(new File("."));

	/**
	 * @param frame
	 * @param currentViewers
	 * @param listeners
	 * @param manager
	 * 
	 */
	public ZooInspectorNodeViewersDialog(Frame frame,
			final List<ZooInspectorNodeViewer> currentViewers,
			final Collection<NodeViewersChangeListener> listeners, final ZooInspectorManager manager)
	{
		super(frame);
		final List<ZooInspectorNodeViewer> newViewers = new ArrayList<ZooInspectorNodeViewer>(
				currentViewers);
		this.setLayout(new BorderLayout());
		this.setIconImage(ZooInspectorIconResources.getChangeNodeViewersIcon().getImage());
		this.setTitle("About ZooInspector");
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setResizable(true);
		final JPanel panel = new JPanel();
		panel.setLayout(new TableLayout(new double[] { 10, TableLayout.PREFERRED, 5,
				TableLayout.PREFERRED, 5, TableLayout.FILL, TableLayout.PREFERRED, 5,
				TableLayout.PREFERRED, 10 }, new double[] { 10, TableLayout.PREFERRED, 5,
				TableLayout.PREFERRED, 5, TableLayout.PREFERRED, TableLayout.FILL, 5,
				TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 10 }));
		viewersList = new JList();
		DefaultListModel model = new DefaultListModel();
		for (ZooInspectorNodeViewer viewer : newViewers)
		{
			model.addElement(viewer);
		}
		viewersList.setModel(model);
		viewersList.setCellRenderer(new DefaultListCellRenderer()
		{
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index,
					boolean isSelected, boolean cellHasFocus)
			{
				ZooInspectorNodeViewer viewer = (ZooInspectorNodeViewer) value;
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				label.setText(viewer.getTitle());
				return label;
			}
		});
		viewersList.setDropMode(DropMode.INSERT);
		viewersList.enableInputMethods(true);
		viewersList.setDragEnabled(true);
		viewersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		viewersList.getSelectionModel().addListSelectionListener(this);
		viewersList.setTransferHandler(new TransferHandler()
		{

			@Override
			public boolean canImport(TransferHandler.TransferSupport info)
			{
				// we only import NodeViewers
				if (!info.isDataFlavorSupported(ZooInspectorNodeViewer.nodeViewerDataFlavor))
				{
					return false;
				}

				JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
				if (dl.getIndex() == -1)
				{
					return false;
				}
				return true;
			}

			@Override
			public boolean importData(TransferHandler.TransferSupport info)
			{
				JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
				DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
				int index = dl.getIndex();
				boolean insert = dl.isInsert();
				// Get the string that is being dropped.
				Transferable t = info.getTransferable();
				String data;
				try
				{
					data = (String) t.getTransferData(ZooInspectorNodeViewer.nodeViewerDataFlavor);
				}
				catch (Exception e)
				{
					return false;
				}
				try
				{
					ZooInspectorNodeViewer viewer = (ZooInspectorNodeViewer) Class.forName(data)
							.newInstance();
					if (listModel.contains(viewer))
					{
						listModel.removeElement(viewer);
					}
					if (insert)
					{
						listModel.add(index, viewer);
					}
					else
					{
						listModel.set(index, viewer);
					}
					return true;
				}
				catch (Exception e)
				{
					LoggerFactory.getLogger().error("Error instantiating class: " + data, e);
					return false;
				}

			}

			@Override
			public int getSourceActions(JComponent c)
			{
				return MOVE;
			}

			@Override
			protected Transferable createTransferable(JComponent c)
			{
				JList list = (JList) c;
				ZooInspectorNodeViewer value = (ZooInspectorNodeViewer) list.getSelectedValue();
				return value;
			}
		});
		JScrollPane scroller = new JScrollPane(viewersList);
		panel.add(scroller, "1,1,6,6");
		upButton = new JButton(ZooInspectorIconResources.getUpIcon());
		downButton = new JButton(ZooInspectorIconResources.getDownIcon());
		removeButton = new JButton(ZooInspectorIconResources.getDeleteNodeIcon());
		addButton = new JButton(ZooInspectorIconResources.getAddNodeIcon());
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		removeButton.setEnabled(false);
		addButton.setEnabled(true);
		upButton.setToolTipText("Move currently selected node viewer up");
		downButton.setToolTipText("Move currently selected node viewer down");
		removeButton.setToolTipText("Remove currently selected node viewer");
		addButton.setToolTipText("Add node viewer");
		final JTextField newViewerTextField = new JTextField();
		panel.add(upButton, "8,1");
		panel.add(downButton, "8,5");
		panel.add(removeButton, "8,3");
		panel.add(newViewerTextField, "1,8,6,8");
		panel.add(addButton, "8,8");
		upButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
				ZooInspectorNodeViewer viewer = (ZooInspectorNodeViewer) viewersList
						.getSelectedValue();
				int index = viewersList.getSelectedIndex();
				if (listModel.contains(viewer))
				{
					listModel.removeElementAt(index);
					listModel.insertElementAt(viewer, index - 1);
					viewersList.setSelectedValue(viewer, true);
				}
			}
		});
		downButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
				ZooInspectorNodeViewer viewer = (ZooInspectorNodeViewer) viewersList
						.getSelectedValue();
				int index = viewersList.getSelectedIndex();
				if (listModel.contains(viewer))
				{
					listModel.removeElementAt(index);
					listModel.insertElementAt(viewer, index + 1);
					viewersList.setSelectedValue(viewer, true);
				}
			}
		});
		removeButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
				ZooInspectorNodeViewer viewer = (ZooInspectorNodeViewer) viewersList
						.getSelectedValue();
				int index = viewersList.getSelectedIndex();
				if (listModel.contains(viewer))
				{
					listModel.removeElement(viewer);
					viewersList.setSelectedIndex(index == listModel.size() ? index - 1 : index);
				}
			}
		});
		addButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				String className = newViewerTextField.getText();
				if (className == null || className.length() == 0)
				{
					JOptionPane
							.showMessageDialog(
									ZooInspectorNodeViewersDialog.this,
									"Please enter the full class name for a Node Viewer and click the add button",
									"Input Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					try
					{
						DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
						ZooInspectorNodeViewer viewer = (ZooInspectorNodeViewer) Class.forName(
								className).newInstance();
						if (listModel.contains(viewer))
						{
							JOptionPane
									.showMessageDialog(
											ZooInspectorNodeViewersDialog.this,
											"Node viewer already exists.  Each node viewer can only be added once.",
											"Input Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							listModel.addElement(viewer);
						}
					}
					catch (Exception ex)
					{
						LoggerFactory.getLogger().error(
								"An error occurred while instaniating the node viewer. ", ex);
						JOptionPane.showMessageDialog(ZooInspectorNodeViewersDialog.this,
								"An error occurred while instaniating the node viewer: "
										+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		saveFileButton = new JButton("Save");
		loadFileButton = new JButton("Load");
		setDefaultsButton = new JButton("Set As Defaults");
		saveFileButton.setToolTipText("Save current node viewer configuration to file");
		loadFileButton.setToolTipText("Load node viewer configuration frm file");
		setDefaultsButton.setToolTipText("Set current configuration asd defaults");
		panel.add(saveFileButton, "1,10");
		panel.add(loadFileButton, "3,10");
		panel.add(setDefaultsButton, "6,10");
		saveFileButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int result = fileChooser.showSaveDialog(ZooInspectorNodeViewersDialog.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = fileChooser.getSelectedFile();
					int answer = JOptionPane.YES_OPTION;
					if (selectedFile.exists())
					{
						answer = JOptionPane.showConfirmDialog(ZooInspectorNodeViewersDialog.this,
								"The specified file already exists.  do you want to overwrite it?",
								"Confirm Overwrite", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
					}
					if (answer == JOptionPane.YES_OPTION)
					{
						DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
						List<String> nodeViewersClassNames = new ArrayList<String>();
						Object[] modelContents = listModel.toArray();
						for (Object o : modelContents)
						{
							nodeViewersClassNames.add(((ZooInspectorNodeViewer) o).getClass()
									.getCanonicalName());
						}
						try
						{
							manager.saveNodeViewersFile(selectedFile, nodeViewersClassNames);
						}
						catch (IOException ex)
						{
							LoggerFactory.getLogger().error(
									"Error saving node veiwer configuration from file.", ex);
							JOptionPane.showMessageDialog(ZooInspectorNodeViewersDialog.this,
									"Error saving node veiwer configuration from file: "
											+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		loadFileButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int result = fileChooser.showOpenDialog(ZooInspectorNodeViewersDialog.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						List<String> nodeViewersClassNames = manager
								.loadNodeViewersFile(fileChooser.getSelectedFile());
						List<ZooInspectorNodeViewer> nodeViewers = new ArrayList<ZooInspectorNodeViewer>();
						for (String nodeViewersClassName : nodeViewersClassNames)
						{
							ZooInspectorNodeViewer viewer = (ZooInspectorNodeViewer) Class.forName(
									nodeViewersClassName).newInstance();
							nodeViewers.add(viewer);
						}
						DefaultListModel model = new DefaultListModel();
						for (ZooInspectorNodeViewer viewer : nodeViewers)
						{
							model.addElement(viewer);
						}
						viewersList.setModel(model);
						panel.revalidate();
						panel.repaint();
					}
					catch (Exception ex)
					{
						LoggerFactory.getLogger().error(
								"Error loading node veiwer configuration from file.", ex);
						JOptionPane.showMessageDialog(ZooInspectorNodeViewersDialog.this,
								"Error loading node veiwer configuration from file: "
										+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		setDefaultsButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int answer = JOptionPane.showConfirmDialog(ZooInspectorNodeViewersDialog.this,
						"Are you sure you want to save this configuration as the default?",
						"Confirm Set Defaults", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (answer == JOptionPane.YES_OPTION)
				{
					DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
					List<String> nodeViewersClassNames = new ArrayList<String>();
					Object[] modelContents = listModel.toArray();
					for (Object o : modelContents)
					{
						nodeViewersClassNames.add(((ZooInspectorNodeViewer) o).getClass()
								.getCanonicalName());
					}
					try
					{
						manager.setDefaultNodeViewerConfiguration(nodeViewersClassNames);
					}
					catch (IOException ex)
					{
						LoggerFactory.getLogger().error(
								"Error setting default node veiwer configuration.", ex);
						JOptionPane.showMessageDialog(ZooInspectorNodeViewersDialog.this,
								"Error setting default node veiwer configuration: "
										+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new TableLayout(new double[] { 10, TableLayout.FILL,
				TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 10, TableLayout.FILL },
				new double[] { TableLayout.PREFERRED }));
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ZooInspectorNodeViewersDialog.this.dispose();
				DefaultListModel listModel = (DefaultListModel) viewersList.getModel();
				newViewers.clear();
				Object[] modelContents = listModel.toArray();
				for (Object o : modelContents)
				{
					newViewers.add((ZooInspectorNodeViewer) o);
				}
				currentViewers.clear();
				currentViewers.addAll(newViewers);
				for (NodeViewersChangeListener listener : listeners)
				{
					listener.nodeViewersChanged(currentViewers);
				}
			}
		});
		buttonsPanel.add(okButton, "2,0");
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ZooInspectorNodeViewersDialog.this.dispose();
			}
		});
		buttonsPanel.add(cancelButton, "4,0");
		this.add(panel, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		this.pack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
	 * .ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e)
	{
		int index = viewersList.getSelectedIndex();
		if (index == -1)
		{
			removeButton.setEnabled(false);
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		}
		else
		{
			removeButton.setEnabled(true);
			if (index == 0)
			{
				upButton.setEnabled(false);
			}
			else
			{
				upButton.setEnabled(true);
			}
			if (index == ((DefaultListModel) viewersList.getModel()).getSize())
			{
				downButton.setEnabled(false);
			}
			else
			{
				downButton.setEnabled(true);
			}
		}
	}
}
