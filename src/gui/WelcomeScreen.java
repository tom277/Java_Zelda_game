package gui;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import listeners.StartPanelListener;
import model.MyUtils;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class WelcomeScreen extends JPanel {

	/**
	 * This creates the screen which has the quest/storyline
	 */
	private static final long serialVersionUID = -8824293977897628397L;
	private StartPanelListener listener;
	private ImageIcon hover;
	private ImageIcon icon;
	private Color color1 = new Color(251, 208, 158);
	private Color color2 = new Color(62, 54, 54);

	/**
	 * Create the panel.
	 */
	public WelcomeScreen() {
		hover = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		icon = new ImageIcon(getClass().getResource("/button.png"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{26, 969, 0};
		gridBagLayout.rowHeights = new int[]{145, 16, 78, 16, 16, 16, 16, 16, 89, 25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblWelcome = new JLabel("Welcome Traveller");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(MyUtils.getFont(Font.BOLD, 50));
		lblWelcome.setForeground(color1);
		GridBagConstraints gbc_lblWelcome = new GridBagConstraints();
		gbc_lblWelcome.fill = GridBagConstraints.BOTH;
		gbc_lblWelcome.insets = new Insets(0, 0, 5, 0);
		gbc_lblWelcome.gridx = 1;
		gbc_lblWelcome.gridy = 1;
		add(lblWelcome, gbc_lblWelcome);
		
		JLabel lblQuestLabel = new JLabel("A magical orb safeguarded by the old man Gundren ");
		lblQuestLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestLabel.setFont(MyUtils.getFont(Font.BOLD, 30));
		lblQuestLabel.setForeground(color1);
		GridBagConstraints gbc_lblQuestLabel = new GridBagConstraints();
		gbc_lblQuestLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblQuestLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQuestLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuestLabel.gridx = 1;
		gbc_lblQuestLabel.gridy = 3;
		add(lblQuestLabel, gbc_lblQuestLabel);
		
		JLabel lblNewLabel = new JLabel("has been stolen by the the evil wizard Ezufaris,");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(MyUtils.getFont(Font.BOLD, 30));
		lblNewLabel.setForeground(color1);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblQuest = new JLabel("which he is now using to summon evil creatures from other realms.");
		lblQuest.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuest.setFont(MyUtils.getFont(Font.BOLD, 30));
		lblQuest.setForeground(color1);
		GridBagConstraints gbc_lblQuest = new GridBagConstraints();
		gbc_lblQuest.anchor = GridBagConstraints.NORTH;
		gbc_lblQuest.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQuest.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuest.gridx = 1;
		gbc_lblQuest.gridy = 5;
		add(lblQuest, gbc_lblQuest);
		
		JLabel lblNewLabel_1 = new JLabel("It is time for a hero to rise and defeat him and his minions!");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(MyUtils.getFont(Font.BOLD, 30));
		lblNewLabel_1.setForeground(color1);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 6;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblQuest_1 = new JLabel("You must must be that hero!");
		lblQuest_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuest_1.setFont(MyUtils.getFont(Font.BOLD, 30));
		lblQuest_1.setForeground(color1);
		GridBagConstraints gbc_lblQuest_1 = new GridBagConstraints();
		gbc_lblQuest_1.anchor = GridBagConstraints.NORTH;
		gbc_lblQuest_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQuest_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblQuest_1.gridx = 1;
		gbc_lblQuest_1.gridy = 7;
		add(lblQuest_1, gbc_lblQuest_1);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireStartGame();
			}
		});
		btnOk.setForeground(color1);
		btnOk.setIcon(icon);
		btnOk.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnOk.setRolloverEnabled(true);
		btnOk.setRolloverIcon(hover);
		btnOk.setHorizontalTextPosition(JButton.CENTER);
		btnOk.setVerticalTextPosition(JButton.CENTER);
		btnOk.setBorder(BorderFactory.createEmptyBorder());
		btnOk.setContentAreaFilled(false);
		btnOk.setVerticalTextPosition(SwingConstants.CENTER);
		btnOk.setHorizontalTextPosition(SwingConstants.CENTER);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 9;
		add(btnOk, gbc_btnOk);

	}
	
	public void setListener(StartPanelListener listener) {
		this.listener = listener;
	}
	private void fireStartGame() {
		if (listener != null)
			listener.startGame(MainFrame.SPLIT_GAME_PANEL);
	}
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        //g.drawImage(bgImage, 0, 0, null);//set color/ image whatever
	    g.setColor(color2);
	    g.fillRect(0, 0, 2000, 2000);
	}
}
