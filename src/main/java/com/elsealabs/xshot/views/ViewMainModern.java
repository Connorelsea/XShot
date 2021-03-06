package com.elsealabs.xshot.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.CaptureDevice;
import com.elsealabs.xshot.graphics.ColorContainer;
import com.elsealabs.xshot.graphics.ColorGlobalSet;
import com.elsealabs.xshot.program.Program;

public class ViewMainModern extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JPanel titlePanel;
	private JPanel containerPanel;

	private Timer  timerAlpha;
	private float  alpha = 0f;

	private int    animationState = 0;
	private double target;
	private double ypos;
	private Point  pos;

	private Font   font;
	private Font   fontLarge;

	int pX, pY;

	private ColorContainer container;

	public ViewMainModern()
	{
		_loadResources();
		build();

	}

	private void _loadResources()
	{
		try
		{
			System.setProperty("awt.useSystemAAFontSettings", "on");
			System.setProperty("swing.aatext", "true");

			System.out.println(Program.getInstance().getBuildType());

			if (Program.getInstance().getBuildType() == Program.BUILD_TYPE.JAR)
			{
				InputStream is = getClass().getResourceAsStream("/resources/font.ttf");
				font = Font.createFont(Font.TRUETYPE_FONT, is);
			}
			else if (Program.getInstance().getBuildType() == Program.BUILD_TYPE.IDE)
			{
				File file = new File("resources/font.ttf");
				font = Font.createFont(Font.TRUETYPE_FONT, file);
			}

			font = font.deriveFont(18f);
			fontLarge = font.deriveFont(40f);
		}
		catch (FontFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}

	public void build()
	{
		container = ColorGlobalSet.getInstance().getDefaultOf("main");
		addListeners();

		setUndecorated(true);
		setSize(600, 230);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		mainPanel = new JPanel();
		setContentPane(mainPanel);

		mainPanel.setBackground(container.getColor("dark"));
		mainPanel.setLayout(new BorderLayout());

		titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(0, 5, 0, 10));
		titlePanel.setBackground(container.getColor("dark"));
		titlePanel.setPreferredSize(new Dimension(titlePanel.getPreferredSize().width, 60));
		mainPanel.add(titlePanel, BorderLayout.NORTH);

		ModernLabel labelTitle = new ModernLabel("xshot", container.getColor("light"), fontLarge);
		titlePanel.add(labelTitle);

		ModernLabel labelElsea = new ModernLabel("beta version", container.getColor("light"), font);
		titlePanel.add(labelElsea);

		containerPanel = new JPanel();
		containerPanel.setLayout(new CardLayout());
		containerPanel.setBackground(container.getColor("med_dark"));
		mainPanel.add(containerPanel, BorderLayout.CENTER);

		JPanel buttonPanelContainer = new JPanel();
		buttonPanelContainer.setLayout(new BorderLayout());
		containerPanel.add(buttonPanelContainer);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanelContainer.add(buttonPanel, BorderLayout.CENTER);

		ModernButton button_freeform = new ModernButton(new ModernLabel(
				"freeform",
				container.getColor("dark"), fontLarge),
				container.getColor("med_dark"),
				container.getColor("med_dark").brighter(),
				a -> {
					// Hide View Main Modern before screenshot
					this.setVisible(false);
					
					// Capture the entire screen
					CaptureDevice device = new CaptureDevice();
					BufferedImage image  = device.captureAll();

					// Create a View Capture Rectangle to allow the user
					// to select the area of the image they want to keep.
					ViewCaptureRec disp  = new ViewCaptureRec(image);
					disp.build(device.mergeBounds(device.getMonitors()));

					dispose();
				});

		buttonPanel.add(button_freeform);

		ModernButton button_fullscreen = new ModernButton(new ModernLabel(
				"fullscreen",
				container.getColor("med_dark"), fontLarge),
				container.getColor("med_light"),
				container .getColor("med_light").brighter(),
				a -> {
					CaptureDevice device = new CaptureDevice();
					BufferedImage image  = device.captureAll();
					
					Rectangle total  = new Rectangle(0, 0, image.getWidth(), image.getHeight());
					Capture capture  = new Capture(image, total, total);
					ViewPicture view = new ViewPicture(capture);
					view.build();
				});
		buttonPanel.add(button_fullscreen);

		ModernButton button_timed = new ModernButton(new ModernLabel("timed",
				container.getColor("med_light"), fontLarge),
				container.getColor("light"),
				container.getColor("light").brighter(),
				a -> {

				});
		buttonPanel.add(button_timed);

		setOpacity(0.0f);
		setVisible(true);

		startAnimations();
	}

	public void startAnimations()
	{
		timerAlpha = new Timer(5, e ->
		{
            if (animationState == 0)
            {
                pos = getLocation();
                ypos = pos.getY();
                target = ypos - 100;

                animationState = 1;
            }

            if (animationState == 1)
            {
                double newpos = ypos += (target - ypos) / 5;
                pos.setLocation(pos.getX(), newpos);
                setLocation(pos);
            }

            if (alpha < 0.95f)
            {
                alpha += 0.05f;
                setOpacity(alpha);
            }
            else
            {
                setOpacity(1.0f);
                timerAlpha.stop();
            }
        });
		timerAlpha.start();
	}

	private void addListeners()
	{
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e)
			{
				pX = e.getX();
				pY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				setLocation(getLocation().x + e.getX() - pX, getLocation().y + e.getY() - pY);
			}
		});
	}

}