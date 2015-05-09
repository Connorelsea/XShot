package com.elsealabs.xshot.views;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.file.FileUtil;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * PictureView.java
 *
 */
public class ViewPicture extends JFrame {

	private JFrame instance;

    private String title;
    private int barHeight;

    private JPanel bar;
    private JPanel container;
    private PanelCapture panelCapture;

    private JScrollPane scrollPane;

    private JButton buttonSave;
    private JButton buttonNew;
    private JButton buttonCopy;

    private ActionListener actionSave;
    private ActionListener actionNew;
    private ActionListener actionCopy;

    private Capture capture;

	private String[] options;

    public ViewPicture(Capture capture)
    {
        this.capture = capture;

		instance = this;
        title = "Capture";
        barHeight = 90;

		options = new String[] {"Save", "Back", "Close"};

		addListeners();
    }

    public void build() {
        // Set size of window
        setSize(
                (int) capture.getOriginalBounds().getWidth() + 200,
                (int) capture.getOriginalBounds().getHeight() + barHeight + 200
        );

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
            File defaultFile = new File("C:\\Capture.PNG");
            File dest = new FileUtil().getUserSaveLocation(defaultFile, "Save Image");
            
            // TODO: re-implement image saving
            //capture.getUpdatedImage().writeImage(dest, XImage.FORMAT.PNG);
        };

        buttonSave = new JButton("Save");
        buttonSave.addActionListener(actionSave);
        bar.add(buttonSave);

        actionNew = x ->
        {

        };

        buttonNew = new JButton("New");
        buttonNew.addActionListener(actionNew);
        bar.add(buttonNew);

        actionCopy = x ->
        {

        };

        buttonCopy = new JButton("Copy");
        buttonCopy.addActionListener(actionCopy);
        bar.add(buttonCopy);

        // Container for easier manipulation of the scroll pane.
        container = new JPanel();
        container.setLayout(null);
        container.setBackground(Color.BLACK);
        
        Dimension containerSize = new Dimension(
				(int) capture.getFullBounds().getWidth() + 500, 
				(int) capture.getFullBounds().getHeight() + 500
		);
        
        container.setSize(containerSize);
        container.setPreferredSize(containerSize);
        container.setLocation(0, 0);

        // Scroll pane with always-on scroll bars
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
        
        scrollPane.getViewport().setLayout(null);
        scrollPane.getViewport().add(container);

        // Customized image panel
        panelCapture = new PanelCapture(scrollPane, (JFrame) this, capture);
        
        // Set size the same as the size of the full original image
        
        Dimension panelCaptureSize = new Dimension(
                capture.getFullImage().getWidth(),
                capture.getFullImage().getHeight()
        );
        
        panelCapture.setSize(panelCaptureSize);
        panelCapture.setPreferredSize(panelCaptureSize);
        
        panelCapture.setLocation(
        		((capture.getFullImage().getWidth()  + 500) / 2) - (capture.getFullImage().getWidth()  / 2),
        		((capture.getFullImage().getHeight() + 500) / 2) - (capture.getFullImage().getHeight() /  2)
        );
        
        // Add panelCapture at static position
        container.add(panelCapture);

        setTitle(title);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addListeners()
    {
        addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
				int n = JOptionPane.showOptionDialog(
						instance,
						"Your image is unsaved and will be lost. Are you sure you want to close?",
						"Confirm Close",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null,
						options,
						options[0]
				);

				if (n == JOptionPane.YES_OPTION)
				{
					actionSave.actionPerformed(null);
				}
				else if (n == JOptionPane.NO_OPTION)
				{

				}
				else
				{
					instance.dispose();
				}

                super.windowClosing(e);
            }

        });
    }

    private void setLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}