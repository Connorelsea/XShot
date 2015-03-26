package com.elsealabs.xshot.views;

import com.elsealabs.xshot.DisplayRecCaptureBeta;
import com.elsealabs.xshot.graphics.Capturer;
import com.elsealabs.xshot.graphics.XImage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
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

	private Capturer capturer;

	// TODO: Create color packages in the future

	private Color RED_DARK      = new Color(43, 6, 6);
	private Color RED_DARK_MED  = new Color(130, 24, 24);
	private Color RED_LIGHT_MED = new Color(178, 52, 52);
	private Color RED_LIGHT     = new Color(226, 86, 86);

	private Color HIGHLIGHT_RED_DARK_MED  = new Color(160, 30, 30);
	private Color HIGHLIGHT_RED_LIGHT_MED = new Color(197, 53, 53);
	private Color HIGHLIGHT_RED_LIGHT     = new Color(255, 119, 116);

	public ViewMainModern()
	{
		capturer = Capturer.getInstance(this);
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
		titlePanel.setBackground(RED_DARK);
		titlePanel.setPreferredSize(new Dimension(titlePanel.getPreferredSize().width, 60));
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		
		containerPanel = new JPanel();
		containerPanel.setLayout(new CardLayout());
		containerPanel.setBackground(RED_DARK_MED);
		mainPanel.add(containerPanel, BorderLayout.CENTER);
		
		JPanel buttonPanelContainer = new JPanel();
		buttonPanelContainer.setLayout(new BorderLayout());
		containerPanel.add(buttonPanelContainer);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanelContainer.add(buttonPanel, BorderLayout.CENTER);

		ModernButton button_freeform = new ModernButton(
				new ModernLabel("freeform", RED_DARK, fontLarge),
				RED_DARK_MED,
				HIGHLIGHT_RED_DARK_MED,
				a -> {
					XImage image = capturer.capture(capturer.getAllMonitors());
					DisplayRecCaptureBeta disp = new DisplayRecCaptureBeta(image);
					disp.build();
				});

		buttonPanel.add(button_freeform);

		ModernButton button_fullscreen = new ModernButton(
				new ModernLabel("fullscreen", RED_DARK_MED, fontLarge),
				RED_LIGHT_MED,
				HIGHLIGHT_RED_LIGHT_MED,
				a -> {

				});
		buttonPanel.add(button_fullscreen);

		ModernButton button_rapid = new ModernButton(
				new ModernLabel("rapid", RED_LIGHT_MED, fontLarge),
				RED_LIGHT,
				HIGHLIGHT_RED_LIGHT,
				a -> {

				});
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