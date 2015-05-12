package com.elsealabs.xshot.views;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.component.ComponentSelection;
import com.elsealabs.xshot.math.PointRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class ViewCaptureRec extends JFrame {
	private static final long serialVersionUID = 1L;

	private Rectangle     bounds;
	private BufferedImage image;

	private ComponentSelection componentSelection;
	private JPanel             panel;

	private Point pointBefore;
	private Point pointNew;

	private boolean clicked;
	private boolean dragging;
	private boolean released;
	private boolean keep = true;

	private boolean zooming;

	private PointRectangle prevRec;

	public ViewCaptureRec(BufferedImage image)
	{
		this.image = image;

		this.componentSelection = ComponentSelection.DEFAULT_MODERN;
	}

	public void build(Rectangle bounds)
	{
		this.bounds = bounds;

		setUndecorated(true);
		setBounds(bounds.x, bounds.y, bounds.width, bounds.height);

		_addListeners();
		_createPanel();

		add(panel);

		setVisible(true);
	}

	private void _addListeners()
	{
		addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{
				dragging = false;
				released = true;
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				clicked = false;
				pointBefore = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(pointBefore, panel);
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				System.out.println(e.getPoint());
				clicked = true;
			}

		});

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e)
			{
				dragging = true;
				pointNew = e.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(pointNew, panel);
			}

		});

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e)
			{

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				zooming = false;
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_Z)
				{
					zooming = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_K)
				{
					keep = false;
				}
			}
		});

	}

	private void _createPanel()
	{
		panel = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g)
			{
				super.paint(g);
				Graphics2D gd = (Graphics2D) g;

				// Draw image
				g.drawImage(image, 0, 0, null);

				// Draw rectangle over image (background)
				gd.setComposite(getAlphaComposite(0.35f));
				gd.setColor(Color.BLACK);
				gd.fillRect(0, 0, getWidth(), getHeight());
				gd.setComposite(getAlphaComposite(1.0f));

				// If they have not released their selection
				if (!released && keep)
				{
					// Draw Selection Component
					if (dragging && !clicked)
					{
						prevRec = PointRectangle.rectFromPoint(pointNew, pointBefore);

						if (prevRec != null)
						{
							if (prevRec.width > 0 && prevRec.height > 0)
                            {
                                componentSelection.paint(gd, prevRec, image);
                            }
						}
					}

					// Draw zooming component
					if (zooming)
					{

					}
				}

				// If they have released their selection
				else if (released)
				{
					if (keep)
					{
						componentSelection.paint(gd, prevRec, image);

						Capture  capture = new Capture(image, bounds, prevRec);
						ViewPicture view = new ViewPicture(capture);

						view.build();
						dispose();
					}
					else
					{
						keep = true;
						released = false;
					}
				}

				repaint();
			}
		};
	}

	private AlphaComposite getAlphaComposite(float alpha)
	{
		return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
	}

}