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

public class ViewCaptureFreeform extends XView
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Monitor> monitors;
	
	private Rectangle bounds;
	private Capturer  capturer;
	
	private XComponent cmpImage;
	private XComponent cmpSelection;
	private XComponent cmpCursorZoom;
	
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
				cmpImage.paint(gd);
			}
		};
		
		mainPanel.setLayout(null);
		mainPanel.setBorder(null);
		add(mainPanel);
	}
	
	private void defineComponents()
	{
		
		// Component: Image
		// The image of both monitors displayed in the background
		
		cmpImage = new XComponent()
		{
			@Override
			public void paint(Graphics2D gd)
			{
				// Get property values
				Color color = Color.decode(cmpImage.getProperty("Color").getValue());
				float alpha = Float.parseFloat(cmpImage.getProperty("Opacity").getValue());
				boolean visible = cmpImage.getProperty("Visibility").getValue().equals("true") ? true : false;
				
				// Draw image
				image.draw(gd, 0, 0);
				
				if (visible == true)
				{
					// Draw color overlay
					gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
					gd.setColor(color);
					gd.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		
		cmpImage.addProperty(new XProperty(".5", "Opacity", "The transparency of the image overlay"));
		cmpImage.addProperty(new XProperty("000000", "Color", "The color of the image overlay"));
		cmpImage.addProperty(new XProperty("true", "Visibility", "Whether or not the colored overlay is visible"));
		
		// Component: Selection
		// The screenshot selection
		
		cmpSelection = new XComponent()
		{
			@Override
			public void paint(Graphics2D g)
			{
				
			}
		};
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