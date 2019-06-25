package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class HighScoreTableModel extends AbstractTableModel {

	/**
	 * Model used for the data in the High Scores table in the highscores panel
	 */
	private static final long serialVersionUID = 6208781589700118612L;
	private List<PlayerScore> db;
	private String[] colNames = { "Rank", "Name", "Finished", "Score" };

	public HighScoreTableModel() {

	}

	public void setData(List<PlayerScore> db) {
		this.db = db;
	}

	@Override
	public int getRowCount() {
		return db.size() + 1;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex == 0) {
			switch (columnIndex) {
			case 0:
				return "Rank";
			case 1:
				return "Name";
			case 2:
				return "Finished";
			case 3:
				return "Score";
			}
		}
		PlayerScore playerScore = db.get(rowIndex - 1);
		switch (columnIndex) {
		case 0:
			return new Integer(rowIndex).toString();
		case 1:
			return playerScore.getName();
		case 2:
			return playerScore.getFinished();
		case 3:
			return new Integer(playerScore.getScore()).toString();
		}
		return null;
	}

	public String getColumnName(int col) {
		return colNames[col];
	}

}
