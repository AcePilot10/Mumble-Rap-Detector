package com.codygordon.mumblerapdetector;

import java.awt.EventQueue;

import com.codygordon.mumblerapdetector.ui.MainScreen;

public class MumbleRapDetector {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}