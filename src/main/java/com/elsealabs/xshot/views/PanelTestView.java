package com.elsealabs.xshot.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelTestView extends JFrame
{
	public static void main(String[] args)
	{
		new PanelTestView();
	}
	
	public PanelTestView()
	{
		setSize(500, 500);
		setLayout(new BorderLayout());
		
		JScrollPane sp = new JScrollPane();
		add(sp, BorderLayout.CENTER);
		
		JPanel container = new JPanel();
		container.setBackground(Color.RED);
		container.setSize(20, 20);
		container.setPreferredSize(new Dimension(20, 20));
		container.setLayout(null);
		
		JPanel inner = new JPanel();
		inner.setBackground(Color.GREEN);
		inner.setSize(10, 10);
		inner.setPreferredSize(new Dimension(10, 10));
		inner.setLayout(null);
		
		inner.setBounds(10, 10, 10, 10);
		
		container.add(inner);
		
		sp.getViewport().add(container);
		
		JFrame frame = new JFrame();
		frame.setSize(100, 400);
		
		JButton button = new JButton("Zoom");
		button.addActionListener(x -> {
			container.setSize(container.getSize().width + 10, container.getSize().height + 10);
			container.setPreferredSize(new Dimension(container.getSize().width + 10, container.getSize().height + 10));
			inner.setBounds(inner.getX() + 10, inner.getY() + 10, inner.getWidth() + 10, inner.getHeight() + 10);
		});
		
		frame.add(button);
		frame.setVisible(true);
		
		setVisible(true);
	}
}