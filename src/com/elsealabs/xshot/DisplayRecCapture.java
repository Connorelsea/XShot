package com.elsealabs.xshot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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

	public DisplayRecCapture(Image image)
	{
		dashed = new BasicStroke(
				1.0f, BasicStroke.CAP_BUTT,
		        BasicStroke.JOIN_MITER, 10.0f,
		        new float[] { 4.0f } , 0.0f
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
					// Create rectangle, draw cropped image
					XRectangle rec = XRectangle.rectFromPoint(pointNew, pointBefore);
					currentImage = image.getSubImage(rec);
					g2d.drawImage(currentImage.getBufferedImage(), rec.x, rec.y, null);
					
					// Draw border
					g2d.setStroke(normal);
					g2d.setColor(Color.WHITE);
					
						g2d.draw(rec);
					
					g2d.setStroke(dashed);
					g2d.setColor(Color.BLACK);
					
						g2d.draw(rec);
						
					// Dynamically draw point indicators
						
					g2d.setStroke(normal);
					
					rec.getPointsAsArray().stream().forEach(x ->
					{
						g2d.setColor(Color.BLACK);
						g2d.fillRect(x.x - 3, x.y - 3, 7, 7);
						g2d.setColor(Color.WHITE);
						g2d.drawRect(x.x - 3, x.y - 3, 7, 7);
					});
					
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







