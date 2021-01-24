package Visualization;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class HelpWindow extends JFrame implements ActionListener {
	// Initialize Fonts
	private static final Font HELP_LABEL_FONT = new Font("Comic Sans MS", Font.PLAIN, 18);
	private static final Font HELP_BUTTON_FONT = new Font("Comic Sans MS", Font.PLAIN, 20);

	// Initialize Help Window Background Color
	private static final Color HELP_BACKGROUND = new Color(204, 255, 255);
	private static final Color PAGE_DIRECTION_BUTTON = new Color(106, 228, 87);
	private static final Color EXIT_BUTTON = new Color(249, 90, 90);

	// Help Buttons
	private JButton next = new JButton("Next");
	private JButton back = new JButton("Back");
	private JButton exit = new JButton("Exit");

	// Help Window
	private JFrame helpMenu = new JFrame();

	// Help Labels
	private JLabel h1 = new JLabel();
	private JLabel h2 = new JLabel();
	private JLabel h3 = new JLabel();
	private JLabel h4 = new JLabel();
	private JLabel h5 = new JLabel();
	private JLabel h6 = new JLabel();
	private JLabel h7 = new JLabel();
	private JLabel h8 = new JLabel();

	// Screen Number
	private int screenNum = 1;

	public HelpWindow() {
		// Help Window Settings
		helpMenu.setSize(750, 400);
		helpMenu.setLayout(null);
		helpMenu.setLocationRelativeTo(null);
		helpMenu.setResizable(false);
		helpMenu.getContentPane().setBackground(HELP_BACKGROUND);
		helpMenu.setUndecorated(true);

		h1.setText("1) Select a Blue Maze Generation Algorithm to Generate a Maze.");
		h2.setText("        - Manual Generation is Controlled By Mouse Click.");
		h3.setText("        - Left Click on the Maze to Create Walls, and Right Click to Delete Walls.");

		LabelSettings(h1, 50, 50, 650, 50);
		LabelSettings(h2, 50, 80, 650, 50);
		LabelSettings(h3, 50, 110, 650, 50);

		h4.setText("2) Create Start and Destination Nodes By Selecting Purple Buttons.");
		h5.setText("        - Left Click to Add Start or Destination to Maze.");
		h6.setText("        - Right Click on Start or Destination to Delete From Maze.");

		LabelSettings(h4, 50, 50, 650, 50);
		LabelSettings(h5, 50, 80, 650, 50);
		LabelSettings(h6, 50, 110, 650, 50);

		h7.setText("3) Select an Orange Pathfinding Algorithm.");
		h8.setText("        - Algorithm will Pathfind Between the Start and Destination Node.");

		LabelSettings(h7, 50, 50, 650, 50);
		LabelSettings(h8, 50, 80, 650, 50);

		// Next Button Settings
		SetButtonSettings(next);
		SetButtonColour(next, PAGE_DIRECTION_BUTTON, Color.BLACK);
		next.setBounds(600, 250, 100, 50);

		// Back Button Settings
		SetButtonSettings(back);
		SetButtonColour(back, PAGE_DIRECTION_BUTTON, Color.BLACK);
		back.setBounds(50, 250, 100, 50);

		// Exit Button Settings
		SetButtonSettings(exit);
		SetButtonColour(exit, EXIT_BUTTON, Color.BLACK);
		exit.setBounds(325, 250, 100, 50);

		helpMenu.setVisible(true);

		// Initial Screen
		Screen1();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == next) {
			if (screenNum == 1) {
				Screen2();
				screenNum = 2;

			} else if (screenNum == 2) {
				Screen3();
				screenNum = 3;
			}
		}

		if (event.getSource() == back) {
			if (screenNum == 2) {
				Screen1();
				screenNum = 1;

			} else if (screenNum == 3) {
				Screen2();
				screenNum = 2;
			}
		}

		if (event.getSource() == exit) {
			Dispose();
		}
	}

	private void Screen1() {
		ClearWindow();

		h1.setVisible(true);
		h2.setVisible(true);
		h3.setVisible(true);

		next.setVisible(true);
	}

	private void Screen2() {
		ClearWindow();

		h4.setVisible(true);
		h5.setVisible(true);
		h6.setVisible(true);

		next.setVisible(true);
		back.setVisible(true);
	}

	private void Screen3() {
		ClearWindow();

		h7.setVisible(true);
		h8.setVisible(true);

		back.setVisible(true);
	}

	public void ClearWindow() {
		h1.setVisible(false);
		h2.setVisible(false);
		h3.setVisible(false);
		h4.setVisible(false);
		h5.setVisible(false);
		h6.setVisible(false);
		h7.setVisible(false);
		h8.setVisible(false);

		next.setVisible(false);
		back.setVisible(false);
	}

	public void Dispose() {
		helpMenu.dispose();
		MazeVisualizer.setHelpWindowState(0);
	}

	public void LabelSettings(JLabel label, int x1, int y1, int x2, int y2) {
		label.setFont(HELP_LABEL_FONT);
		label.setBounds(x1, y1, x2, y2);
		helpMenu.add(label);
	}

	public void SetButtonSettings(JButton button) {
		button.setFont(HELP_BUTTON_FONT);
		button.setBorderPainted(true);
		button.setFocusPainted(false);
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setVisible(true);
		button.addActionListener(this);
		helpMenu.add(button);
	}

	public void SetButtonColour(JButton button, Color bg, Color fg) {
		button.setBackground(bg);
		button.setForeground(fg);
	}
}
