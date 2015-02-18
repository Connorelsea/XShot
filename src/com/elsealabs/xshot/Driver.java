package com.elsealabs.xshot;

import java.awt.image.BufferedImage;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Driver {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setSize(100, 100);
		frame.setVisible(true);
		
		Capturer cap = new Capturer(frame);
		Image image = cap.capture(cap.getCurrentMonitor());
		
		image.writeImage(Paths.get("C:\\Users\\test\\Desktop", "image.png"), Image.FORMAT.PNG);
		
		//EventQueue.invokeLater(() -> new DisplayCapture());
		
	}

}