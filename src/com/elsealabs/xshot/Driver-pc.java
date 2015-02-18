package com.elsealabs.xshot;

import java.awt.image.BufferedImage;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Driver {
	
	public static void main(String[] args) {
		
		String text = "UWU/CST/13/0032 F";
		
		String[] array = text.split("[/ ]");
		String[] other = array[0].split("/");
		
		for (String e : array) System.out.println(e);
		
//		JFrame frame = new JFrame();
//		frame.setSize(100, 100);
//		frame.setVisible(true);
//		
//		Capturer cap = new Capturer(frame);
//		BufferedImage image = cap.capture(cap.getCurrentMonitor());
//		
//		ImageIO.write(image, "PNG", Files.createFile(FileSystems.getDefault().getPath("", "image.png")) );
		
		//EventQueue.invokeLater(() -> new DisplayCapture());
		
	}

}