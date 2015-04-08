package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.XImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class ViewPicture extends JFrame
{
	private XImage image;
	private JPanel panelBar;
	private JPanel panelImage;
	private JScrollPane scrollPane;

	private int imageX;
	private int imageY;
	private Rectangle imageSize;
	private Rectangle imageNorth;
	private Rectangle imageEast;
	private Rectangle imageSouth;
	private Rectangle imageWest;
	private boolean hovering = false;
	private boolean resizing = false;

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

		imageNorth = new Rectangle();
		imageEast  = new Rectangle();
		imageSouth = new Rectangle();
		imageWest  = new Rectangle();
	}

	private void _initListeners()
	{
		panelImage.addMouseListener(new MouseListener()
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

		panelImage.addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{

			}

			@Override
			public void mouseMoved(MouseEvent e)
			{


				panelImage.repaint();
			}
		});

		// Resizing Listeners:
		// They detect when the window begins resizing and stops
		// resizing in order to allow  for  the  optimization of
		// collision detection

		getRootPane().addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				super.componentResized(e);
				System.out.print("resize\n");
				resizing = true;
			}
		});

		getRootPane().addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				System.out.print("false");
				resizing = false;
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

				// TODO: Do not do calculations while resizing
				// TODO: Move boxes so they completely overlap at corners

				int collisionHeight = 10;
				int collisionWidth  = 10;

				if (!resizing)
				{
					System.out.println("drawing");
					// Image Size
					imageSize.setBounds(
							imageX,
							imageY,
							image.getWidth(),
							image.getHeight()
					);

					// Image's Northern Collision Bounds
					imageNorth.setBounds(
							imageX,
							imageY - (collisionHeight / 2),
							image.getWidth(),
							collisionHeight
					);

					// Image's Southern Collision Bounds
					imageSouth.setBounds(
							imageX,
							imageY + image.getHeight() - (collisionHeight / 2),
							image.getWidth(),
							collisionHeight
					);

					// Image's Eastern Collision Bounds
					imageEast.setBounds(
							imageX + image.getWidth() - (collisionWidth / 2),
							imageY,
							collisionWidth,
							image.getHeight()
					);

					// Image's Western Collision Bounds

					imageWest.setBounds(
							imageX - (collisionWidth / 2),
							imageY,
							collisionWidth,
							image.getHeight()
					);
				}

				g.drawImage(
						image.getBufferedImage(),
						(int) imageSize.getX(),
						(int) imageSize.getY(),
						null
				);

				g.setColor(Color.BLACK);
				g.drawRect((int) imageSize.getX(), (int) imageSize.getY(), (int) imageSize.getWidth(), (int) imageSize.getHeight());

				g.setColor(Color.GREEN);
				g.drawRect((int) imageNorth.getX(), (int) imageNorth.getY(), (int) imageNorth.getWidth(), (int)
						imageNorth.getHeight());

				g.setColor(Color.RED);
				g.drawRect((int) imageWest.getX(), (int) imageWest.getY(), (int) imageWest.getWidth(), (int)
						imageWest.getHeight());

				if (hovering)
				{
					g.setColor(Color.BLACK);
					g.drawRect((int) imageSize.getX(), (int) imageSize.getY(), (int) imageSize.getWidth(), (int) imageSize.getHeight());
				}
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
		} else
		{
			setSize((int) calcWidth, (int) calcHeight);
			setLocationRelativeTo(null);
		}

		_initListeners();
		setVisible(true);

		resizing = false;
	}

}