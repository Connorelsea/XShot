package com.elsealabs.xshot;

import java.awt.EventQueue;
import java.util.stream.IntStream;

import com.elsealabs.xshot.views.ViewMain;
import com.elsealabs.xshot.views.ViewMainModern;

public class Driver {
	
	public static void main(String[] args) {
		
		//EventQueue.invokeLater(() -> new ViewMain());
		EventQueue.invokeLater(() -> new ViewMainModern());

		//System.out.print(IntStream.range(1, 6).reduce(0, (a, b) -> (2 * a) - 1 + b));
		
	}

}