package com.codygordon.mumblerapdetector.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.codygordon.mumblerapdetector.dataobjects.ResultScreenData;

public class ResultScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	
	public ResultScreen(ResultScreenData data) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Word Count", data.song1.rawWordCount, data.song2.rawWordCount},
				{"Unique Word Count", data.song1.uniqueWordCount, data.song2.uniqueWordCount},
				{"Words Per Second", data.song1.getWordsPerSecond(), data.song2.getWordsPerSecond()},
			},
			new String[] {
				"Info", "Song 1", "Song 2"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(186);
		table.setBounds(80, 38, 213, 153);
		contentPane.add(table);
	}
}
