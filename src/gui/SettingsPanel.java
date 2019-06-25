package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import listeners.StartPanelListener;
import model.MyUtils;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SettingsPanel extends JPanel implements Serializable {

	/**
	 * This creates the settings panel from which you can turn sound and music off
	 * and adjust the difficulty
	 */
	private static final long serialVersionUID = 7730196586321760622L;
	private StartPanelListener listener;
	private Color color1 = new Color(251, 208, 158);
	private Color color2 = new Color(62, 54, 54);
	private ImageIcon pressed;
	private ImageIcon hover;
	private ImageIcon icon;
	private int buttonPressCount = 0;
	private String difficulty = "Medium";
	private JLabel lblNewLabel;
	private String previous;
	private JSlider slider;

	/**
	 * Create the panel.
	 */
	public SettingsPanel() {
		pressed = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		hover = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		icon = new ImageIcon(getClass().getResource("/button.png"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 522, 0, 0, 0, 0, 103, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setForeground(color1);
		lblSettings.setFont(MyUtils.getFont(Font.BOLD, 50));
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.gridwidth = 6;
		gbc_lblSettings.insets = new Insets(200, 0, 50, 0);
		gbc_lblSettings.gridx = 0;
		gbc_lblSettings.gridy = 0;
		add(lblSettings, gbc_lblSettings);

		JButton btnReturn = new JButton("Return");
		btnReturn.setIcon(icon);
		btnReturn.setRolloverEnabled(true);
		btnReturn.setRolloverIcon(hover);
		btnReturn.setFont(MyUtils.getFont(Font.BOLD, 20));
		btnReturn.setHorizontalTextPosition(JButton.CENTER);
		btnReturn.setVerticalTextPosition(JButton.CENTER);
		btnReturn.setBorder(BorderFactory.createEmptyBorder());
		btnReturn.setContentAreaFilled(false);
		btnReturn.setForeground(color1);
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireReturnListener();
			}
		});

		JLabel lblMusic = new JLabel("Music:");
		lblMusic.setForeground(new Color(251, 208, 158));
		lblMusic.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_lblMusic = new GridBagConstraints();
		gbc_lblMusic.insets = new Insets(0, 0, 5, 20);
		gbc_lblMusic.gridx = 0;
		gbc_lblMusic.gridy = 1;
		add(lblMusic, gbc_lblMusic);

		JToggleButton toggleButton = new JToggleButton("ON ");
		toggleButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					toggleButton.setText("ON ");
					if (buttonPressCount == 1) {
						SoundBoard.UnmuteMusic();
					} else {
						buttonPressCount = 1;
					}
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					toggleButton.setText("OFF");
					try {
						SoundBoard.MuteMusic();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		toggleButton.setSelected(true);
		toggleButton.setForeground(new Color(251, 208, 158));
		toggleButton.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_toggleButton = new GridBagConstraints();
		gbc_toggleButton.gridwidth = 5;
		gbc_toggleButton.insets = new Insets(0, 0, 5, 150);
		gbc_toggleButton.gridx = 1;
		gbc_toggleButton.gridy = 1;
		add(toggleButton, gbc_toggleButton);

		JLabel lblSounds = new JLabel("Sounds:");
		lblSounds.setForeground(new Color(251, 208, 158));
		lblSounds.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_lblSounds = new GridBagConstraints();
		gbc_lblSounds.insets = new Insets(0, 0, 5, 20);
		gbc_lblSounds.gridx = 0;
		gbc_lblSounds.gridy = 2;
		add(lblSounds, gbc_lblSounds);

		JToggleButton toggleButton_1 = new JToggleButton("ON ");
		toggleButton_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					toggleButton_1.setText("ON ");
					if (buttonPressCount == 1) {
						SoundBoard.UnmuteSounds();
					} else {
						buttonPressCount = 1;
					}
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					toggleButton_1.setText("OFF");
					try {
						SoundBoard.MuteSounds();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		toggleButton_1.setSelected(true);
		toggleButton_1.setForeground(new Color(251, 208, 158));
		toggleButton_1.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_toggleButton_1 = new GridBagConstraints();
		gbc_toggleButton_1.gridwidth = 5;
		gbc_toggleButton_1.insets = new Insets(0, 0, 5, 150);
		gbc_toggleButton_1.gridx = 1;
		gbc_toggleButton_1.gridy = 2;
		add(toggleButton_1, gbc_toggleButton_1);

		JLabel lblDifficulty = new JLabel("Difficulty:");
		// label_2.insets = new Insets(0, 0, 5, 20);
		lblDifficulty.setForeground(new Color(251, 208, 158));
		lblDifficulty.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_lblDifficulty = new GridBagConstraints();
		gbc_lblDifficulty.insets = new Insets(30, 0, 5, 20);
		gbc_lblDifficulty.gridx = 0;
		gbc_lblDifficulty.gridy = 3;
		add(lblDifficulty, gbc_lblDifficulty);
		Hashtable<Integer, JLabel> labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("Easy"));
		labelTable.put(new Integer(1), new JLabel("Medium"));
		labelTable.put(new Integer(2), new JLabel("Hard"));
		labelTable.put(new Integer(3), new JLabel("Impossible"));

		JLabel lblNote = new JLabel("NOTE:  Difficulty cannot be changed in-game");
		// label_2.insets = new Insets(0, 0, 5, 20);
		lblNote.setForeground(new Color(251, 208, 158));
		lblNote.setFont(MyUtils.getFont(Font.PLAIN, 17));
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.insets = new Insets(0, 300, 0, 0);
		gbc_lblNote.gridx = 0;
		gbc_lblNote.gridy = 4;
		add(lblNote, gbc_lblNote);

		slider = new JSlider(JSlider.HORIZONTAL, 0, 3, 2);
		slider.setFont(MyUtils.getFont(Font.PLAIN, 5));
		slider.setMajorTickSpacing(1);
		slider.setValue(1);
		slider.setPreferredSize(new Dimension(400, 70));
		slider.setPaintTicks(true);
		// slider.setPaintLabels(true);
		slider.setFont(MyUtils.getFont(Font.PLAIN, 10));
		slider.setLabelTable(labelTable);
		slider.setFont(MyUtils.getFont(Font.PLAIN, 20));
		slider.setBackground(color2);
		slider.setForeground(color1);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				JSlider source = (JSlider) arg0.getSource();
				int value = source.getValue();
				setDifficultyLabel(value);
			}
		});
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.gridwidth = 6;
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 5;
		add(slider, gbc_slider);

		lblNewLabel = new JLabel(difficulty);
		setDifficultyLabel(slider.getValue());
		lblNewLabel.setForeground(color1);
		lblNewLabel.setFont(MyUtils.getFont(Font.BOLD, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 6;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 6;
		add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.gridwidth = 6;
		gbc_btnReturn.insets = new Insets(50, 0, 5, 0);
		gbc_btnReturn.gridx = 0;
		gbc_btnReturn.gridy = 7;
		add(btnReturn, gbc_btnReturn);

	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficultyLabel(int value) {
		if (value == 0) {
			difficulty = "Easy";
			lblNewLabel.setText(difficulty);
		} else if (value == 1) {
			difficulty = "Medium";
			lblNewLabel.setText(difficulty);
		} else if (value == 2) {
			difficulty = "Hard";
			lblNewLabel.setText(difficulty);
		} else {
			difficulty = "IMPOSSIBLE";
			lblNewLabel.setText(difficulty);
		}
	}

	public void setSliderEnabled(boolean state) {
		slider.setEnabled(state);

	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public void setListener(StartPanelListener listener) {
		this.listener = listener;
	}

	public void fireReturnListener() {
		if (listener != null) {
			listener.startGame(previous);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		// g.drawImage(bgImage, 0, 0, null);//set color/ image whatever
		g.setColor(color2);
		g.fillRect(0, 0, 2000, 2000);
	}
}
