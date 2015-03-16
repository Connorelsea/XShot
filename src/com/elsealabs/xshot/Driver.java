package com.elsealabs.xshot;

import java.awt.EventQueue;

import com.elsealabs.xshot.views.ViewMain;
import com.elsealabs.xshot.views.ViewMainModern;

public class Driver {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> new ViewMain());
		//EventQueue.invokeLater(() -> new ViewMainModern());
		
	}

}