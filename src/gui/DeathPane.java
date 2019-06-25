package gui;

import javax.swing.JPanel;

import listeners.StartPanelListener;
import model.DifficultySettings;
import model.MyUtils;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class DeathPane extends JPanel {

	/**
	 * This class is the panel shown when the hero dies
	 */
	private static final long serialVersionUID = -699004365209230445L;

	private StartPanelListener listener;
	private int coins;
	private int enemiesKilled;
	private int coinsInWeapons;
	private Integer score;
	private Color color1 = new Color(251, 208, 158);
	private Color color2 = new Color(62, 54, 54);
	private ImageIcon pressed;
	private ImageIcon hover;
	private JLabel lblScoreGoesHere;

	/**
	 * Create the panel.
	 */
	public DeathPane() {
		pressed = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		hover = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		score = new Integer(0);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 1157, 0 };
		gridBagLayout.rowHeights = new int[] { 181, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblYouDied = new JLabel("You died");
		lblYouDied.setForeground(color1);
		lblYouDied.setVerticalAlignment(SwingConstants.BOTTOM);
		lblYouDied.setHorizontalAlignment(SwingConstants.CENTER);
		lblYouDied.setIcon(null);
		lblYouDied.setFont(MyUtils.getFont(Font.BOLD, 50));
		GridBagConstraints gbc_lblYouDied = new GridBagConstraints();
		gbc_lblYouDied.insets = new Insets(100, 0, 5, 0);
		gbc_lblYouDied.gridx = 0;
		gbc_lblYouDied.gridy = 0;
		add(lblYouDied, gbc_lblYouDied);

		JLabel lblYourScoreWas = new JLabel("Your score was:");
		lblYourScoreWas.setForeground(color1);
		lblYourScoreWas.setFont(MyUtils.getFont(Font.BOLD, 30));
		GridBagConstraints gbc_lblYourScoreWas = new GridBagConstraints();
		gbc_lblYourScoreWas.insets = new Insets(0, 0, 5, 0);
		gbc_lblYourScoreWas.gridx = 0;
		gbc_lblYourScoreWas.gridy = 1;
		add(lblYourScoreWas, gbc_lblYourScoreWas);

		lblScoreGoesHere = new JLabel(score.toString());
		lblScoreGoesHere.setForeground(color1);
		lblScoreGoesHere.setFont(MyUtils.getFont(Font.BOLD, 30));
		GridBagConstraints gbc_lblScoreGoesHere = new GridBagConstraints();
		gbc_lblScoreGoesHere.insets = new Insets(50, 0, 5, 0);
		gbc_lblScoreGoesHere.gridx = 0;
		gbc_lblScoreGoesHere.gridy = 2;
		add(lblScoreGoesHere, gbc_lblScoreGoesHere);

		JButton btnRestart = new JButton("Restart");
		btnRestart.setForeground(color1);
		btnRestart.setRolloverEnabled(true);
		btnRestart.setRolloverIcon(hover);
		btnRestart.setSelectedIcon(pressed);
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireStartGame();
			}
		});
		btnRestart.setIcon(pressed);
		btnRestart.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnRestart.setHorizontalTextPosition(JButton.CENTER);
		btnRestart.setVerticalTextPosition(JButton.CENTER);
		btnRestart.setBorder(BorderFactory.createEmptyBorder());
		btnRestart.setContentAreaFilled(false);
		GridBagConstraints gbc_btnRestart = new GridBagConstraints();
		gbc_btnRestart.gridx = 0;
		gbc_btnRestart.gridy = 3;
		add(btnRestart, gbc_btnRestart);

	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setEnemiesKilled(int enemiesKilled) {
		this.enemiesKilled = enemiesKilled;
	}

	public void setCoinsInWeapons(int coinsInWeapons) {
		this.coinsInWeapons = coinsInWeapons;
	}

	public void update() {
		score = (coins + coinsInWeapons + enemiesKilled * 10) * DifficultySettings.getDifficultyMultiplier();
		lblScoreGoesHere.setText(score.toString());
	}

	public void setListener(StartPanelListener listener) {
		this.listener = listener;
	}

	private void fireStartGame() {
		if (listener != null)
			listener.startGame(MainFrame.START_PANEL);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		// g.drawImage(bgImage, 0, 0, null);//set color/ image whatever
		g.setColor(color2);
		g.fillRect(0, 0, 2000, 2000);
	}

	public int getScore() {
		return score;
	}
}
