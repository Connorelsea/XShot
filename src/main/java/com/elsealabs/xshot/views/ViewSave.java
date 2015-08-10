package com.elsealabs.xshot.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.elsea.stone.property.PropertyPool;
import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.ClipboardCapture;
import com.elsealabs.xshot.file.SaveLocation;
import com.elsealabs.xshot.program.Program;

public class ViewSave extends JFrame {

	private JPanel      contentPane;
	private JTextField  field_fileName;
	
	private Program      program;
	private PropertyPool pool;
	private JComboBox    combo_locations;
	private JComboBox    combo_fileType;
	private JLabel       label_fileAlert;
	
	private List<String> positiveFileAlerts; 
	private boolean      correctFileName;
	
	private List<String> files;
	private File         defaultFilePath;
	private File         currentFile;
	
	private String       defaultExtension;
	private String       currentExtension;
	private List<String> extensions;
	
	private Capture          capture;
	private ClipboardCapture clipCapture;
	
	private ViewPicture viewPicture;
	
	public void init()
	{	
		program = Program.getInstance();
		pool    = program.getPool();
		
		positiveFileAlerts = new ArrayList<>();
		positiveFileAlerts.add("Good to go.");
		positiveFileAlerts.add("Ready to go.");
		positiveFileAlerts.add("Okay to save!");
		positiveFileAlerts.add("Ready to save!");
		positiveFileAlerts.add("Awesome!");
		positiveFileAlerts.add("Cool! Save it!");
		positiveFileAlerts.add("Neato!");
		positiveFileAlerts.add("Sweet!");
		positiveFileAlerts.add("Coolio!");
		positiveFileAlerts.add("Ready to savaroo!");
		positiveFileAlerts.add("Wahoo!");
		
		generateExtensions();
		addListeners();
		
	}
	
	public void generateExtensions() {
		extensions = Arrays.asList(ImageIO.getWriterFormatNames());
		if (extensions.contains("png")) defaultExtension = "png";
		else defaultExtension = extensions.get(0);
		
		currentExtension = defaultExtension;
	}
	
	public void updateCurrentFile()
	{
		if (field_fileName.getText().length() > 0)
		{
			currentFile = new File(
					defaultFilePath.getAbsolutePath() + 
					"\\" +
					field_fileName.getText() +
					"." + currentExtension
			);
			
			if (currentFile.exists())
			{
				correctFileName = false;
				label_fileAlert.setForeground(Color.RED);
				label_fileAlert.setText("File already exists. Name conflicts!");
			}
			else 
			{
				correctFileName = true;
				label_fileAlert.setForeground(Color.GRAY);
				label_fileAlert.setText("File does not already exist. " + positiveFileAlerts.get(new Random().nextInt((positiveFileAlerts.size() - 1 ) + 1)));
			}
		}
		else
		{
			correctFileName = true;
			label_fileAlert.setForeground(Color.RED);
			label_fileAlert.setText("File name not valid.");
		}
	}
	
	public void doCopy()
	{
		clipCapture = new ClipboardCapture(capture);
		clipCapture.moveToClipboard();
	}
	
	public void quit()
	{
		this.setVisible(false);
		this.dispose();
		System.exit(0);
	}
	
