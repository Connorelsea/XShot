package com.elsealabs.xshot.views;

import com.elsealabs.xshot.file.FileUtil;
import com.elsealabs.xshot.graphics.Capture;
import com.elsealabs.xshot.graphics.XImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * PictureView.java
 *
 */
public class ViewPicture extends JFrame {

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

    public ViewPicture(Capture capture)
    {
        this.capture = capture;

        title = "Capture";
        barHeight = 90;
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
            capture.getBoundedImage().writeImage(dest, XImage.FORMAT.PNG);
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

        // Container for easier maniuplation of the scroll pane.
        container = new JPanel();
        container.setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);

        // Scroll pane with always-on scroll bars
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.add(scrollPane, BorderLayout.CENTER);

        // Customized image panel
        panelCapture = new PanelCapture(scrollPane, (JFrame) this, capture);
        panelCapture.setBorder(null);
        panelCapture.setPreferredSize(
                new Dimension(
                        capture.getBoundedImage().getWidth() + 500,
                        capture.getBoundedImage().getHeight() + 500
                )
        );
        scrollPane.getViewport().add(panelCapture);

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