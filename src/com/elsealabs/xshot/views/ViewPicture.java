package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.XImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ViewPicture extends JFrame
{
	private XImage image;
	private JPanel panelBar;
	private JPanel panelImage;
	private ScrollPane scrollPane;

	public ViewPicture(XImage image)
	{
		this.image = image;
	}

	public void build()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setSize(image.getWidth() + 50, image.getHeight() + 100);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		panelBar = new JPanel();
		panelBar.setPreferredSize(new Dimension(panelBar.getWidth(), 80));
		panelBar.setBackground(Color.BLACK);
		add(panelBar, BorderLayout.NORTH);

		panelImage = new JPanel()
		{
			public void paint(Graphics gd)
			{
				Graphics2D g = (Graphics2D) gd;

				g.drawImage(
					image.getBufferedImage(),
					getWidth()  / 2 - (image.getWidth()  / 2),
					getHeight() / 2 - (image.getHeight() / 2),
					null
				);

				g.setColor(Color.BLACK);

				g.drawRect(
						getWidth()  / 2 - (image.getWidth()  / 2),
						getHeight() / 2 - (image.getHeight() / 2),
						image.getWidth(),
						image.getHeight()
				);

				repaint();
			}
		};
		panelImage.setSize(image.getWidth() + 100, image.getHeight() + 100);
		panelImage.setLayout(null);

		scrollPane = new ScrollPane();
		scrollPane.add(panelImage);
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

}