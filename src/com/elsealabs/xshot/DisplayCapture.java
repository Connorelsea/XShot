package com.elsealabs.xshot;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayCapture extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private Capturer capturer;

	public DisplayCapture() {
		
		// TODO: Entirely re-do capture UI, needs planning as well
		
		capturer = Capturer.getInstance(this);
		
		setTitle("XShot");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 300);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton bt_capture_rec = new JButton("Rectangular Capture");
		bt_capture_rec.setBounds(15, 16, 276, 212);
		contentPane.add(bt_capture_rec);
		
		JButton bt_capture_full = new JButton("Full-Screen Capture");
		bt_capture_full.setBounds(306, 16, 276, 212);
		contentPane.add(bt_capture_full);
		
		bt_capture_rec.addActionListener(a -> {
			
			XImage image = capturer.capture();
			DisplayRecCapture disp = new DisplayRecCapture(image);
		});
		
		bt_capture_full.addActionListener(a -> {
			
		});
		
		setVisible(true);
	}
}
