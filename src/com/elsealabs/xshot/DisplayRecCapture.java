package com.elsealabs.xshot;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DisplayRecCapture extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel imagePane;
	
	private AlphaComposite overlayComposite;
	
	private Capturer capturer = Capturer.getInstance();
	
	private boolean dragging;
	private Point point1;
	private Point point2;

	public DisplayRecCapture(Image image) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 600);
		setUndecorated(true);
		capturer.getCurrentMonitor().getDevice().setFullScreenWindow(this);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		
		overlayComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f);
		
		imagePane = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paint(Graphics g)
			{
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				
				// Draw initial image
				g2d.drawImage(image.getBufferedImage(), 0, 0, null);
				
				// Draw rectangle over image
				g2d.setComposite(overlayComposite);
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				
				g2d.setColor(Color.RED);
				
				if (dragging) {
					System.out.println("yo");
					System.out.println(point1);
					g2d.fillRect(point1.x, point1.y, point1.x + 20, point1.y + 20);
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
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dragging = true;
				point1 = e.getLocationOnScreen();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				point1 = e.getLocationOnScreen();
				dragging = true;
				point2 = e.getLocationOnScreen();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
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

}
