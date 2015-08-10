package com.elsealabs.xshot.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.elsea.stone.property.Property;
import com.elsea.stone.property.PropertyPool;
import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.ClipboardCapture;
import com.elsealabs.xshot.program.Program;

public class ViewSave extends JFrame {

	private JPanel contentPane;
	private JTextField field_fileName;
	
	private Program program;
	private PropertyPool pool;
	private JComboBox combo_locations;
	private JComboBox combo_fileType;
	private JLabel label_fileAlert;
	
	private List<String> positiveFileAlerts; 
	
	private File defaultFilePath;
	private File currentFile;
	private List<String> fileLocations;
	
	private String defaultExtension;
	private List<String> extensions;
	
	private Capture capture;
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
		
		generateFileLocations();
		generateExtensions();
		addListeners();
		
	}
	
	public void generateFileLocations()
	{
		fileLocations = new ArrayList<String>();
		Property prop = pool.search().getProperty("savePath");
		
		String defaultPath = prop.getCurrentValue();
		defaultFilePath = new File(defaultPath);
		fileLocations.add(defaultPath);
	}
	
	public void generateExtensions() {
		extensions = Arrays.asList(ImageIO.getWriterFormatNames());
		if (extensions.contains("png")) defaultExtension = "png";
		else defaultExtension = extensions.get(0);
	}
	
	public void updateCurrentFile()
	{
		currentFile = new File(
				defaultFilePath.getAbsolutePath() + 
				"\\" +
				field_fileName.getText() +
				".png"
		);
		
		System.out.println(currentFile.getAbsolutePath());
		
		if (currentFile.exists())
		{
			label_fileAlert.setForeground(Color.RED);
			label_fileAlert.setText("File already exists. Name conflicts!");
		}
		else 
		{
			label_fileAlert.setForeground(Color.GRAY);
			label_fileAlert.setText("File does not already exist. " + positiveFileAlerts.get(new Random().nextInt((positiveFileAlerts.size() - 1 ) + 1)));
		}
	}

	/**
	 * Create the frame.
	 */
	public void build()
	{
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JButton button_copySave = new JButton("Copy & Save");
		button_copySave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_cancel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button_cancel.setBounds(28, 361, 155, 33);
		contentPane.add(button_cancel);
		
		JButton button_copyQuit = new JButton("Copy & Quit");
		button_copyQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCopy();
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
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_randomName.setBounds(193, 169, 149, 23);
		contentPane.add(button_randomName);
		
		JButton button_addLocation = new JButton("Quick Add New Location");
		button_addLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_addLocation.setBounds(193, 270, 149, 23);
		contentPane.add(button_addLocation);
		
		JButton button_editLocations = new JButton("Edit Save Locations");
		button_editLocations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_editLocations.setBounds(28, 270, 155, 23);
		contentPane.add(button_editLocations);
		
		combo_locations = new JComboBox(fileLocations.toArray());
		combo_locations.setBounds(28, 236, 314, 23);
		contentPane.add(combo_locations);
		
		JButton button_resolveConflict = new JButton("Resolve Conflict");
		button_resolveConflict.setToolTipText("This will append numbers to the end of your file name to resolve the conflicting file.");
		button_resolveConflict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_resolveConflict.setBounds(28, 169, 155, 23);
		contentPane.add(button_resolveConflict);
		
		JButton button_save = new JButton("Save");
		button_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
	
	public void doCopy() {
		clipCapture = new ClipboardCapture(capture);
		clipCapture.moveToClipboard();
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