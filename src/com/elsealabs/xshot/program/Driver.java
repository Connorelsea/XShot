package com.elsealabs.xshot.program;

import java.awt.EventQueue;

import com.elsealabs.xshot.views.ViewMainModern;

public class Driver {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new ViewMainModern());
	}

}