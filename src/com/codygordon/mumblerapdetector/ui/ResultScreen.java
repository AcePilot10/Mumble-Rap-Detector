package com.codygordon.mumblerapdetector.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.codygordon.mumblerapdetector.dataobjects.ResultScreenData;

public class ResultScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	
	public ResultScreen(ResultScreenData data) {
		setTitle("Results");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		table = new JTable();
		table.setBounds(5, 5, 424, 204);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Songs", data.song1.title, data.song2.title},
				{"Duration", data.song1.length + " seconds", data.song2.length + " seconds"},
				{"Word Count", data.song1.rawWordCount, data.song2.rawWordCount},
				{"Unique Word Count", data.song1.uniqueWordCount, data.song2.uniqueWordCount},
				{"Words Per Second", data.song1.getWordsPerSecond(), data.song2.getWordsPerSecond()},
			},
			new String[] {
				"Info", "Song 1", "Song 2"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(186);
		contentPane.setLayout(null);
		contentPane.add(table);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(173, 227, 89, 23);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			save();
			}
		});
		contentPane.add(btnSave);
	}
	
	public void save() {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save Location");
		
		JButton btnOpen = new JButton();
		if(fileChooser.showOpenDialog(btnOpen) == JFileChooser.APPROVE_OPTION) {
		
	        Rectangle r = table.getBounds();
	        try {
	            BufferedImage img = new BufferedImage(r.width, r.height,
	                    BufferedImage.TYPE_INT_RGB);
	            
	            Graphics2D g2 = img.createGraphics();
	    		paint(g2);
	            
	            ImageIO.write(img, "png", fileChooser.getSelectedFile());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
}
