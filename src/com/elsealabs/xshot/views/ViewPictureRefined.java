package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.Capture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;

/**
 * PictureView.java
 *
 */
public class ViewPictureRefined extends JFrame {

    private String title;
    private int barHeight;

    private JPanel bar;
    private JPanel container;
    private JPanel imagePanel;

    private JScrollPane scrollPane;

    private Capture capture;

    public ViewPictureRefined(Capture capture)
    {
        this.capture = capture;

        title = "Capture";
        barHeight = 90;
    }

    public void build()
    {
        setTitle(title);

        setSize(
                (int) capture.getOriginalBounds().getWidth() + 200,
                (int) capture.getOriginalBounds().getHeight() + barHeight + 200
        );

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLookAndFeel();

        getContentPane().setLayout(new BorderLayout());

        bar = new JPanel();
        bar.setLayout(new GridBagLayout());
        bar.setPreferredSize(new Dimension(barHeight, barHeight));
        add(bar, BorderLayout.NORTH);

        container = new JPanel();
        container.setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.add(scrollPane, BorderLayout.CENTER);

        imagePanel = new JPanel()
        {
            public void paint(Graphics gd)
            {
                Graphics2D g = (Graphics2D) gd;

                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());

                // DRAW IMAGE

                int imageX = (getWidth() / 2)  - (capture.getBoundedImage().getWidth() / 2);
                int imageY = (getHeight() / 2) - (capture.getBoundedImage().getHeight() / 2);

                int padding_x = (int) (scrollPane.getWidth() - capture.getUpdatedBounds().getWidth()) / 2;
                int padding_y = (int) (scrollPane.getHeight() - capture.getUpdatedBounds().getHeight()) / 2;

                scrollPane.getViewport().setViewPosition(
                        new Point(
                                (int) imageX - padding_x,
                                (int) imageY - padding_y
                        )
                );

                g.drawImage(
                        capture.getBoundedImage().getBufferedImage(),
                        imageX,
                        imageY,
                        null
                );
            }
        };
        imagePanel.setBorder(null);
        imagePanel.setPreferredSize(
                new Dimension(
                        capture.getBoundedImage().getWidth() + 500,
                        capture.getBoundedImage().getHeight() + 500
                )
        );
        scrollPane.getViewport().add(imagePanel);

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