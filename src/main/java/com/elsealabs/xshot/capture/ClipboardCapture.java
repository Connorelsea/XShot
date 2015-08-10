package com.elsealabs.xshot.capture;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

public class ClipboardCapture implements ClipboardOwner {
	
	private Capture capture;
	
	public ClipboardCapture(Capture capture) {
		this.capture = capture;
	}
	
	public void moveToClipboard() {
		TransferableCapture ti = new TransferableCapture(capture.getUpdatedImage());
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(ti, this);
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// TODO lostOwnership ClipboardCapture
	}

}