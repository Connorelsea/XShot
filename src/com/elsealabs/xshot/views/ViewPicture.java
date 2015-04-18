package com.elsealabs.xshot.views;

import com.elsealabs.xshot.file.FileUtil;
import com.elsealabs.xshot.file.ImageSaver;
import com.elsealabs.xshot.graphics.XImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;

/**
 * ViewPicture.java
 *
 * A user interface providing basic editing functionality, basic
 * annotation functionality, and the ability to save images.
 */
public class ViewPicture extends JFrame
{
	// Components
	private XImage image;
	private JPanel panelBar;
	private JPanel panelImage;
	private JScrollPane scrollPane;

	private ModernButton buttonSave;
	private ModernButton buttonNew;

	// Image rendering and collision
	private int imageX;
	private int imageY;
	private Rectangle imageSize;
	private Rectangle imageNorth;
	private Rectangle imageEast;
	private Rectangle imageSouth;
	private Rectangle imageWest;
	private boolean hovering = false;
	private boolean resizing = false;

	// Image zooming
	private double scaleTarget = 2;
	private double scaleCurrent = 1;

	// Screen sizes and width
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
		panelBar.setLayout(new GridBagLayout());
		panelBar.setPreferredSize(new Dimension(90, 90));
		panelBar.setBackground(Color.LIGHT_GRAY);
		add(panelBar, BorderLayout.NORTH);

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;

		buttonSave = new ModernButton(
			new ModernLabel("save", Color.WHITE, null),
			new Color(62, 62, 62),
			Color.GRAY,
			a -> {

				File defaultFile = new File("C:\\Capture.PNG");
				File dest = new FileUtil().getUserSaveLocation(defaultFile, "Save Image");
				image.writeImage(dest, XImage.FORMAT.PNG);

			}
		);
		panelBar.add(buttonSave, c);

		c.gridx = 1;
		c.gridy = 0;

		buttonNew = new ModernButton(
				new ModernLabel("new", Color.WHITE, null),
				new Color(104, 104, 104),
				Color.GRAY,
				a -> {

				}
		);
		panelBar.add(buttonNew, c);

		c.gridx = 2;
		c.gridy = 0;

		buttonNew = new ModernButton(
				new ModernLabel("copy", Color.WHITE, null),
				new Color(143, 143, 143),
				Color.GRAY,
				a -> {

				}
		);
		panelBar.add(buttonNew, c);

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

				imageX = (getWidth() / 2)  - (image.getWidth() / 2);
				imageY = (getHeight() / 2) - (image.getHeight() / 2);

				// TODO: Do not do calculations while resizing

				int collisionHeight = 10;
				int collisionWidth  = 10;

				// Image Size
				imageSize.setBounds(
						imageX,
						imageY,
						image.getWidth(),
						image.getHeight()
				);

				// Bounds setup of a rectangle
				imageNorth.setBounds(
						imageX - (collisionWidth / 2),
						imageY - (collisionHeight / 2),
						image.getWidth() + collisionWidth,
						collisionHeight
				);

				// Image's Southern Collision Bounds
				imageSouth.setBounds(
						imageX - (collisionWidth / 2),
						imageY + image.getHeight() - (collisionHeight / 2),
						image.getWidth() + collisionWidth,
						collisionHeight
				);

				// Image's Eastern Collision Bounds
				imageEast.setBounds(
						imageX + image.getWidth() - (collisionWidth / 2),
						imageY - (collisionHeight / 2),
						collisionWidth,
						image.getHeight() + collisionHeight
				);

				// Image's Western Collision Bounds

				imageWest.setBounds(
						imageX - (collisionWidth / 2),
						imageY - (collisionHeight / 2),
						collisionWidth,
						image.getHeight() + collisionHeight
				);

				g.drawImage(
						image.getBufferedImage(),
						(int) imageSize.getX(),
						(int) imageSize.getY(),
						null
				);

				// animate scaling

				if (scaleCurrent != scaleTarget)
				{
					scaleCurrent += (scaleTarget - scaleCurrent) / 10;
					AffineTransform transform = new AffineTransform();
					transform.scale(2, 2);
					g.setTransform(transform);

					// TODO: Reset rectangle and panel, etc after changing size of image with scale.
				}

//				g.drawImage(
//						image.getBufferedImage(),
//						(int) imageSize.getX(),
//						(int) imageSize.getY(),
//						null
//				);
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