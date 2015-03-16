package com.elsealabs.xshot.views;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
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
import com.elsealabs.xshot.graphics.XRectangle;
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
				super.paint(g);
				Graphics2D gd = (Graphics2D) g;
				
				cmpImage.paint(gd);
				
				// Draw Selection Component
				if (dragging && !clicked)
				{
					System.out.println("dragging");
					cmpSelection.paint(gd);
				}
				
				repaint();
			}
		};
		
		mainPanel.setLayout(null);
		mainPanel.setBorder(null);
		add(mainPanel);
	}
	
	/**
	 *  Defines the components, how they are drawn, and their properties
	 */
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
				
				// Draw color overlay if visible
				if (visible == true)
				{
					gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
					gd.setColor(color);
					gd.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		
		cmpImage.addProperty(new XProperty(".5",     "Opacity",    "The transparency of the image overlay"));
		cmpImage.addProperty(new XProperty("000000", "Color",      "The color of the image overlay"));
		cmpImage.addProperty(new XProperty("true",   "Visibility", "Whether or not the colored overlay is visible"));
		
		// Component: Selection
		// The screenshot selection
		
		cmpSelection = new XComponent()
		{
			@Override
			public void paint(Graphics2D g)
			{
				XRectangle rec = XRectangle.rectFromPoint(pointNew, pointBefore);
				
				if (rec.width > 0 && rec.height > 0)
				{
					// Get property values
					float   foreBorderThickness = Float.parseFloat(cmpSelection.getValueOf("ForeBorderThickness"));
					boolean foreBorderDashed    = cmpSelection.getValueOf("ForeBorderThickness").equals("true") ? true : false;
					Color   foreBorderColor     = Color.decode(cmpSelection.getValueOf("ForeBorderColor"));
					
					Color   axisLockColor       = Color.decode(cmpSelection.getValueOf("AxisLockColor"));
					float   axisLockThickness   = Float.parseFloat(cmpSelection.getValueOf("AxisLockThickness"));
					
					float   backBorderThickness = Float.parseFloat(cmpSelection.getValueOf("BackBorderThickness"));
					Color   backBorderColor     = Color.decode(cmpSelection.getValueOf("BackBorderColor"));
					
					boolean decorated           = cmpSelection.getValueOf("Decorated").equals("true") ? true : false;
					float   decorSize           = Float.parseFloat(cmpSelection.getValueOf("DecorSize"));
					Color   decorBorderColor    = Color.decode(cmpSelection.getValueOf("DecorBorderColor"));
					Color   decorBackColor      = Color.decode(cmpSelection.getValueOf("DecorBackColor"));
					
					// Draw background stroke
					g.setStroke(generateStroke(backBorderThickness));
					g.setColor(backBorderColor);
					g.draw(rec);
					
					// Draw foreground stroke
					if (foreBorderDashed) g.setStroke(generateStroke(foreBorderThickness, 4));
					else g.setStroke(generateStroke(foreBorderThickness));
			
					g.setColor(foreBorderColor);
					g.draw(rec);
					
					// Draw decorations
					if (decorated)
					{
						g.setStroke(generateStroke(1));
						
						rec.getPointsAsArray().stream()
						.forEach(a ->
						{
							g.setColor(decorBackColor);
							g.fillRect(a.x - ((int) decorSize / 2), a.y - ((int) decorSize / 2), (int) decorSize, (int) decorSize);
							
							g.setColor(decorBorderColor);
							g.drawRect(a.x - ((int) decorSize / 2), a.y - ((int) decorSize / 2), (int) decorSize, (int) decorSize);
						});
					}
				}
			}
		};
		
		cmpSelection.addProperty(new XProperty("1",      "ForeBorderThickness", "The thickness of the foreground border"));
		cmpSelection.addProperty(new XProperty("true",   "ForeBorderDashed",    "Whether or not the foreground border is dashed"));
		cmpSelection.addProperty(new XProperty("000000", "ForeBorderColor",     "The color of the foreground border"));
		
		cmpSelection.addProperty(new XProperty("981d1d", "AxisLockColor",       "The color of the axis-lock border"));
		cmpSelection.addProperty(new XProperty("1",      "AxisLockThickness",   "The thickness of the axis-lock border"));
		
		cmpSelection.addProperty(new XProperty("1",      "BackBorderThickness", "The thickness of the background border"));
		cmpSelection.addProperty(new XProperty("ffffff", "BackBorderColor",     "The color of the background border"));
		
		cmpSelection.addProperty(new XProperty("true",   "Decorated",           "Whether or not decorations are visible"));
		cmpSelection.addProperty(new XProperty("7",      "DecorSize",           "The size decoration box"));
		cmpSelection.addProperty(new XProperty("000000", "DecorBorderColor",    "The color of the decoration border"));
		cmpSelection.addProperty(new XProperty("ffffff", "DecorBackColor",      "The color of the decoration background"));
		
	}
	
	public static Stroke generateStroke(float width, float dashWidth)
	{
		return new BasicStroke(
			width,
			BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER,
			10.0f,
			new float[] { dashWidth },
			0.0f
		);
	}
	
	public static Stroke generateStroke(float width)
	{
		return new BasicStroke(width);
	}
	
	/**
	 * Define and start all mouse, key, or drag listeners
	 */
	private void initListeners()
	{
		/**
		 * Mouse Listeners to handle the state of the mouse, and
		 * gathering points.
		 */
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
		
		/**
		 * Mouse motion listener to update the new point  whenever
		 * the user drags the mouse. This will size the selection.
		 */
		addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
				dragging = true;
				clicked = false;
				pointNew = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(pointNew, mainPanel);
			}
			
		});
		
		/**
		 * Key Listener for various key shortcuts such as zooming
		 * or axis-locking.
		 */
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