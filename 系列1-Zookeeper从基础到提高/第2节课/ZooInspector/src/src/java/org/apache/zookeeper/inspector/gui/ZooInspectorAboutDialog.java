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
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import org.apache.zookeeper.inspector.logger.LoggerFactory;

/**
 * @author CGSmithe
 * 
 */
public class ZooInspectorAboutDialog extends JDialog
{
	/**
	 * @param frame
	 * 
	 */
	public ZooInspectorAboutDialog(Frame frame)
	{
		super(frame);
		this.setLayout(new BorderLayout());
		this.setIconImage(ZooInspectorIconResources.getInformationIcon().getImage());
		this.setTitle("About ZooInspector");
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		JPanel panel = new JPanel();
		panel.setLayout(new TableLayout(new double[] { 5, 800, 5 }, new double[] { 5, 170, 5 }));
		JEditorPane aboutPane = new JEditorPane();
		aboutPane.setEditable(false);
		aboutPane.setOpaque(false);
		java.net.URL aboutURL = ZooInspectorAboutDialog.class.getResource("about.html");
		try
		{
			aboutPane.setPage(aboutURL);
		}
		catch (IOException e)
		{
			LoggerFactory.getLogger().error("Error loading about.html, file may be corrupt", e);
		}
		panel.add(aboutPane, "1,1");
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new TableLayout(new double[] { TableLayout.FILL,
				TableLayout.PREFERRED, TableLayout.FILL }, new double[] { TableLayout.PREFERRED }));
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ZooInspectorAboutDialog.this.dispose();
			}
		});
		buttonsPanel.add(okButton, "1,0");
		this.add(panel, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		this.pack();
	}
}
