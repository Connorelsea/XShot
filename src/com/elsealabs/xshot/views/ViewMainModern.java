package com.elsealabs.xshot.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewMainModern extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JPanel titlePanel;
	private JPanel containerPanel;

	public ViewMainModern()
	{
		build();
	}
	
	public void build()
	{
		setUndecorated(true);
		setSize(550, 250);
		setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		add(mainPanel);
		
		mainPanel.setBackground(new Color(43, 6, 6));
		mainPanel.setLayout(new BorderLayout());
		
		titlePanel = new JPanel();
		titlePanel.setBackground(new Color(43, 6, 6));
		titlePanel.setPreferredSize(new Dimension(titlePanel.getPreferredSize().width, 60));
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		
		containerPanel = new JPanel();
		containerPanel.setLayout(new CardLayout());
		containerPanel.setBackground(new Color(130, 24, 24));
		mainPanel.add(containerPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
}