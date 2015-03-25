package com.elsealabs.xshot.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.security.AlgorithmParameterGenerator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ViewMainModern extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JPanel titlePanel;
	private JPanel containerPanel;
	
	private Timer  timerAlpha;
	private float  alpha = 0f;
	
	private int    animationState = 0;
	private double target;
	private double ypos;
	private Point  pos;
	
	private Font font;
	private Font fontLarge;

	public ViewMainModern()
	{
		_loadResources();
		build();
	}
	
	private void _loadResources()
	{
		try
		{
			System.setProperty("awt.useSystemAAFontSettings","on");
			System.setProperty("swing.aatext", "true");
			
			File file = new File("res/font.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, file);
			fontLarge = font.deriveFont(40f);
		}
		catch (FontFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void build()
	{
		setUndecorated(true);
		setSize(600, 230);
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel();
		setContentPane(mainPanel);
		
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
		
		JPanel buttonPanelContainer = new JPanel();
		buttonPanelContainer.setLayout(new BorderLayout());
		containerPanel.add(buttonPanelContainer);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanelContainer.add(buttonPanel, BorderLayout.CENTER);
		
		JPanel button_freeform = new JPanel();
			button_freeform.setLayout(new BorderLayout());
			button_freeform.setBackground(new Color(130, 24, 24));
			
			ModernLabel label_freeform = new ModernLabel("freeform", new Color(43, 6, 6), fontLarge);
			button_freeform.add(label_freeform, BorderLayout.CENTER);
			
			button_freeform.addMouseListener(new MouseListener()
			{				
				@Override
				public void mouseExited(MouseEvent arg0)
				{
					button_freeform.setBackground(new Color(130, 24, 24));
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0)
				{
					button_freeform.setBackground(new Color(145, 38, 38));
				}
				
				@Override
				public void mousePressed(MouseEvent arg0)
				{
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					System.out.println("EVENT");
				}
				
				@Override
				public void mouseReleased(MouseEvent arg0)
				{
				}
				
			});
		buttonPanel.add(button_freeform);
		
		JPanel button_fullscreen = new JPanel();
			button_fullscreen.setLayout(new BorderLayout());
			button_fullscreen.setBackground(new Color(178, 52, 52));
			
			ModernLabel label_fullscreen = new ModernLabel("fullscreen", new Color(130, 24, 24), fontLarge);
			button_fullscreen.add(label_fullscreen, BorderLayout.CENTER);
		buttonPanel.add(button_fullscreen);
		
		JPanel button_rapid = new JPanel();
			button_rapid.setLayout(new BorderLayout());
			button_rapid.setBackground(new Color(226, 86, 86));
			
			ModernLabel label_rapid = new ModernLabel("rapid", new Color(178, 52, 52), fontLarge);
			button_rapid.add(label_rapid, BorderLayout.CENTER);
		buttonPanel.add(button_rapid);
		
		setOpacity(0.0f);
		setVisible(true);
		
		startAnimations();
	}
	
	public void startAnimations()
	{
		timerAlpha = new Timer(5, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (animationState == 0)
				{
					pos    = getLocation();
					ypos   = pos.getY();
					target = ypos - 100;
					
					animationState = 1;
				}
				
				if (animationState == 1)
				{
					double newpos = ypos += (target - ypos) / 5;
					pos.setLocation(pos.getX(), newpos);
					setLocation(pos);
				}
				
				if (alpha < 0.95f)
				{
					alpha += 0.05f;
					setOpacity(alpha);
				}
				else
				{
					setOpacity(1.0f);
					timerAlpha.stop();
				}
			}
		});
		timerAlpha.start();
	}
	
}