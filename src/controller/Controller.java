package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import gui.Game;
import gui.MainFrame;
import gui.SoundBoard;
import listeners.KeyListener;
import listeners.MakeGameListener;
import listeners.PlayerListener;
import listeners.PlayerEvent;
import listeners.PlayerEventListener;
import listeners.ProjectileListener;
import listeners.SaveGameListener;
import listeners.SpecialInteractionEvent;
import listeners.SpecialInteractionListener;
import listeners.TimerStopListener;
import model.DifficultySettings;
import model.GameSave;
import model.Hero;
import model.Map;
import model.Map1;
import model.Map2;
import model.Map3;
import model.Map4;
import model.PlayerScore;

public class Controller implements ActionListener {

	/*
	 * This class provides the main controller functionality for the program
	 */
	private Game game2;
	private MainFrame mainFrame;
	private Timer timer;
	private Hero hero;
	private ArrayList<Map> maps;
	private HeroMovement movement;
	private Map currentMap;

	public Controller() {
		DifficultySettings.setDifficulty(DifficultySettings.Difficulty.MEDIUM);
		timer = new Timer(17, this);// 60 FPS

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new MainFrame();
				mainFrame.setTimerListener(new TimerStopListener() {
					public void stopTimer() {
						timer.stop();
					}

					public void startTimer() {
						timer.start();
					}

					public void toggleTimer() {
						if (timer.isRunning()) {
							timer.stop();
							mainFrame.getInfoBar().stopTimer();
							// bring up a stop screen
						} else if (!timer.isRunning()) {
							timer.start();
							mainFrame.getInfoBar().startTimer();
							// remove stop screen
						}
					}
				});
				mainFrame.setKeyListener(new KeyListener() {
					public void setUpKeyPressed(boolean upKeyPressed) {
						movement.setUpKeyPressed(upKeyPressed);
					}

					public void setDownKeyPressed(boolean downKeyPressed) {
						movement.setDownKeyPressed(downKeyPressed);
					}

					public void setRightKeyPressed(boolean rightKeyPressed) {
						movement.setRightKeyPressed(rightKeyPressed);
					}

					public void setLeftKeyPressed(boolean leftKeyPressed) {
						movement.setLeftKeyPressed(leftKeyPressed);
					}

					public void setPgdownKeyPressed(boolean pgdownKeyPressed) {

					}
				});
				mainFrame.setPlayerEventListener(new PlayerEventListener() {
					public void playerUpdate(PlayerEvent event) {

					}

					public void playerUpdate(Hero herotmp) {
						hero = herotmp;
						hero.setDeathEventListener(new PlayerListener() {
							public void playerDeath(PlayerEvent event) {
								if (hero != null) {
									playerDeathEvent(event);
								} else {
									// System.out.println("error");
								}
							}

							public void playerWin(PlayerEvent event) {
								if (hero != null) {
									playerWinEvent(event);
								} else {
									// System.out.println("error");
								}
							}
						});
						hero.setUpdateEventListener(new PlayerEventListener() {
							public void playerUpdate(PlayerEvent event) {
								playerUpdateEvent(event);
							}

							public void playerUpdate(Hero hero) {

							}
						});
						hero.setMovingDown(false);
						hero.setMovingLeft(false);
						hero.setMovingRight(false);
						hero.setMovingUp(false);
						hero.setHeroMovement(movement);
						game2.setHero(hero);
						for (Map tmpMap : maps) {
							tmpMap.updateHero(hero);
						}
					}
				});
				mainFrame.setSaveGameListener(new SaveGameListener() {
					public void saveTheGame() {
						saveGame();
					}
				});
				mainFrame.setMakeGameListener(new MakeGameListener() {
					public void createGame() {
						game2 = createNewGame();
						mainFrame.setGame(game2);
					}
				});
			}
		});

	}

	public Game createNewGame() {
		maps = new ArrayList<Map>();
		Map map1 = new Map1(1024, 737, hero);// hard coded size of currentMap previously it was the size of the window
		// (getWidth(),
		map1.setBaddyDeathEventListener(new ProjectileListener() {
			public void DeleteProjectile() {
				hero.addEnemiesKilled();
			}
		});

		maps.add(map1);
		currentMap = map1;
		Game game = new Game();
		game.setCurrentMap(currentMap);
		movement = new HeroMovement(currentMap, new listeners.HeroMovementListener() {
			public Map changeMap(int num) {
				currentMap = maps.get(num);
				game.setCurrentMap(currentMap);
				SoundBoard.UpdateMusic(findMusic(num), false); // update music according to level
				return maps.get(num);
			}

			public String findMusic(int mapNum) {
				if (mapNum == 0) {
					return "Countryside5.wav";
				} else if (mapNum < 3) { // i.e. main maps
					return "Rain Forest2.wav";
				} else if (mapNum == 3) {
					return "Spider's Lair.wav";
				} else {
					return "02-title-screen.wav"; // placeholder for unhandled maps
				}
			}

			public void interact(String string) {
				game.interactions(string);
			}

			public void setCloseToShopFalse() {
				// closeToShop = false;
				game.setCloseToShop(false);
			}

			public void setCloseToGundrenFalse() {
				game.setCloseToGundren(false);
			}

			public void hideTextBox() {
				// textBox.hide();
				game.getTextBox().hide();
			}
		});
		Map map2 = new Map2(1024, 737, hero);
		map2.setBaddyDeathEventListener(new ProjectileListener() {
			public void DeleteProjectile() {
				hero.addEnemiesKilled();
			}
		});
		maps.add(map2);
		Map map3 = new Map3(1024, 737, hero);
		map3.setBaddyDeathEventListener(new ProjectileListener() {
			public void DeleteProjectile() {
				hero.addEnemiesKilled();
			}
		});
		maps.add(map3);

		Map map4 = new Map4(1024, 737, hero);
		map4.setBaddyDeathEventListener(new ProjectileListener() {
			public void DeleteProjectile() {
				hero.addEnemiesKilled();
			}
		});
		maps.add(map4);

		// finish making the map and the Hero
		game.addSpecialInteractionListener(new SpecialInteractionListener() {
			public void eventOccured(SpecialInteractionEvent SIE) {
				if (SIE.getInteractionName().equals("shop")) {
					// System.out.println("shop opened");// set a new pane and pause game
					mainFrame.changeGamePanel();
					// System.out.println(gamePanel);
				}
			}
		});
		return game;
	}

	public void playerDeathEvent(PlayerEvent event) {
		// deathPanel = new DeathPanel(event.getCoins(), event.getEnemiesKilled(),
		// event.getWeponsOwned());
		mainFrame.getDeathPane().setCoins(event.getCoins());
		mainFrame.getDeathPane().setEnemiesKilled(event.getEnemiesKilled());
		mainFrame.getDeathPane().setCoinsInWeapons(event.getWeponsOwned());
		// System.out.println("death");
		mainFrame.getDeathPane().update();
		mainFrame.getHighScoresPanel()
				.addPlayerScore(new PlayerScore(hero.getName(), "NO", mainFrame.getDeathPane().getScore()));
		mainFrame.changeCard(MainFrame.DEATH_PANEL);
		timer.stop();
		hero = null;
		game2 = null;
	}

	public void playerWinEvent(PlayerEvent event) {
		// deathPanel = new DeathPanel(event.getCoins(), event.getEnemiesKilled(),
		// event.getWeponsOwned());
		mainFrame.getWinPanel().setCoins(event.getCoins());
		mainFrame.getWinPanel().setEnemiesKilled(event.getEnemiesKilled());
		mainFrame.getWinPanel().setCoinsInWeapons(event.getWeponsOwned());
		mainFrame.getWinPanel().update();
		// System.out.println("Win");
		mainFrame.getHighScoresPanel()
				.addPlayerScore(new PlayerScore(hero.getName(), "YES", mainFrame.getWinPanel().getScore()));
		mainFrame.changeCard(MainFrame.WIN_PANEL);
		timer.stop();
		hero = null;
		game2 = null;
	}

	public void playerUpdateEvent(PlayerEvent event) {
		mainFrame.getInfoBar().UpdateHealth(event.getHealth());
		mainFrame.getInfoBar().UpdateCoins(event.getCoins());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game2.update();
		if (game2 != null) {
			game2.repaint();
		}
	}

	public void saveGame() {
		GameSave gameSave = new GameSave(game2, hero, maps);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream("gameSaves/save1.data", false);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(gameSave);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<PlayerScore> loadGame(String saveName) {
		ArrayList<PlayerScore> tmp = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream("gameSaves/" + saveName + ".data");
			ObjectInputStream ois = new ObjectInputStream(fis);

			tmp = (ArrayList) ois.readObject();

			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			// System.out.println("Class not found");
			c.printStackTrace();
			return null;
		}
		return tmp;
	}
}