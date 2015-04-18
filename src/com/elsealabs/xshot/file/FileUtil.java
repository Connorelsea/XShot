package com.elsealabs.xshot.file;

import javax.swing.*;
import java.io.File;

public class FileUtil {

    public File getUserSaveLocation(File defaultFile, String title)
    {
        JFileChooser fileChooser = new JFileChooser();
        File dest = new File("empty");

        fileChooser.setDialogTitle(title);
        fileChooser.setSelectedFile(defaultFile);

        int choice = fileChooser.showSaveDialog((JFrame) null);

        if (choice == JFileChooser.APPROVE_OPTION)
        {
            dest = fileChooser.getSelectedFile();
        }
        else
        {
            System.out.println("FileUtil.java: User canceled dialog.");
            // TODO: Handle error
        }
        return dest;
    }

}
