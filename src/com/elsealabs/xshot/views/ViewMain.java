package com.elsealabs.xshot.views;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

import com.elsealabs.xshot.graphics.Capturer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewMain extends JFrame
{
	private Capturer capturer;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ViewMain frame = new ViewMain();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewMain()
	{
		capturer = Capturer.getInstance(this);
		
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception ex) { ex.printStackTrace(); }
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnElseaLabs = new JMenu("Elsea Labs");
		menuBar.add(mnElseaLabs);
		
		JMenuItem mntmCompanyWebsite = new JMenuItem("Company Website");
		mnElseaLabs.add(mntmCompanyWebsite);
		
		JMenuItem mntmProgramCredits = new JMenuItem("Program Credits");
		mnElseaLabs.add(mntmProgramCredits);
		
		JMenuItem mntmOpenSource = new JMenuItem("Open Source");
		mnElseaLabs.add(mntmOpenSource);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenu mnThemes = new JMenu("Themes");
		mnSettings.add(mnThemes);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Capture Types", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 3, 10, 4));
		
		JButton btnNewButton = new JButton("Free-Form");
		btnNewButton.addActionListener(a -> {
			ViewCaptureFreeform vcf = new ViewCaptureFreeform(Capturer.getInstance().getAllMonitors());
		});
		btnNewButton.setBorder(null);
		btnNewButton.setMnemonic('f');
		btnNewButton.setIcon(null);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnNewButton);
		
		JButton btnFullscreen = new JButton("Fullscreen");
		btnFullscreen.setMnemonic('u');
		btnFullscreen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnFullscreen);
		
		JButton btnRapidCapture = new JButton("Rapid");
		btnRapidCapture.setMnemonic('r');
		btnRapidCapture.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnRapidCapture);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 1, 4, 6));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JCheckBox chckbxTi = new JCheckBox("Wait before taking capture");
		chckbxTi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_2.add(chckbxTi);
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JComponent field = ((JSpinner.DefaultEditor) spinner.getEditor());
	    field.setPreferredSize(new Dimension(30, field.getPreferredSize().height));
		spinner.setValue(10);
		panel_2.add(spinner);
		
		JLabel lblSeconds = new JLabel("seconds");
		lblSeconds.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_2.add(lblSeconds);
		
		JLabel lblThisUiIs = new JLabel("This UI is temporary while functionality is being implemented");
		lblThisUiIs.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblThisUiIs);
		
		setVisible(true);
	}

}
