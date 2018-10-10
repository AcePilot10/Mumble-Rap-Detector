package com.codygordon.mumblerapdetector.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.codygordon.mumblerapdetector.dataobjects.ResultScreenData;
import com.codygordon.mumblerapdetector.dataobjects.SongData;
import com.codygordon.mumblerapdetector.util.duration.DurationFinder;
import com.codygordon.mumblerapdetector.util.lyricfinder.LyricFinder;
import com.codygordon.mumblerapdetector.util.wordcounter.WordCounter;

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
		contentPane.setLayout(null);
		
		JPanel panelSong1 = new JPanel();
		panelSong1.setBounds(10, 11, 192, 188);
		contentPane.add(panelSong1);
		panelSong1.setLayout(null);
		
		txtArtist1 = new JTextField();
		txtArtist1.setBounds(76, 23, 86, 20);
		panelSong1.add(txtArtist1);
		txtArtist1.setColumns(10);
		
		txtTrack1 = new JTextField();
		txtTrack1.setBounds(76, 108, 86, 20);
		panelSong1.add(txtTrack1);
		txtTrack1.setColumns(10);
		
		JLabel lblArtist1 = new JLabel("Artist");
		lblArtist1.setBounds(32, 26, 26, 14);
		panelSong1.add(lblArtist1);
		
		JLabel lblTrack1 = new JLabel("Track");
		lblTrack1.setBounds(32, 111, 26, 14);
		panelSong1.add(lblTrack1);
		
		JPanel panelSong2 = new JPanel();
		panelSong2.setLayout(null);
		panelSong2.setBounds(232, 11, 192, 183);
		contentPane.add(panelSong2);
		
		txtArtist2 = new JTextField();
		txtArtist2.setColumns(10);
		txtArtist2.setBounds(76, 23, 86, 20);
		panelSong2.add(txtArtist2);
		
		txtTrack2 = new JTextField();
		txtTrack2.setColumns(10);
		txtTrack2.setBounds(76, 108, 86, 20);
		panelSong2.add(txtTrack2);
		
		JLabel lblArtist2 = new JLabel("Artist");
		lblArtist2.setBounds(32, 26, 26, 14);
		panelSong2.add(lblArtist2);
		
		JLabel lblTrack2 = new JLabel("Track");
		lblTrack2.setBounds(32, 111, 26, 14);
		panelSong2.add(lblTrack2);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.setBounds(167, 227, 89, 23);
		btnCalculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateButtonClicked();
			}
		});
		contentPane.add(btnCalculate);
	}
	
	private void calculateButtonClicked() {
		ResultScreenData data = getResultData();
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
		
		SongData songData2 = new SongData();
		songData2.length = song2Duration;
		songData2.lyrics = song2Lyrics;
		songData2.rawWordCount = song2RawWords;
		songData2.uniqueWordCount = song2UniqueWords;
		
		ResultScreenData resultData = new ResultScreenData();
		resultData.song1 = songData1;
		resultData.song2 = songData2;
		
		return resultData;
	}
}