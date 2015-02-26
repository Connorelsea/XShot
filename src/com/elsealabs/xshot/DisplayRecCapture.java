package com.elsealabs.xshot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicArrowButton;

public class DisplayRecCapture extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel imagePane;
	
	private Capturer capturer = Capturer.getInstance();
	
	private boolean dragging;
	private Point pointBefore;
	private Point pointNew;
	
	private Image currentImage;
	
	private BasicStroke dashed;
	private BasicStroke normal;
	private BasicStroke normal_thick;

	public DisplayRecCapture(Image image)
	{
		dashed = new BasicStroke(
				1.0f, BasicStroke.CAP_BUTT,
		        BasicStroke.JOIN_MITER, 10.0f,
		        new float[] { 5.0f } , 0.0f
		);
		normal = new BasicStroke(1.0f);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 600);
		setUndecorated(true);
		capturer.getCurrentMonitor().getDevice().setFullScreenWindow(this);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		
		imagePane = new JPanel()
		{
			private static final long serialVersionUID = 1L;
			
			public void paint(Graphics g)
			{
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				
				// Draw initial image
				g2d.drawImage(image.getBufferedImage(), 0, 0, null);
				
				// Draw rectangle over image
				g2d.setComposite(getAlphaComposite(0.35f));
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				g2d.setComposite(getAlphaComposite(1.0f));
				
				if (dragging && pointNew != null)
				{
					//System.out.printf("Point 1: (%d, %d) Point 2: (%d, %d)\n", pointBefore.x, pointBefore.y, pointNew.x, pointNew.y);
					
					// Create rectangle, draw cropped image
					XRectangle rec = XRectangle.rectFromPoint(pointNew, pointBefore);
					currentImage = image.getSubImage(rec);
					g2d.drawImage(currentImage.getBufferedImage(), rec.x, rec.y, null);
					
					// Draw border
					g2d.setStroke(normal);
					g2d.setColor(Color.BLACK);
					
						g2d.draw(rec);
					
					g2d.setStroke(dashed);
					g2d.setColor(Color.WHITE);
					
						g2d.draw(rec);
					
					// Draw point indicators	
					g2d.setStroke(normal);
					g2d.setColor(Color.BLACK);
					
						// Before/After Points
						g2d.fillRect(pointBefore.x - 3, pointBefore.y - 3, 6, 6);
						g2d.fillRect(pointNew.x - 3, pointNew.y - 3, 6, 6);
						
						// TESTING
						g2d.fillOval(rec.getCornerUpperLeft().x, rec.getCornerUpperLeft().y, 100, 100);
						
						// Other Corners
						g2d.fillRect(rec.x - 3, rec.y - 3, 6, 6);
						//g2d.fillRect(rec.width - 3, rec.height - 3, 6, 6);
					
					g2d.setColor(Color.WHITE);
					
						// Before/After Points
						g2d.drawRect(pointBefore.x - 3, pointBefore.y - 3, 6, 6);
						g2d.drawRect(pointNew.x - 3, pointNew.y - 3, 6, 6);
						
						// Other Corners
						g2d.drawRect(rec.x - 3, rec.y - 3, 6, 6);
						//g2d.drawRect(rec.width - 3, rec.height - 3, 6, 6);
				}
				
				repaint();
			}
			
		};
		imagePane.setLayout(null);
		imagePane.setBorder(null);
		imagePane.setBounds(0, 0, this.getWidth(), this.getHeight());
		imagePane.repaint();
		
		add(imagePane);
		
		// add listener(s)
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				dragging = true;
				pointBefore = e.getPoint();
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				dragging = false;
			}
			
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (dragging == true)
				{
					pointNew = e.getLocationOnScreen();
				}
			}
			
		});
		
		// force correct positioning
        Point p = new Point(0, 0);
        SwingUtilities.convertPointToScreen(p, getContentPane());
        Point l = getLocation();
        l.x -= p.x;
        l.y -= p.y;
        setLocation(l);
		
		setVisible(true);
	}
	
	private AlphaComposite getAlphaComposite(float alpha)
	{
		return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	}

}







