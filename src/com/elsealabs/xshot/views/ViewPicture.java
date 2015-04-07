package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.XImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ViewPicture extends JFrame
{
	private XImage image;
	private JPanel panelBar;
	private JPanel panelImage;
	private JScrollPane scrollPane;

	private int imageX;
	private int imageY;
	private Rectangle imageSize;

	private Dimension screenSize;
	private double calcWidth;
	private double calcHeight;

	public ViewPicture(XImage image)
	{
		this.image = image;
		_init();
	}

	private void _init()
	{
		imageSize = new Rectangle();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}

	private void _initListeners()
	{
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{

			}

			@Override
			public void mousePressed(MouseEvent e)
			{

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseExited(MouseEvent e)
			{

			}
		});
	}

	public void build()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		panelBar = new JPanel();
		panelBar.setPreferredSize(new Dimension(90, 90));
		panelBar.setBackground(Color.LIGHT_GRAY);
		add(panelBar, BorderLayout.NORTH);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);

		panelImage = new JPanel()
		{
			public void paint(Graphics gd)
			{
				Graphics2D g = (Graphics2D) gd;

				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());

				// DRAW IMAGE

				imageX = (getWidth() / 2) - (image.getWidth() / 2);
				imageY = (getHeight() / 2) - (image.getHeight() / 2);

				imageSize.setBounds(imageX, imageY, image.getHeight(), image.getWidth());

				g.drawImage(
					image.getBufferedImage(),
					(int) imageSize.getX(),
					(int) imageSize.getY(),
					null
				);

			}
		};
		panelImage.setBorder(null);
		panelImage.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		scrollPane.getViewport().add(panelImage);

		// TODO: Factor in width of items on tool bar

		calcWidth  = image.getWidth()  + 100;
		calcHeight = image.getHeight() + 100 + 100;

		// TODO: Fix odd window sizing if they take a really big freeform

		if (calcWidth > screenSize.getWidth())
		{
			calcWidth  = screenSize.getWidth();
			setSize((int) calcWidth, (int) calcHeight);
			setLocation(0, 0);
		}
		else
		{
			setSize((int) calcWidth, (int) calcHeight);
			setLocationRelativeTo(null);
		}

		setVisible(true);
	}

}