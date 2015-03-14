package com.elsealabs.xshot;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.elsealabs.xshot.graphics.Capturer;
import com.elsealabs.xshot.graphics.XImage;

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
			XImage image = capturer.capture(capturer.getAllMonitors());
			DisplayRecCaptureBeta disp = new DisplayRecCaptureBeta(image);
			disp.build();
		});
		
		bt_capture_full.addActionListener(a ->
		{
			XImage image = capturer.capture(capturer.getAllMonitors());
			image.writeImage(Paths.get("C:\\Users\\connorelsea\\Desktop\\image.png"), XImage.FORMAT.PNG);
		});
		
		setVisible(true);
	}
}
