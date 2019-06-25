package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import listeners.StartPanelListener;
import model.MyUtils;

public class StartPane extends JPanel {

	/**
	 * This creates the main menu
	 */
	private static final long serialVersionUID = -8071464264687094409L;
	private StartPanelListener listener;
	private ImageIcon pressed;
	private ImageIcon hover;
	private ImageIcon icon;
	private Color color1 = new Color(251, 208, 158);
	private Color color2 = new Color(62, 54, 54);

	/**
	 * Create the panel.
	 */
	public StartPane() {
		pressed = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		hover = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		icon = new ImageIcon(getClass().getResource("/button.png"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 1084, 0 };
		gridBagLayout.rowHeights = new int[] { 144, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4.9E-324 };
		setLayout(gridBagLayout);

		JButton btnNewButton = new JButton("Start New Game");
		btnNewButton.setSelectedIcon(pressed);
		btnNewButton.setForeground(color1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireStartGame();
			}
		});

		JLabel lblNewLabel = new JLabel("Legend of Papyrus");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(MyUtils.getFont(Font.BOLD, 50));
		lblNewLabel.setForeground(color1);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(200, 0, 50, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		btnNewButton.setIcon(icon);
		btnNewButton.setRolloverEnabled(true);
		btnNewButton.setRolloverIcon(hover);
		btnNewButton.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnNewButton.setHorizontalTextPosition(JButton.CENTER);
		btnNewButton.setVerticalTextPosition(JButton.CENTER);
		btnNewButton.setBorder(BorderFactory.createEmptyBorder());
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);

		JButton button = new JButton("Settings");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireSettingsStart();
			}
		});
		button.setForeground(color1);
		button.setIcon(icon);
		button.setFont(MyUtils.getFont(Font.BOLD, 20));
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setRolloverEnabled(true);
		button.setRolloverIcon(hover);
		button.setVerticalTextPosition(JButton.CENTER);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		button.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 0;
		gbc_button.gridy = 2;
		add(button, gbc_button);

		JButton button_1 = new JButton("Load Game");
		button_1.setForeground(color1);
		button_1.setIcon(icon);
		button_1.setFont(MyUtils.getFont(Font.BOLD, 20));
		button_1.setRolloverEnabled(true);
		button_1.setRolloverIcon(hover);
		button_1.setHorizontalTextPosition(JButton.CENTER);
		button_1.setVerticalTextPosition(JButton.CENTER);
		button_1.setBorder(BorderFactory.createEmptyBorder());
		button_1.setContentAreaFilled(false);
		button_1.setVerticalTextPosition(SwingConstants.CENTER);
		button_1.setHorizontalTextPosition(SwingConstants.CENTER);
		button_1.setFont(MyUtils.getFont(Font.BOLD, 20));
		button_1.setContentAreaFilled(false);
		button_1.setBorder(BorderFactory.createEmptyBorder());
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 3;

		// add(button_1, gbc_button_1);

		JButton btnHighScores = new JButton("High Scores");
		btnHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireHighScores();
			}
		});
		btnHighScores.setForeground(color1);
		btnHighScores.setIcon(icon);
		btnHighScores.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnHighScores.setRolloverEnabled(true);
		btnHighScores.setRolloverIcon(hover);
		btnHighScores.setHorizontalTextPosition(JButton.CENTER);
		btnHighScores.setVerticalTextPosition(JButton.CENTER);
		btnHighScores.setBorder(BorderFactory.createEmptyBorder());
		btnHighScores.setContentAreaFilled(false);
		btnHighScores.setVerticalTextPosition(SwingConstants.CENTER);
		btnHighScores.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHighScores.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnHighScores.setContentAreaFilled(false);
		btnHighScores.setBorder(BorderFactory.createEmptyBorder());
		GridBagConstraints gbc_btnHighScores = new GridBagConstraints();
		gbc_btnHighScores.insets = new Insets(0, 0, 5, 0);
		gbc_btnHighScores.gridx = 0;
		gbc_btnHighScores.gridy = 3;
		add(btnHighScores, gbc_btnHighScores);
		button_1.setContentAreaFilled(false);

		JButton button_exit = new JButton("Exit");
		button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int action = JOptionPane.showConfirmDialog(StartPane.this,
						"This action will close the game without saving.",
						"are you sure you want to complete this action?", JOptionPane.YES_NO_OPTION);
				if (action == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		button_exit.setForeground(color1);
		button_exit.setIcon(icon);
		button_exit.setFont(MyUtils.getFont(Font.BOLD, 20));
		button_exit.setRolloverEnabled(true);
		button_exit.setRolloverIcon(hover);
		button_exit.setHorizontalTextPosition(JButton.CENTER);
		button_exit.setVerticalTextPosition(JButton.CENTER);
		button_exit.setBorder(BorderFactory.createEmptyBorder());
		button_exit.setVerticalTextPosition(SwingConstants.CENTER);
		button_exit.setHorizontalTextPosition(SwingConstants.CENTER);
		GridBagConstraints gbc_button_exit = new GridBagConstraints();
		gbc_button_exit.insets = new Insets(0, 0, 5, 0);
		gbc_button_exit.gridx = 0;
		gbc_button_exit.gridy = 4;

		add(button_exit, gbc_button_exit);

	}

	public void setListener(StartPanelListener listener) {
		this.listener = listener;
	}

	private void fireStartGame() {
		if (listener != null)
			listener.startGame(MainFrame.PLAYER_SELECT_PANEL);
	}

	private void fireSettingsStart() {
		if (listener != null)
			listener.startGame(MainFrame.SETTINGS_PANEL);
	}

	private void fireHighScores() {
		if (listener != null)
			listener.startGame(MainFrame.HIGH_SCORE_PANEL);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		// g.drawImage(bgImage, 0, 0, null);//set color/ image whatever
		g.setColor(color2);
		g.fillRect(0, 0, 2000, 2000);
	}
}
