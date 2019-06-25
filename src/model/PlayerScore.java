package model;

import java.io.Serializable;

public class PlayerScore implements Serializable, Comparable<PlayerScore> {
	/**
	 * Defines a relationship between Player and score for the highScores panel
	 */
	private static final long serialVersionUID = 4897514228028944261L;
	private String name;
	private int score;
	private String finished;

	public PlayerScore(String name, String finished, int score) {
		super();
		this.name = name;
		this.finished = finished;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFinished() {
		return finished;
	}
	public Integer getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(PlayerScore arg0) {
		return this.getScore().compareTo(arg0.getScore());
	}

}
