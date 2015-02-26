package com.elsealabs.xshot;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class DisplayRecCapture2 extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayRecCapture2 frame = new DisplayRecCapture2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DisplayRecCapture2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 290);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWait = new JLabel("Wait ");
		lblWait.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblWait.setBounds(10, 11, 56, 37);
		contentPane.add(lblWait);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.TRAILING);
		textField.setText("0");
		textField.setBorder(null);
		textField.setBackground(SystemColor.menu);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField.setBounds(67, 11, 28, 37);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblSecondsBefore = new JLabel("Seconds Before...");
		lblSecondsBefore.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblSecondsBefore.setBounds(101, 11, 209, 37);
		contentPane.add(lblSecondsBefore);
		
		JButton btnNewButton = new JButton("Rectangular Capture");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(10, 99, 141, 120);
		contentPane.add(btnNewButton);
		
		JButton btnScreenCapture = new JButton("Screen Capture");
		btnScreenCapture.setBounds(169, 99, 141, 120);
		contentPane.add(btnScreenCapture);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 51, 300, 37);
		contentPane.add(panel);
	}
}
