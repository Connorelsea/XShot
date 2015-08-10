package com.elsealabs.xshot.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.ClipboardCapture;
import com.elsealabs.xshot.file.FileUtil;
import com.elsealabs.xshot.math.Scale;

/**
 * PictureView.java
 *
 */
public class ViewPicture extends JFrame
{

	private JFrame instance;

	private String title;
	private int barHeight;

	private JPanel bar;
	private JPanel container;
	private PanelCapture panelCapture;

	private JScrollPane scrollPane;

	private WindowListener windowListener;
	private JButton buttonSave;
	private JButton buttonNew;
	private JButton buttonCopy;

	private ActionListener actionSave;
	private ActionListener actionNew;
	private ActionListener actionZoom;
	private ActionListener actionCopy;
	private ActionListener actionQuit;

	private ClipboardCapture clipCapture;
	private Capture capture;

	private String[] options;
	
	private Scale scale = Scale.getInstance();
	
	public ViewPicture(Capture capture)
	{
		this.capture = capture;

		instance = this;
		title = "Capture";
		barHeight = 90;

		options = new String[] { "Save", "Back", "Close" };

		addListeners();
	}

	public void build()
	{
		// Set size of window
		setSize(capture.getUpdatedBounds().width + 200,
				capture.getUpdatedBounds().height + barHeight + 200);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLookAndFeel();

		getContentPane().setLayout(new BorderLayout());

		// Top bar of the program. Has feature buttons.
		bar = new JPanel();
		bar.setLayout(new GridLayout(0, 5));
		bar.setPreferredSize(new Dimension(barHeight, barHeight));
		add(bar, BorderLayout.NORTH);

		// Adding elements to top bar

		actionSave = x ->
		{
			
			ViewSave viewSave = new ViewSave();
			viewSave.supplyCapture(capture);
			viewSave.supplyViewPicture(this);
			viewSave.init();
			viewSave.build();
			viewSave.setVisible(true);

			panelCapture.setSaved(true);
		};

		buttonSave = new JButton("Save");
		buttonSave.addActionListener(actionSave);
		bar.add(buttonSave);

		actionNew = x ->
		{
			this.setVisible(false);
			this.dispose();
			EventQueue.invokeLater(ViewMainModern::new);
		};

		buttonNew = new JButton("New");
		buttonNew.addActionListener(actionNew);
		bar.add(buttonNew);

		actionZoom = x ->
		{
			scale.setScale(scale.getScale() + .1f);
			
			Rectangle totalBounds = scale.scaleRectangle(capture.getTotalBounds());
			
			// Set size and position of container
			
			Dimension containerSize = new Dimension(
					totalBounds.width  + 500,
					totalBounds.height + 500
			);
			
			container.setSize(containerSize);
			container.setPreferredSize(containerSize);
			
			container.repaint();
			
			// Set size and position of panel capture
			
			Dimension panelCaptureSize = new Dimension(
					totalBounds.width,
					totalBounds.height
			);

			panelCapture.setSize(panelCaptureSize);
			panelCapture.setPreferredSize(panelCaptureSize);

			panelCapture.setLocation(
					((totalBounds.width + 500)  / 2) - (totalBounds.width  / 2),
					((totalBounds.height + 500) / 2) - (totalBounds.height / 2)
			);
			
			container.repaint();
			panelCapture.repaint();
		};
		
		// Copy image action and button
		
		actionCopy = x -> {
			clipCapture = new ClipboardCapture(capture);
			clipCapture.moveToClipboard();
		};

		buttonCopy = new JButton("Copy");
		buttonCopy.addActionListener(actionCopy);
		bar.add(buttonCopy);
		
		// Force quit no save action and button
		
		actionQuit = x -> {
			this.setVisible(false);
			this.dispose();
			System.exit(0);
		};
		
		JButton buttonQuit = new JButton("Quit, no save");
		buttonQuit.addActionListener(actionQuit);
		bar.add(buttonQuit);

		// Container for easier manipulation of the scroll pane.
		container = new JPanel();
		container.setLayout(null);
		container.setBackground(Color.LIGHT_GRAY);

		Dimension containerSize = new Dimension(
				capture.getTotalBounds().width  + 500,
				capture.getTotalBounds().height + 500
		);

		container.setSize(containerSize);
		container.setPreferredSize(containerSize);
		container.setLocation(0, 0);

		// Scroll pane with always-on scroll bars
		scrollPane = new JScrollPane();

		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

		add(scrollPane, BorderLayout.CENTER);

		scrollPane.getViewport().setLayout(null);
		scrollPane.getViewport().add(container);

		// Customized image panel
		panelCapture = new PanelCapture(scrollPane, (JFrame) this, capture);

		// Set size the same as the size of the full original image

		Dimension panelCaptureSize = new Dimension(
				capture.getTotalBounds().width,
				capture.getTotalBounds().height
		);

		panelCapture.setSize(panelCaptureSize);
		panelCapture.setPreferredSize(panelCaptureSize);

		panelCapture.setLocation(
				((capture.getTotalBounds().width + 500) / 2)  - (capture.getTotalBounds().width / 2),
				((capture.getTotalBounds().height + 500) / 2) - (capture.getTotalBounds().height / 2)
		);

		// Add panelCapture at static position
		container.add(panelCapture);

		setTitle(title);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addListeners()
	{
		windowListener = new WindowAdapter()
		{

			@Override
			public void windowClosing(WindowEvent e)
			{
				int n = JOptionPane
						.showOptionDialog(
								instance,
								"Your image is unsaved and will be lost. Are you sure you want to close?",
								"Warning: Capture Not Saved",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.WARNING_MESSAGE, null, options,
								options[0]);

				if (n == JOptionPane.YES_OPTION)
				{
					actionSave.actionPerformed(null);
				} else if (n == JOptionPane.NO_OPTION)
				{
					panelCapture.setSaved(false);
				} else
				{
					super.windowClosing(e);
					instance.dispose();
				}
			}

		};
		
		addWindowListener(windowListener);
	}

	private void setLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public ActionListener getActionSave()
	{
		return actionSave;
	}
	
	public ActionListener getActionNew()
	{
		return actionNew;
	}
	
	public WindowListener getWindowListener()
	{
		return windowListener;
	}

}