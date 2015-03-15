package com.elsealabs.xshot.views;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.elsealabs.xshot.graphics.Capturer;
import com.elsealabs.xshot.graphics.Monitor;
import com.elsealabs.xshot.graphics.XImage;
import com.elsealabs.xshot.property.XProperty;

public class ViewCaptureFreeform extends View
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Monitor> monitors;
	
	private Rectangle bounds;
	private Capturer  capturer;
	
	private XComponent componentImage;
	private XComponent componentSelection;
	private XComponent componentCursorZoom;
	
	private JPanel mainPanel;
	private XImage image;
	
	private Point pointBefore;
	private Point pointNew;
	
	private boolean clicked;
	private boolean pressed;
	private boolean dragging;
	
	private boolean zooming;
	
	public ViewCaptureFreeform(ArrayList<Monitor> monitors)
	{
		this.monitors = monitors;
		
		initCapture(monitors);
		defineComponents();
		initPanel();
		initListeners();
		
		setUndecorated(true);
		setVisible(true);
	}
	
	private void initCapture(ArrayList<Monitor> monitors)
	{
		capturer = Capturer.getInstance();
		image    = capturer.capture(monitors);
		bounds   = capturer.mergeBounds(capturer.getAllMonitors());
		setBounds(bounds);
	}
	
	private void initPanel()
	{
		mainPanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paint(Graphics g)
			{
				Graphics2D gd = (Graphics2D) g;
				componentImage.paint(gd);
			}
		};
		
		mainPanel.setLayout(null);
		mainPanel.setBorder(null);
		add(mainPanel);
	}
	
	private void defineComponents()
	{
		componentImage = new XComponent()
		{
			@Override
			public void paint(Graphics2D gd)
			{
				
				Color color = Color.getColor(componentImage.getProperty("color").getValueCurrent());
				float alpha = Float.parseFloat(componentImage.getProperty("opacity").getValueCurrent());
				
				// Draw image
				image.draw(gd, 0, 0);
				
				// Draw rectangle over image (background)
				gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				gd.setColor(color);
				gd.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		
		componentImage.addProperty(new XProperty(".5", "opacity", "The transparency of the image overlay"));
		componentImage.addProperty(new XProperty("black", "color", "The color of the image overlay"));
	}
	
	private void initListeners()
	{
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				pressed = false;
				dragging = false;
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				clicked = false;
				pressed = true;
				pointBefore = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(pointBefore, mainPanel);
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				clicked = true;
			}
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
				dragging = true;
				pointNew = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(pointNew, mainPanel);
			}
			
		});
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				zooming = false;
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_Z)
				{
					zooming = true;
				}
			}
		});
	}
}