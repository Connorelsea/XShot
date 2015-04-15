package com.elsealabs.xshot.file;

import com.elsealabs.xshot.graphics.XImage;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class ImageSaver {

    private ArrayList<XImage> images;
    private File dest;

    private JFileChooser fileChooser;

    public ImageSaver()
    {
        images = new ArrayList<XImage>();
    }



    public void getLocationFromUser()
    {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save your Screenshot");

        File file = new File(fileChooser.getCurrentDirectory(), "Capture.png");
        fileChooser.setSelectedFile(file);

        int choice = fileChooser.showSaveDialog((JFrame) null);

        if (choice == JFileChooser.APPROVE_OPTION)
        {
            dest = fileChooser.getSelectedFile();
        }
    }

    public void flush()
    {

    }

    public void addImage(XImage image)
    {
        images.add(image);
    }

    public void removeImage(XImage image)
    {
        images.remove(image);
    }

    public ArrayList<XImage> getImages()
    {
        return images;
    }
}
