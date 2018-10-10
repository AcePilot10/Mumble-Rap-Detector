package com.codygordon.mumblerapdetector.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.codygordon.mumblerapdetector.dataobjects.ResultScreenData;
import com.codygordon.mumblerapdetector.dataobjects.SongData;
import com.codygordon.mumblerapdetector.util.duration.DurationFinder;
import com.codygordon.mumblerapdetector.util.lyricfinder.LyricFinder;
import com.codygordon.mumblerapdetector.util.wordcounter.WordCounter;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.GridLayout;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtArtist1;
	private JTextField txtTrack1;
	private JTextField txtArtist2;
	private JTextField txtTrack2;

	public MainScreen() {
		setTitle("Mumble Rap Detector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panelSong1 = new JPanel();
		panelSong1.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblArtist1 = new JLabel("Artist");
		lblArtist1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblArtist1.setHorizontalAlignment(SwingConstants.CENTER);
		panelSong1.add(lblArtist1);
		
		txtArtist1 = new JTextField();
		txtArtist1.setHorizontalAlignment(SwingConstants.LEFT);
		panelSong1.add(txtArtist1);
		txtArtist1.setColumns(10);
		
		JLabel lblTrack1 = new JLabel("Track");
		lblTrack1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTrack1.setHorizontalAlignment(SwingConstants.CENTER);
		panelSong1.add(lblTrack1);
		
		txtTrack1 = new JTextField();
		panelSong1.add(txtTrack1);
		txtTrack1.setColumns(10);
		
		JPanel panelSong2 = new JPanel();
		panelSong2.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblArtist2 = new JLabel("Artist");
		lblArtist2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblArtist2.setHorizontalAlignment(SwingConstants.CENTER);
		panelSong2.add(lblArtist2);
		
		txtArtist2 = new JTextField();
		txtArtist2.setColumns(10);
		panelSong2.add(txtArtist2);
		
		JLabel lblTrack2 = new JLabel("Track");
		lblTrack2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTrack2.setHorizontalAlignment(SwingConstants.CENTER);
		panelSong2.add(lblTrack2);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateButtonClicked();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(panelSong1, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
					.addGap(30)
					.addComponent(panelSong2, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(162)
					.addComponent(btnCalculate, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
					.addGap(173))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelSong1, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panelSong2, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
							.addGap(5)))
					.addGap(28)
					.addComponent(btnCalculate, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
					.addGap(6))
		);
		
		txtTrack2 = new JTextField();
		txtTrack2.setColumns(10);
		panelSong2.add(txtTrack2);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void calculateButtonClicked() {
		ResultScreenData data = getResultData();
		
		if(data.song1.lyrics == "Error") {
			String msg = "Could not find song named " + data.song1.title;
			JOptionPane.showMessageDialog(this, msg, "Song Not Found!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(data.song2.lyrics == "Error") {
			String msg = "Could not find song named " + data.song2.title;
			JOptionPane.showMessageDialog(this, msg, "Song Not Found!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		ResultScreen screen = new ResultScreen(data);
		screen.setVisible(true);
	}
	
	private ResultScreenData getResultData() {
		LyricFinder lyricFinder = new LyricFinder();
		WordCounter wordCounter = new WordCounter();
		
		String artist1 = txtArtist1.getText();
		String artist2 = txtArtist2.getText();
		
		String track1 = txtTrack1.getText();
		String track2 = txtTrack2.getText();
		
		String querySong1 = track1 + " " + artist1 + " audio";
		String querySong2 = track2 + " " + artist2 + " audio";
		
		String song1Lyrics = lyricFinder.GetLyrics(artist1, track1);
		String song2Lyrics = lyricFinder.GetLyrics(artist2, track2);
		
		int song1Duration = new DurationFinder().getDuration(querySong1);
		int song2Duration = new DurationFinder().getDuration(querySong2);
		
		int song1RawWords = wordCounter.countWords(song1Lyrics);
		int song1UniqueWords = wordCounter.countUniqueWords(song1Lyrics);
		
		int song2RawWords = wordCounter.countWords(song2Lyrics);
		int song2UniqueWords = wordCounter.countUniqueWords(song2Lyrics);
		
		SongData songData1 = new SongData();
		songData1.length = song1Duration;
		songData1.lyrics = song1Lyrics;
		songData1.rawWordCount = song1RawWords;
		songData1.uniqueWordCount = song1UniqueWords;
		songData1.title = track1;
		
		SongData songData2 = new SongData();
		songData2.length = song2Duration;
		songData2.lyrics = song2Lyrics;
		songData2.rawWordCount = song2RawWords;
		songData2.uniqueWordCount = song2UniqueWords;
		songData2.title = track2;
		
		ResultScreenData resultData = new ResultScreenData();
		resultData.song1 = songData1;
		resultData.song2 = songData2;
		
		return resultData;
	}
}