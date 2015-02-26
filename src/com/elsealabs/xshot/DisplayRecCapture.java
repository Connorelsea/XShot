package com.elsealabs.xshot;

import java.awt.AlphaComposite;
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

public class DisplayRecCapture extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel imagePane;
	
	private Capturer capturer = Capturer.getInstance();
	
	private boolean dragging;
	private Point pointBefore;
	private Point pointNew;

	public DisplayRecCapture(Image image) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 600);
		setUndecorated(true);
		capturer.getCurrentMonitor().getDevice().setFullScreenWindow(this);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		
		imagePane = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paint(Graphics g)
			{
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				
				// Draw initial image
				g2d.drawImage(image.getBufferedImage(), 0, 0, null);
				
				// Draw rectangle over image
				g2d.setComposite(getAlphaComposite(0.5f));
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				
				g2d.setColor(Color.RED);
				
				if (dragging && pointNew != null) {
					//System.out.printf("Point 1: (%d, %d) Point 2: (%d, %d)\n", pointBefore.x, pointBefore.y, pointNew.x, pointNew.y);
					g2d.fill(rectFromPoint(pointNew, pointBefore));
					g2d.setColor(Color.BLACK);
					g2d.setComposite(getAlphaComposite(1.0f));
					g2d.fillOval(pointBefore.x - 5, pointBefore.y - 5, 10, 10);
					g2d.setColor(Color.blue);
					g2d.fillOval(pointNew.x - 5, pointNew.y - 5, 10, 10);
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
	
	private Rectangle rectFromPoint(Point pnew, Point pbef)
	{
		if (pnew.x > pbef.x && pnew.y > pbef.y) {
			return new Rectangle(pbef.x, pbef.y, pnew.x - pbef.x, pnew.y - pbef.y);
		}
		else if (pnew.x > pbef.x && pnew.y < pbef.y) {
			return new Rectangle(pbef.x, pnew.y, pnew.x - pbef.x, pbef.y - pnew.y);
		}
		else if (pnew.x < pbef.x && pnew.y < pbef.y) {
			return new Rectangle(pnew.x, pnew.y, pbef.x - pnew.x, pbef.y - pnew.y);
		}
		else if (pnew.x < pbef.x && pnew.y > pbef.y) {
			return new Rectangle(pnew.x, pbef.y, pbef.x - pnew.x, pnew.y - pbef.y);
		}
		return new Rectangle(1, 1, 1, 1);
	}
	
	private AlphaComposite getAlphaComposite(float alpha)
	{
		return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	}

}







