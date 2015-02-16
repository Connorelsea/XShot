package com.elsealabs.xshot;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayRecCapture extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	public DisplayRecCapture() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
	}

}
