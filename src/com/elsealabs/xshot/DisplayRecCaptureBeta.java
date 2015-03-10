package com.elsealabs.xshot;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DisplayRecCaptureBeta extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Rectangle bounds;
	
	private Capturer capturer;
	private XImage   image;
	
	private ComponenetSelection componentSelection;
	private JPanel panel;
	
	private Point pointBefore;
	private Point pointNew;
	
	private boolean clicked;
	private boolean pressed;
	private boolean dragging;
	
	public DisplayRecCaptureBeta(XImage image)
	{
		this.image = image;
		this.componentSelection = ComponenetSelection.DEFAULT_MODERN;
	}
	
	public void build()
	{
		
		capturer = Capturer.getInstance();
		bounds   = capturer.mergeBounds(capturer.getAllMonitors());
		
		setUndecorated(true);
		setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		
		_addListeners();
		_createPanel();
		
		add(panel);
		
		setVisible(true);
	}
	
	private void _addListeners()
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
				SwingUtilities.convertPointFromScreen(pointBefore, panel);
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				clicked = true;
			}
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				dragging = true;
				pointNew = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(pointNew, panel);
			}
			
		});
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}
		});
		
	}
	
	private void _createPanel()
	{
		panel = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g)
			{
				super.paint(g);
				Graphics2D gd = (Graphics2D) g;
				
				// Draw image
				image.draw(gd, 0, 0);
				
				// Draw rectangle over image (background)
				gd.setComposite(getAlphaComposite(0.35f));
				gd.setColor(Color.BLACK);
				gd.fillRect(0, 0, getWidth(), getHeight());
				gd.setComposite(getAlphaComposite(1.0f));
				
				// Draw Selection Component
				if (dragging && !clicked)
				{
					XRectangle rec = XRectangle.rectFromPoint(pointNew, pointBefore);
					
					if (rec.width > 0 && rec.height > 0)
					{
						componentSelection.paint(gd, panel, rec, image);
					}
				}
				
				repaint();
			}
		};
	}
	
	private AlphaComposite getAlphaComposite(float alpha)
	{
		return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	}
	
}