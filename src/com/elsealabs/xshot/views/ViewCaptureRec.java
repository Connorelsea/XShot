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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.elsealabs.xshot.component.ComponenetSelection;
import com.elsealabs.xshot.component.ComponentZoom;
import com.elsealabs.xshot.graphics.Capture;
import com.elsealabs.xshot.graphics.Capturer;
import com.elsealabs.xshot.graphics.PointRectangle;
import com.elsealabs.xshot.graphics.XImage;

public class ViewCaptureRec extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Rectangle bounds;
	
	private Capturer capturer;
	private XImage   image;
	
	private ComponenetSelection componentSelection;
	private ComponentZoom componentZoom;
	private JPanel panel;
	
	private Point pointBefore;
	private Point pointNew;
	
	private boolean clicked;
	private boolean dragging;
	private boolean released;
	private boolean keep = true;

	private boolean zooming;

	private PointRectangle prevRec;
	
	public ViewCaptureRec(XImage image)
	{
		this.image = image;
		
		this.componentSelection = ComponenetSelection.DEFAULT_MODERN;
		this.componentZoom =      ComponentZoom.DEFAULT_MODERN;
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
				dragging = false;
				released = true;
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				clicked = false;
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
			public void mouseDragged(MouseEvent e)
			{
				dragging = true;
				pointNew = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(pointNew, panel);
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

				if (e.getKeyCode() == KeyEvent.VK_K)
				{
					keep = false;
				}
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

				// If they have not released their selection
				if (released == false && keep == true)
				{
					// Draw Selection Component
					if (dragging && !clicked)
					{
						prevRec = PointRectangle.rectFromPoint(pointNew, pointBefore);

						if (prevRec.width > 0 && prevRec.height > 0)
						{
							componentSelection.paint(gd, prevRec, image);
						}
					}

					// Draw zooming component
					if (zooming == true)
					{

					}
				}

				// If they have released their selection
				else if (released == true)
				{
					if (keep == true)
					{
						componentSelection.paint(gd, prevRec, image);
						//new ViewPicture(image.getSubImage(prevRec)).build();
						new ViewPictureRefined(new Capture(image, prevRec)).build();
						dispose();
					}
					else if (keep == false)
					{
						keep = true;
						released = false;
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