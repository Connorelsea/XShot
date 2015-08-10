package com.elsealabs.xshot.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.elsealabs.xshot.file.FileUtil;
import com.elsealabs.xshot.file.SaveLocation;
import com.elsealabs.xshot.file.SaveLocationPool;

public class ViewFileLocation extends JFrame {

	private JPanel contentPane;
	private JTextField field_name;
	private JTextField field_path;
	
	private SaveLocation save;
	private SaveLocationPool pool;

	public ViewFileLocation(SaveLocation save)
	{
		this.save = save;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 480, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_name = new JLabel("Location Nick Name");
		label_name.setBounds(10, 11, 112, 14);
		contentPane.add(label_name);
		
		JLabel label_path = new JLabel("Location Path");
		label_path.setBounds(10, 38, 112, 14);
		contentPane.add(label_path);
		
		field_name = new JTextField();
		field_name.setBounds(132, 8, 322, 20);
		contentPane.add(field_name);
		field_name.setColumns(10);
		
		field_name.setText(save.getName());
		
		field_path = new JTextField();
		field_path.setColumns(10);
		field_path.setBounds(132, 35, 322, 20);
		contentPane.add(field_path);
		
		field_path.setText(save.getPath());
		
		JButton button_path = new JButton("Choose Path");
		button_path.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				File file = FileUtil.getUserSaveLocation(new File(field_path.getText()), "Select New Path Location");
				field_path.setText(file.toPath().toString());
			}
			
		});
		button_path.setBounds(10, 107, 112, 23);
		contentPane.add(button_path);
		
		JButton button_save = new JButton("Save");
		button_save.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				if (Files.exists(Paths.get(field_path.getText())))
				{
					save.setName(field_name.getText());
					save.setPath(field_path.getText());
					
					pool.saveLocation(save);
					
					dispose();
				}
				else
				{
					setTitle("Path does not exist, can't save!");
				}
			}
			
		});
		button_save.setBounds(342, 107, 112, 23);
		contentPane.add(button_save);
		
		JButton button_delete = new JButton("Delete");
		button_delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				save.remove();
			}
			
		});
		button_delete.setBounds(261, 107, 71, 23);
		contentPane.add(button_delete);
		
		JCheckBox check_default = new JCheckBox("Default Location");
		check_default.addItemListener(new ItemListener()
		{
			
			public void itemStateChanged(ItemEvent e)
			{
				if (check_default.isSelected())
				{
					save.setDefault(true);
					check_default.setSelected(true);
				}
				else
				{
					save.setDefault(false);
					check_default.setSelected(false);
				}
			}
			
		});
		check_default.setBounds(10, 70, 117, 23);
		contentPane.add(check_default);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				dispose();
			}
			
		});
		btnCancel.setBounds(180, 107, 71, 23);
		contentPane.add(btnCancel);
		
		if (save.isDefault()) check_default.setSelected(true);
	}

	public void setPool(SaveLocationPool pool) {
		this.pool = pool;
	}
}
