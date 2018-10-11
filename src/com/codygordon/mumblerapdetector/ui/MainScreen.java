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
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.Box;
import java.awt.SystemColor;
import java.awt.event.MouseMotionAdapter;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtArtist1;
	private JTextField txtTrack1;
	private JTextField txtArtist2;
	private JTextField txtTrack2;
	
	private int xx;
	private int xy;

	public MainScreen() {
		setResizable(false);
		setTitle("Mumble Rap Detector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 603, 438);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				MainScreen.this.setLocation(x - xx, y - xy);
			}
		});
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.setBackground(Color.WHITE);
		btnCalculate.setBounds(214, 375, 188, 52);
		btnCalculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateButtonClicked();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnCalculate);
		
		JLabel lblExit = new JLabel("X");
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblExit.setForeground(Color.RED);
		lblExit.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblExit.setBounds(554, 11, 39, 38);
		contentPane.add(lblExit);
		
		JLabel lblTitle1 = new JLabel("Song 1");
		lblTitle1.setForeground(Color.WHITE);
		lblTitle1.setFont(new Font("Arial", Font.PLAIN, 25));
		lblTitle1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle1.setBounds(96, 24, 98, 38);
		contentPane.add(lblTitle1);
		
		JLabel lblArtist = new JLabel("Artist");
		lblArtist.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblArtist.setForeground(SystemColor.activeCaptionBorder);
		lblArtist.setBounds(83, 92, 72, 22);
		contentPane.add(lblArtist);
		
		txtArtist1 = new JTextField();
		txtArtist1.setBounds(83, 125, 141, 38);
		contentPane.add(txtArtist1);
		txtArtist1.setColumns(10);
		
		JLabel lblTrack = new JLabel("Track");
		lblTrack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTrack.setForeground(SystemColor.activeCaptionBorder);
		lblTrack.setBounds(83, 208, 83, 22);
		contentPane.add(lblTrack);
		
		txtTrack1 = new JTextField();
		txtTrack1.setColumns(10);
		txtTrack1.setBounds(83, 241, 141, 38);
		contentPane.add(txtTrack1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setBackground(Color.LIGHT_GRAY);
		verticalStrut.setForeground(Color.WHITE);
		verticalStrut.setBounds(311, 11, -27, 328);
		contentPane.add(verticalStrut);
		
		JLabel lblTitle2 = new JLabel("Song 2");
		lblTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle2.setForeground(Color.WHITE);
		lblTitle2.setFont(new Font("Arial", Font.PLAIN, 25));
		lblTitle2.setBounds(384, 24, 98, 38);
		contentPane.add(lblTitle2);
		
		JLabel lblArtist2 = new JLabel("Artist");
		lblArtist2.setForeground(SystemColor.activeCaptionBorder);
		lblArtist2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblArtist2.setBounds(369, 92, 72, 22);
		contentPane.add(lblArtist2);
		
		txtArtist2 = new JTextField();
		txtArtist2.setColumns(10);
		txtArtist2.setBounds(369, 125, 141, 38);
		contentPane.add(txtArtist2);
		
		JLabel label_1 = new JLabel("Track");
		label_1.setForeground(SystemColor.activeCaptionBorder);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_1.setBounds(369, 208, 83, 22);
		contentPane.add(label_1);
		
		txtTrack2 = new JTextField();
		txtTrack2.setColumns(10);
		txtTrack2.setBounds(369, 241, 141, 38);
		contentPane.add(txtTrack2);
	}
	
	private void calculateButtonClicked() {
		ResultScreenData data = getResultData();
		
		if(data == null) {
			JOptionPane.showMessageDialog(this, "An error occured!", "Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
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
		try {
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
		catch(Exception e) {
			return null;
		}
	}
}