	public void flush()
	{
		if (correctFileName)
		{
			try {
				ImageIO.write(capture.getUpdatedImage(), currentExtension, currentFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			label_fileAlert.setForeground(Color.RED);
			label_fileAlert.setText("Unable to save file!");
		}
	}
	
	public void updateLocations()
	{
		if (files == null) files = new ArrayList<>();
		
		if (program.getSaveLocationPool().isUpdated())
		{
			
			for (SaveLocation sl : program.getSaveLocations())
			{
				System.out.println(sl.getPath());
				files.add(sl.getPath());
			}
		}
		
		combo_locations = new JComboBox(files.toArray());
	}

	/**
	 * Create the frame.
	 */
	public void build()
	{
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setBounds(100, 100, 384, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_title = new JLabel("Save Capture");
		label_title.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		label_title.setBounds(28, 11, 215, 44);
		contentPane.add(label_title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(28, 66, 314, 2);
		contentPane.add(separator);
		
		JLabel label_fileName = new JLabel("File Name:");
		label_fileName.setBounds(28, 75, 91, 14);
		contentPane.add(label_fileName);
		
		field_fileName = new JTextField();
		field_fileName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		field_fileName.setBounds(28, 95, 229, 38);
		contentPane.add(field_fileName);
		field_fileName.setColumns(10);
		
		// Listen for changes in the text field
		
		field_fileName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateCurrentFile();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateCurrentFile();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e)
			{
				updateCurrentFile();
			}
		});
		
		combo_fileType = new JComboBox(extensions.toArray());
		combo_fileType.setSelectedItem(defaultExtension);
		combo_fileType.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		combo_fileType.setBounds(267, 95, 75, 38);
		contentPane.add(combo_fileType);
		
		combo_fileType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					currentExtension = (String) e.getItem();
					updateCurrentFile();
				}
				
			}
		});
		
		JButton button_copySave = new JButton("Copy & Save");
		button_copySave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				doCopy();
				flush();
				if (correctFileName) quit();
			}
			
		});
		button_copySave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button_copySave.setBounds(193, 317, 149, 33);
		contentPane.add(button_copySave);
		
		label_fileAlert = new JLabel("File does not already exist. Good to go.");
		label_fileAlert.setForeground(Color.GRAY);
		label_fileAlert.setBounds(28, 144, 314, 14);
		contentPane.add(label_fileAlert);
		
		JButton button_cancel = new JButton("Cancel");
		button_cancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				dispose();
			}
		});
		button_cancel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button_cancel.setBounds(28, 361, 155, 33);
		contentPane.add(button_cancel);
		
		JButton button_copyQuit = new JButton("Copy & Quit");
		button_copyQuit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				doCopy();
				quit();
			}
			
		});
		button_copyQuit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button_copyQuit.setBounds(28, 317, 155, 33);
		contentPane.add(button_copyQuit);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(28, 203, 314, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(28, 304, 314, 2);
		contentPane.add(separator_2);
		
		JLabel label_defaultLocation = new JLabel("Default Save Location:");
		label_defaultLocation.setBounds(28, 211, 137, 14);
		contentPane.add(label_defaultLocation);
		
		JButton button_randomName = new JButton("Generate Random Name");
		button_randomName.setToolTipText("This will replace the file name in the text field with a random, non-conflicting, file name.");
		button_randomName.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				
			}
			
		});
		button_randomName.setBounds(193, 169, 149, 23);
		contentPane.add(button_randomName);
		
		JButton button_addLocation = new JButton("Add New Location");
		button_addLocation.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				program.getSaveLocationPool().addNewUI(new SaveLocation("", ""));
			}
		});
		button_addLocation.setBounds(193, 270, 149, 23);
		contentPane.add(button_addLocation);
		
		JButton button_editLocations = new JButton("Edit Current Location");
		button_editLocations.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				List<SaveLocation> locations = program.getSaveLocations();
				
				for (SaveLocation loc : locations)
				{
					if (loc.getPath().equals(combo_locations.getItemAt(combo_locations.getSelectedIndex())))
					{
						program.getSaveLocationPool().addNewUI(loc);
					}
				}
			}
			
		});
		button_editLocations.setBounds(28, 270, 155, 23);
		contentPane.add(button_editLocations);
		
		// Generate files
		combo_locations = new JComboBox();
		combo_locations.setBounds(28, 236, 314, 23);
		contentPane.add(combo_locations);
		
		updateLocations();
		
		// Resolve Conflict Button and Action
		
		JButton button_resolveConflict = new JButton("Resolve Conflict");
		button_resolveConflict.setToolTipText("This will append numbers to the end of your file name to resolve the conflicting file.");
		button_resolveConflict.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				int i = 0;
				
				String filePath = currentFile.getAbsolutePath();
				String fileName = field_fileName.getText();
				
				while (Files.exists(Paths.get(filePath)))
				{
					i += 1;
					fileName = field_fileName.getText() + i;
					filePath = defaultFilePath.getAbsolutePath() + "\\" + fileName + "." + currentExtension;
					System.out.println(filePath);
				}
				
				field_fileName.setText(fileName);
			}
			
		});
		button_resolveConflict.setBounds(28, 169, 155, 23);
		contentPane.add(button_resolveConflict);
		
		JButton button_save = new JButton("Save");
		button_save.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				flush();
				if (correctFileName) quit();
			}
			
		});
		button_save.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button_save.setBounds(193, 361, 149, 33);
		contentPane.add(button_save);
		
		setLocationRelativeTo(null);
	}
	
	private void addListeners()
	{
		addWindowListener(viewPicture.getWindowListener());
	}
	
	public JComboBox getCombo_locations() {
		return combo_locations;
	}
	
	public JComboBox getCombo_fileType() {
		return combo_fileType;
	}
	
	public JLabel getLabel_fileAlert() {
		return label_fileAlert;
	}
	
	public void supplyCapture(Capture capture) {
		this.capture = capture;
	}
	
	public void supplyViewPicture(ViewPicture viewPicture)
	{
		this.viewPicture = viewPicture;
	}
	
}