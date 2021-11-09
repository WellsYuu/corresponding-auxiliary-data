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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.zookeeper.inspector.logger.LoggerFactory;
import org.apache.zookeeper.inspector.manager.Pair;

/**
 * @author CGSmithe
 * 
 */
public class ZooInspectorConnectionPropertiesDialog extends JDialog
{

	/**
	 * @param connectionPropertiesTemplateAndLabels
	 * @param zooInspectorPanel
	 */
	public ZooInspectorConnectionPropertiesDialog(
			Pair<Map<String, List<String>>, Map<String, String>> connectionPropertiesTemplateAndLabels,
			final ZooInspectorPanel zooInspectorPanel)
	{
		final Map<String, List<String>> connectionPropertiesTemplate = connectionPropertiesTemplateAndLabels
				.getKey();
		final Map<String, String> connectionPropertiesLabels = connectionPropertiesTemplateAndLabels
				.getValue();
		this.setLayout(new BorderLayout());
		this.setTitle("Connection Settings");
		this.setModal(true);
		this.setAlwaysOnTop(true);
		final JPanel options = new JPanel();
		final JFileChooser fileChooser = new JFileChooser();
		int numRows = connectionPropertiesTemplate.size() * 2 + 1;
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
		options.setLayout(new TableLayout(new double[] { 10, TableLayout.PREFERRED, 5,
				TableLayout.PREFERRED, 10 }, rows));
		int i = 0;
		final Map<String, JComponent> components = new HashMap<String, JComponent>();
		for (Entry<String, List<String>> entry : connectionPropertiesTemplate.entrySet())
		{
			int rowPos = 2 * i + 1;
			JLabel label = new JLabel(connectionPropertiesLabels.get(entry.getKey()));
			options.add(label, "1," + rowPos);
			if (entry.getValue().size() == 0)
			{
				JTextField text = new JTextField();
				options.add(text, "3," + rowPos);
				components.put(entry.getKey(), text);
			}
			else if (entry.getValue().size() == 1)
			{
				JTextField text = new JTextField(entry.getValue().get(0));
				options.add(text, "3," + rowPos);
				components.put(entry.getKey(), text);
			}
			else
			{
				List<String> list = entry.getValue();
				JComboBox combo = new JComboBox(list.toArray(new String[list.size()]));
				combo.setSelectedItem(list.get(0));
				options.add(combo, "3," + rowPos);
				components.put(entry.getKey(), combo);
			}
			i++;
		}
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new TableLayout(new double[] { 10, TableLayout.PREFERRED, 5,
				TableLayout.FILL, TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 10 },
				new double[] { TableLayout.PREFERRED }));
		JButton loadPropsFileButton = new JButton("Load from file");
		loadPropsFileButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				int result = fileChooser
						.showOpenDialog(ZooInspectorConnectionPropertiesDialog.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File propsFilePath = fileChooser.getSelectedFile();
					Properties props = new Properties();
					try
					{
						FileReader reader = new FileReader(propsFilePath);
						try
						{
							props.load(reader);
							for (Object key : props.keySet())
							{
								String propsKey = (String) key;
								if (components.containsKey(propsKey))
								{
									JComponent component = components.get(propsKey);
									String value = props.getProperty(propsKey);
									if (component instanceof JTextField)
									{
										((JTextField) component).setText(value);
									}
									else if (component instanceof JComboBox)
									{
										((JComboBox) component).setSelectedItem(value);
									}
								}
							}
						}
						finally
						{
							reader.close();
						}
					}
					catch (IOException ex)
					{
						LoggerFactory.getLogger().error(
								"An Error occirred loading connection properties from file", ex);
						JOptionPane.showMessageDialog(ZooInspectorConnectionPropertiesDialog.this,
								"An Error occirred loading connection properties from file",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					options.revalidate();
					options.repaint();
				}

			}
		});
		buttonsPanel.add(loadPropsFileButton, "1,0");

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				ZooInspectorConnectionPropertiesDialog.this.dispose();
				Properties connectionProps = new Properties();
				for (Entry<String, JComponent> entry : components.entrySet())
				{
					String value = null;
					JComponent component = entry.getValue();
					if (component instanceof JTextField)
					{
						value = ((JTextField) component).getText();
					}
					else if (component instanceof JComboBox)
					{
						value = ((JComboBox) component).getSelectedItem().toString();
					}
					connectionProps.put(entry.getKey(), value);
				}
				zooInspectorPanel.connect(connectionProps);
			}
		});
		buttonsPanel.add(okButton, "4,0");
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				ZooInspectorConnectionPropertiesDialog.this.dispose();
			}
		});
		buttonsPanel.add(cancelButton, "6,0");
		this.add(options, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		this.pack();
	}

}
