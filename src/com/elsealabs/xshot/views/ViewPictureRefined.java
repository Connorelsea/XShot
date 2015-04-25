package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.Capture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * PictureView.java
 *
 */
public class ViewPictureRefined extends JFrame {

    private String title;
    private int barHeight;

    private JPanel bar;
    private JPanel container;
    private PanelImage panelImage;

    private JScrollPane scrollPane;

    private Capture capture;

    private Dimension prevWindowSize;

    public ViewPictureRefined(Capture capture)
    {
        this.capture = capture;

        title = "Capture";
        barHeight = 90;
    }

    public void build()
    {
        // Set size of window
        setSize(
                (int) capture.getOriginalBounds().getWidth() + 200,
                (int) capture.getOriginalBounds().getHeight() + barHeight + 200
        );

        // Store the initial size to use  when  window
        // is being resized. If the size changes, then
        // the program knows the window has been resized.
        prevWindowSize = getSize();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLookAndFeel();

        getContentPane().setLayout(new BorderLayout());

        // Top bar of the program. Has feature buttons.
        bar = new JPanel();
        bar.setLayout(new GridBagLayout());
        bar.setPreferredSize(new Dimension(barHeight, barHeight));
        add(bar, BorderLayout.NORTH);

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
        panelImage = new PanelImage(scrollPane, capture);
        panelImage.setBorder(null);
        panelImage.setPreferredSize(
                new Dimension(
                        capture.getBoundedImage().getWidth() + 500,
                        capture.getBoundedImage().getHeight() + 500
                )
        );
        scrollPane.getViewport().add(panelImage);

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

        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
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