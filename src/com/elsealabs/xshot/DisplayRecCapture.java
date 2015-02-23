package com.elsealabs.xshot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DisplayRecCapture extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel imagePane;
	
	private Capturer capturer = Capturer.getInstance();

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
				g2d.drawImage(image.getBufferedImage(), 0, 0, null);
				repaint();
			}
			
		};
		imagePane.setLayout(null);
		imagePane.setBorder(null);
		imagePane.setBounds(0, 0, this.getWidth(), this.getHeight());
		imagePane.repaint();
		
		add(imagePane);
		
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
