package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import listeners.StartPanelListener;
import model.Hero;
import model.MyUtils;

public class PlayerSpriteSelectScreen extends JPanel {
	/**
	 * This creates the panl that allows the user to select a character and give it
	 * a name
	 */
	private static final long serialVersionUID = -1913994672060975350L;
	private JTextField txtSteve;
	private ImageIcon hover;
	private ImageIcon icon;
	private Color color1 = new Color(251, 208, 158);
	private Color color2 = new Color(62, 54, 54);
	private StartPanelListener listener;
	PlayerView panel;

	public PlayerSpriteSelectScreen() {
		hover = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		icon = new ImageIcon(getClass().getResource("/button.png"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblChooseYourCharacter = new JLabel("Choose your character");
		lblChooseYourCharacter.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseYourCharacter.setFont(MyUtils.getFont(Font.BOLD, 50));
		lblChooseYourCharacter.setForeground(color1);
		GridBagConstraints gbc_lblChooseYourCharacter = new GridBagConstraints();
		gbc_lblChooseYourCharacter.insets = new Insets(100, 0, 50, 0);
		gbc_lblChooseYourCharacter.gridwidth = 2;
		gbc_lblChooseYourCharacter.gridx = 0;
		gbc_lblChooseYourCharacter.gridy = 0;
		add(lblChooseYourCharacter, gbc_lblChooseYourCharacter);

		JButton btnPrevious = new JButton("");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.previous();
			}
		});
		btnPrevious.setForeground(color1);
		btnPrevious.setIcon(new ImageIcon(getClass().getResource("/previous.png")));
		btnPrevious.setHorizontalTextPosition(JButton.CENTER);
		btnPrevious.setVerticalTextPosition(JButton.CENTER);
		btnPrevious.setBorder(BorderFactory.createEmptyBorder());
		btnPrevious.setContentAreaFilled(false);
		btnPrevious.setVerticalTextPosition(SwingConstants.CENTER);
		btnPrevious.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrevious.setContentAreaFilled(false);
		btnPrevious.setBorder(BorderFactory.createEmptyBorder());
		GridBagConstraints gbc_btnPrevious = new GridBagConstraints();
		gbc_btnPrevious.insets = new Insets(0, 300, 5, 5);
		gbc_btnPrevious.gridx = 0;
		gbc_btnPrevious.gridy = 1;
		add(btnPrevious, gbc_btnPrevious);

		JButton btnNext = new JButton("");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.next();
			}
		});
		btnNext.setForeground(color1);
		btnNext.setIcon(new ImageIcon(getClass().getResource("/next.png")));
		btnNext.setHorizontalTextPosition(JButton.CENTER);
		btnNext.setVerticalTextPosition(JButton.CENTER);
		btnNext.setBorder(BorderFactory.createEmptyBorder());
		btnNext.setContentAreaFilled(false);
		btnNext.setVerticalTextPosition(SwingConstants.CENTER);
		btnNext.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNext.setContentAreaFilled(false);
		btnNext.setBorder(BorderFactory.createEmptyBorder());
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.insets = new Insets(0, 0, 5, 300);
		gbc_btnNext.gridx = 1;
		gbc_btnNext.gridy = 1;
		add(btnNext, gbc_btnNext);

		panel = new PlayerView();
		panel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, color1));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(20, 350, 20, 350);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);

		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(MyUtils.getFont(Font.BOLD, 20));
		lblName.setForeground(color1);
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 10);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 3;
		add(lblName, gbc_lblName);

		txtSteve = new JTextField();
		txtSteve.setText("George");
		txtSteve.setBackground(color2);
		txtSteve.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, color1));
		txtSteve.setFont(MyUtils.getFont(Font.BOLD, 20));
		txtSteve.setForeground(color1);
		txtSteve.setCaretColor(color1);
		GridBagConstraints gbc_txtSteve = new GridBagConstraints();
		gbc_txtSteve.insets = new Insets(20, 10, 20, 250);
		gbc_txtSteve.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSteve.gridx = 1;
		gbc_txtSteve.gridy = 3;
		add(txtSteve, gbc_txtSteve);
		txtSteve.setColumns(10);

		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireStartGame();
				SoundBoard.UpdateMusic("Countryside5.wav", false);
			}
		});
		btnStartGame.setForeground(color1);
		btnStartGame.setIcon(icon);
		btnStartGame.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnStartGame.setRolloverEnabled(true);
		btnStartGame.setRolloverIcon(hover);
		btnStartGame.setHorizontalTextPosition(JButton.CENTER);
		btnStartGame.setVerticalTextPosition(JButton.CENTER);
		btnStartGame.setBorder(BorderFactory.createEmptyBorder());
		btnStartGame.setContentAreaFilled(false);
		btnStartGame.setVerticalTextPosition(SwingConstants.CENTER);
		btnStartGame.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStartGame.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnStartGame.setContentAreaFilled(false);
		btnStartGame.setBorder(BorderFactory.createEmptyBorder());
		GridBagConstraints gbc_btnStartGame = new GridBagConstraints();
		gbc_btnStartGame.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartGame.gridwidth = 2;
		gbc_btnStartGame.gridx = 0;
		gbc_btnStartGame.gridy = 4;
		add(btnStartGame, gbc_btnStartGame);

		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireReturn();
			}
		});
		btnReturn.setForeground(color1);
		btnReturn.setIcon(icon);
		btnReturn.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnReturn.setRolloverEnabled(true);
		btnReturn.setRolloverIcon(hover);
		btnReturn.setHorizontalTextPosition(JButton.CENTER);
		btnReturn.setVerticalTextPosition(JButton.CENTER);
		btnReturn.setBorder(BorderFactory.createEmptyBorder());
		btnReturn.setContentAreaFilled(false);
		btnReturn.setVerticalTextPosition(SwingConstants.CENTER);
		btnReturn.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReturn.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnReturn.setContentAreaFilled(false);
		btnReturn.setBorder(BorderFactory.createEmptyBorder());
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.insets = new Insets(0, 0, 20, 0);
		gbc_btnReturn.gridwidth = 2;
		gbc_btnReturn.gridx = 0;
		gbc_btnReturn.gridy = 5;
		add(btnReturn, gbc_btnReturn);

	}

	public void setListener(StartPanelListener listener) {
		this.listener = listener;
	}

	private void fireStartGame() {
		if (listener != null)
			listener.startGame(MainFrame.HELP_PANEL);
		panel.stopTimer();
	}

	public void StartTimer() {
		panel.startTimer();
		panel.reset();
	}

	private void fireReturn() {
		if (listener != null)
			listener.startGame(MainFrame.START_PANEL);
		panel.stopTimer();
	}

	public Hero getSelectedPlayer() {
		Hero tmp = panel.getSelectedPlayer();
		tmp.setName(txtSteve.getText());
		return tmp;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		// g.drawImage(bgImage, 0, 0, null);//set color/ image whatever
		g.setColor(color2);
		g.fillRect(0, 0, 2000, 2000);
	}
}
