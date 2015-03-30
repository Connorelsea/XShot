package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.XImage;

import javax.swing.*;
import java.awt.*;

public class ViewPicture extends JFrame
{
	private XImage image;
	private JPanel panelImage;

	public ViewPicture(XImage image)
	{
		this.image = image;
	}

	public void build()
	{
		setSize(image.getWidth(),image.getHeight());

		panelImage = new JPanel()
		{
			public void paint(Graphics gd)
			{
				Graphics2D g = (Graphics2D) gd;
				g.drawImage(image.getBufferedImage(), 0, 0, null);
			}
		};
		panelImage.setLayout(null);
	}

}