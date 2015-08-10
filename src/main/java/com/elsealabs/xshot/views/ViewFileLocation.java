package com.elsealabs.xshot.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ViewFileLocation extends JFrame {

	private JPanel contentPane;
	private JTextField field_name;
	private JTextField field_path;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewFileLocation frame = new ViewFileLocation();
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
	public ViewFileLocation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_name = new JLabel("Location Nick Name");
		label_name.setBounds(10, 11, 112, 14);
		contentPane.add(label_name);
		
		JLabel label_path = new JLabel("Location Path");
		label_path.setBounds(10, 38, 112, 14);
		contentPane.add(label_path);
		
		field_name = new JTextField();
		field_name.setBounds(132, 8, 322, 20);
		contentPane.add(field_name);
		field_name.setColumns(10);
		
		field_path = new JTextField();
		field_path.setColumns(10);
		field_path.setBounds(132, 35, 322, 20);
		contentPane.add(field_path);
		
		JButton button_path = new JButton("Choose Path");
		button_path.setBounds(10, 73, 112, 23);
		contentPane.add(button_path);
		
		JButton button_save = new JButton("Save");
		button_save.setBounds(342, 73, 112, 23);
		contentPane.add(button_save);
		
		JButton button_delete = new JButton("Delete");
		button_delete.setBounds(220, 73, 112, 23);
		contentPane.add(button_delete);
	}
}
