package model;

import java.io.Serializable;
import java.util.ArrayList;

import gui.Game;

public class GameSave implements Serializable {
	/**
	 * Holds the data needed for the stuff
	 */
	private static final long serialVersionUID = -1779266631515333642L;
	private Game game;
	private Hero hero;
	private ArrayList<Map> maps;

	public GameSave(Game game, Hero hero, ArrayList<Map> maps) {
		super();
		this.game = game;
		this.hero = hero;
		this.maps = maps;
	}

	public Game getGame() {
		return game;
	}

	public Hero getHero() {
		return hero;
	}

	public ArrayList<Map> getMaps() {
		return maps;
	}
}
