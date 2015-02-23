package com.elsealabs.xshot;

import java.awt.EventQueue;

public class Driver {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> new DisplayCapture());
		
	}

}