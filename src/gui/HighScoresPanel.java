package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import javax.swing.JTable;

import listeners.StartPanelListener;
import model.HighScoreTableModel;
import model.MyUtils;
import model.PlayerScore;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;

public class HighScoresPanel extends JPanel {
	/**
	 * This panel displays, saves and loads the highscores
	 */
	private static final long serialVersionUID = -9129660655076573533L;
	private JTable table;
	private Color color1 = new Color(251, 208, 158);
	private Color color2 = new Color(62, 54, 54);
	private ImageIcon hover;
	private ImageIcon icon;
	private StartPanelListener listener;
	private HighScoreTableModel tableModel;
	private ArrayList<PlayerScore> db;
	private FileOutputStream fileOutputStream;
	private ObjectOutputStream objectOutputStream;

	/**
	 * Create the panel.
	 */
	public HighScoresPanel() {
		tableModel = new HighScoreTableModel();
		db = getData();
		if (db == null) {
			db = new ArrayList<PlayerScore>();
		}
		Collections.sort(db);
		Collections.reverse(db);
		tableModel.setData(db);
		hover = new ImageIcon(getClass().getResource("/buttonSelected.png"));
		icon = new ImageIcon(getClass().getResource("/button.png"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblHighscores = new JLabel("Highscores");
		lblHighscores.setForeground(color1);
		lblHighscores.setFont(MyUtils.getFont(Font.BOLD, 50));
		GridBagConstraints gbc_lblHighscores = new GridBagConstraints();
		gbc_lblHighscores.insets = new Insets(200, 0, 50, 0);
		gbc_lblHighscores.gridx = 0;
		gbc_lblHighscores.gridy = 0;
		add(lblHighscores, gbc_lblHighscores);
		table = new JTable(tableModel);
		table.setFont(MyUtils.getFont(Font.BOLD, 20));
		table.setBackground(color2);
		table.setForeground(color1);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(30);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);
		table.setShowGrid(false);
		table.setRowHeight(0, 70);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 200, 5, 20);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		add(table, gbc_table);

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
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.insets = new Insets(50, 0, 5, 0);
		gbc_btnReturn.gridx = 0;
		gbc_btnReturn.gridy = 2;
		add(btnReturn, gbc_btnReturn);

	}

	public void setListener(StartPanelListener listener) {
		this.listener = listener;
	}

	public void fireReturnListener() {
		if (listener != null) {
			listener.startGame(MainFrame.START_PANEL);
		}
	}

	public void addPlayerScore(PlayerScore ps) {
		db.add(ps);
		Collections.sort(db);
		Collections.reverse(db);
		tableModel.setData(db);
		table.setModel(tableModel);
		// table.updateUI();
		// table.repaint();
		tableModel.fireTableDataChanged();
		serialize();
	}

	public void serialize() {// save to file

		try {
			fileOutputStream = new FileOutputStream("highScores.data", false);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(db);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<PlayerScore> getData() {// get from file
		ArrayList<PlayerScore> tmp = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream("highScores.data");
			ObjectInputStream ois = new ObjectInputStream(fis);

			tmp = (ArrayList) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			// ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			// c.printStackTrace();
			return null;
		}
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
