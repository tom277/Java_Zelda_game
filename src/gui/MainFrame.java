package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import listeners.MakeGameListener;
import listeners.PlayerEventListener;
import listeners.PurchaseEvent;
import listeners.PurchaseListener;
import listeners.SaveGameListener;
import listeners.StartPanelListener;
import listeners.TimerStopListener;
import model.DifficultySettings;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 * This class selects what to display to the user and is the main container for
	 * all
	 */
	private static final long serialVersionUID = 8291789686931270691L;

	public static final String START_PANEL = "start";
	public static final String SPLIT_GAME_PANEL = "splitPanel";
	public static final String GAME_PANEL = "game";
	public static final String SHOP_PANEL = "shop";
	public static final String DEATH_PANEL = "deathPanel";
	public static final String SETTINGS_PANEL = "settings";
	public static final String HIGH_SCORE_PANEL = "highScoresPanel";
	public static final String PLAYER_SELECT_PANEL = "playerSelectScreen";
	public static final String IN_GAME_MENU_PANEL = "inGameMenu";
	public static final String WELCOME_PANEL = "welcomeScreen";
	public static final String HELP_PANEL = "helpScreen";
	public static final String TITLE_PANEL = "titlePanel";
	public static final String WIN_PANEL = "winPanel";

	private Game game;
	private TitleScreenPane titleScreenPane;
	private StartPane startPane;
	private SettingsPanel settingsPanel;
	private PlayerSpriteSelectScreen playerSelectScreen;
	private InGameMenu inGameMenu;
	private CardLayout cards;
	private String currentCard;
	private String gamePanel;
	private Shop shop;
	private JSplitPane splitPane;
	private JPanel bottomPanel;
	private JPanel topPanel;
	private DeathPane deathPane;
	private HighScoresPanel highScoresPanel;
	private WinScreen winPanel;
	private Container container = getContentPane();
	private PlayerEventListener playerEventListener;
	InfoBar infoBar;
	private TimerStopListener timerListener;
	private listeners.KeyListener keyListener;
	private MakeGameListener makeGameListener;

	public SoundBoard soundBoard;
	private WelcomeScreen welcomeScreen;
	private CommandHelpScreen commandHelpScreen;
	private boolean reset;
	private SaveGameListener saveGameListener;

	public MainFrame() {
		super("Legend of Papyrus");
		gamePanel = GAME_PANEL;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		soundBoard = new SoundBoard();

		createShop();

		cards = new CardLayout();

		splitPane = new JSplitPane();

		createAssortedPanels();
		createTitleScreenPanel();
		createStartPanel();// call make start panel

		setSize(1024, 900);
		setResizable(false);

		setLayout(new BorderLayout());
		container.setLayout(cards);
		container.add(titleScreenPane, TITLE_PANEL);
		currentCard = TITLE_PANEL;
		container.add(startPane, START_PANEL);
		currentCard = "start";
		container.add(splitPane, SPLIT_GAME_PANEL);
		gamePanel = GAME_PANEL;
		container.add(deathPane, DEATH_PANEL);
		container.add(settingsPanel, SETTINGS_PANEL);
		container.add(highScoresPanel, HIGH_SCORE_PANEL);
		container.add(playerSelectScreen, PLAYER_SELECT_PANEL);
		container.add(inGameMenu, IN_GAME_MENU_PANEL);
		container.add(welcomeScreen, WELCOME_PANEL);
		container.add(commandHelpScreen, HELP_PANEL);
		container.add(winPanel, WIN_PANEL);

		this.setFocusable(true);
		this.requestFocusInWindow();
		addKeyListener();
		setVisible(true);
	}

	public void setGame(Game game) {
		this.game = game;
		this.game.setSize(1024, 768);
		createSplitPane();
	}

	public void createShop() {
		shop = new Shop();
		shop.setPurchaseListener(new PurchaseListener() {
			public void purchase(PurchaseEvent event) {
				game.getHero().decrementCoins(event.getCost());
				game.getHero().setWeapon(event.getWeapon(), true);
				changeGamePanel();
				// System.out.println(game.getHero().getCoins());
			}
		});
	}

	public void createAssortedPanels() {
		winPanel = new WinScreen();
		winPanel.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);
				currentCard = nextPanel;
				timerListener.stopTimer();
				infoBar.stopTimer();
			}
		});
		commandHelpScreen = new CommandHelpScreen();
		commandHelpScreen.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);
				currentCard = nextPanel;// changed
				timerListener.stopTimer();
			}
		});
		welcomeScreen = new WelcomeScreen();
		welcomeScreen.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);
				currentCard = nextPanel;// changed
				if (nextPanel.equalsIgnoreCase(MainFrame.SPLIT_GAME_PANEL)) {
					gamePanel();
				} else {
					timerListener.stopTimer();
					infoBar.stopTimer();
				}
			}
		});
		inGameMenu = new InGameMenu();
		inGameMenu.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);
				if (nextPanel.equalsIgnoreCase(MainFrame.SETTINGS_PANEL)) {
					settingsPanel.setPrevious(IN_GAME_MENU_PANEL);
					settingsPanel.setSliderEnabled(false);
				}
				currentCard = nextPanel;
				timerListener.startTimer();
				infoBar.startTimer();
				game.resetHero();
				keyListener.setUpKeyPressed(false);
				keyListener.setDownKeyPressed(false);
				keyListener.setLeftKeyPressed(false);
				keyListener.setRightKeyPressed(false);
			}
		});
		inGameMenu.setSaveGameListener(new SaveGameListener() {
			public void saveTheGame() {
				if (saveGameListener != null) {
					saveGameListener.saveTheGame();
				}
			}
		});
		playerSelectScreen = new PlayerSpriteSelectScreen();
		playerSelectScreen.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);
				if (nextPanel.equalsIgnoreCase(HELP_PANEL)) {
					if (makeGameListener != null) {
						makeGameListener.createGame();
						playerEventListener.playerUpdate(playerSelectScreen.getSelectedPlayer());
					}
					reset = true;
				}
				currentCard = nextPanel;
			}
		});
		deathPane = new DeathPane();
		deathPane.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);// changed
				currentCard = nextPanel;// changed
				timerListener.stopTimer();
				infoBar.stopTimer();
			}
		});
		highScoresPanel = new HighScoresPanel();
		highScoresPanel.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);// changed
				currentCard = nextPanel;// changed
				timerListener.stopTimer();
				// infoBar.stopTimer();
			}
		});

		settingsPanel = new SettingsPanel();
		settingsPanel.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);
				currentCard = nextPanel;
				timerListener.stopTimer();
			}
		});
	}

	public void createStartPanel() {
		startPane = new StartPane();
		startPane.setListener(new StartPanelListener() {
			public void startGame(String nextPane) {
				cards.show(MainFrame.this.getContentPane(), nextPane);
				currentCard = nextPane;
				if (nextPane.equalsIgnoreCase(MainFrame.PLAYER_SELECT_PANEL)) {
					playerSelectScreen.StartTimer();
				} else if (nextPane.equalsIgnoreCase(MainFrame.SETTINGS_PANEL)) {
					settingsPanel.setPrevious(START_PANEL);
					settingsPanel.setSliderEnabled(true);
				}
			}
		});
	}

	public void createTitleScreenPanel() {
		titleScreenPane = new TitleScreenPane();
		titleScreenPane.setListener(new StartPanelListener() {
			public void startGame(String nextPanel) {
				cards.show(MainFrame.this.getContentPane(), nextPanel);
				currentCard = nextPanel;
			}
		});
	}

	public InfoBar getInfoBar() {
		return infoBar;
	}

	public DeathPane getDeathPane() {
		return deathPane;
	}

	public WinScreen getWinPanel() {
		return winPanel;
	}

	private void gamePanel() {
		if (reset) {
			String difficulty = settingsPanel.getDifficulty();
			if (difficulty.equalsIgnoreCase("easy")) {
				DifficultySettings.setDifficulty(DifficultySettings.Difficulty.EASY);
			} else if (difficulty.equalsIgnoreCase("medium")) {
				DifficultySettings.setDifficulty(DifficultySettings.Difficulty.MEDIUM);
			} else if (difficulty.equalsIgnoreCase("hard")) {
				DifficultySettings.setDifficulty(DifficultySettings.Difficulty.HARD);
			} else {
				DifficultySettings.setDifficulty(DifficultySettings.Difficulty.IMPOSSIBLE);
			}
			playerSelectScreen.getSelectedPlayer();
			if (playerEventListener != null) {
				playerEventListener.playerUpdate(playerSelectScreen.getSelectedPlayer());
			}
			game.getHero().setTimeBetweenDamage();
		}
		timerListener.startTimer();
		cards.show(MainFrame.this.getContentPane(), MainFrame.SPLIT_GAME_PANEL);
		infoBar.startTimer();
	}

	public void createSplitPane() {
		container.remove(splitPane);

		container.revalidate();
		container.repaint();
		revalidate();
		repaint();

		splitPane = new JSplitPane();
		topPanel = new JPanel(); // our top component
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setLayout(new BorderLayout());
		infoBar = new InfoBar();
		infoBar.update();
		topPanel.add(infoBar, BorderLayout.CENTER);
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(game, BorderLayout.CENTER);
		splitPane.setDividerSize(0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(132);
		splitPane.setTopComponent(topPanel);
		splitPane.setBottomComponent(bottomPanel);// bottomPanel

		bottomPanel.revalidate();
		bottomPanel.repaint();
		splitPane.revalidate();
		splitPane.repaint();

		container.add(splitPane, SPLIT_GAME_PANEL);
		container.revalidate();
		container.repaint();
		revalidate();
		repaint();
	}

	public void setPlayerEventListener(PlayerEventListener playerEventListener) {
		this.playerEventListener = playerEventListener;
	}

	private void addKeyListener() {
		this.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (currentCard.contains("splitPane")) {
					int key = e.getKeyCode();
					switch (key) {
					case KeyEvent.VK_W:
						keyListener.setUpKeyPressed(true);
						if (gamePanel.equalsIgnoreCase(SHOP_PANEL)) {
							shop.decreaseSelectedItem();
						}
						break;
					case KeyEvent.VK_S:
						keyListener.setDownKeyPressed(true);
						if (gamePanel.equalsIgnoreCase(SHOP_PANEL)) {
							shop.increaseSelectedItem();
						}
						break;
					case KeyEvent.VK_A:
						keyListener.setLeftKeyPressed(true);
						if (gamePanel.equalsIgnoreCase(SHOP_PANEL)) {
							shop.decreaseSelectedConfirmItem();
						}
						break;
					case KeyEvent.VK_D:
						keyListener.setRightKeyPressed(true);
						if (gamePanel.equalsIgnoreCase(SHOP_PANEL)) {
							shop.increaseSelectedConfirmItem();
						}
						break;
					case KeyEvent.VK_E:
						game.KeyYPressed();
						infoBar.startTimer();
						break;
					case KeyEvent.VK_P:
						timerListener.toggleTimer();
						break;
					case KeyEvent.VK_SPACE:
						game.KeyXPressed();
						if (gamePanel.equalsIgnoreCase(SHOP_PANEL)) {
							shop.purchase(game.getHero().getCoins());
						}
						break;
					case KeyEvent.VK_ESCAPE:
						escKeyPressed();
						break;
					case KeyEvent.VK_M:
						menuKeyPressed();
						break;
					case KeyEvent.VK_Q:
						questKeyPressed();
						break;
					case KeyEvent.VK_PAGE_DOWN:
						game.getHero().EnterEndGame();
						break;
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				if (currentCard.contains("splitPane")) {
					int key = e.getKeyCode();
					switch (key) {
					case KeyEvent.VK_W:
						keyListener.setUpKeyPressed(false);
						break;
					case KeyEvent.VK_S:
						keyListener.setDownKeyPressed(false);
						break;
					case KeyEvent.VK_A:
						keyListener.setLeftKeyPressed(false);
						break;
					case KeyEvent.VK_D:
						keyListener.setRightKeyPressed(false);
						break;
					case KeyEvent.VK_SPACE:
						game.KeyXPressed();
					}
				}
			}
		});
	}

	public CardLayout getCards() {
		return cards;
	}

	public void escKeyPressed() {
		int action = JOptionPane.showConfirmDialog(MainFrame.this, "This action will close the game without saving.",
				"are you sure you want to complete this action?", JOptionPane.YES_NO_OPTION);
		if (action == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public void menuKeyPressed() {
		cards.show(MainFrame.this.getContentPane(), IN_GAME_MENU_PANEL);
		currentCard = IN_GAME_MENU_PANEL;
		timerListener.stopTimer();
		infoBar.stopTimer();
		game.resetHero();
	}

	public void questKeyPressed() {
		reset = false;
		cards.show(MainFrame.this.getContentPane(), MainFrame.WELCOME_PANEL);
		currentCard = WELCOME_PANEL;
		timerListener.stopTimer();
		infoBar.stopTimer();
		game.resetHero();
	}

	public void changeGamePanel() {
		if (gamePanel.equalsIgnoreCase(GAME_PANEL)) {
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT); // split the window
			splitPane.setDividerLocation(150);
			splitPane.setTopComponent(topPanel);
			JPanel shopWrapper = new JPanel();

			shopWrapper.setLayout(new BorderLayout());
			shopWrapper.add(shop, BorderLayout.CENTER);
			splitPane.setBottomComponent(shop);
			gamePanel = SHOP_PANEL;
			timerListener.stopTimer();
			infoBar.stopTimer();
		} else if (gamePanel.equalsIgnoreCase(SHOP_PANEL)) {
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT); // split the window
			splitPane.setDividerLocation(150);
			splitPane.setTopComponent(topPanel);
			splitPane.setBottomComponent(bottomPanel);
			gamePanel = GAME_PANEL;
			timerListener.startTimer();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void addSecondsTimerListener(ActionEvent e) {
	}

	public void changeCard(String name) {
		cards.show(MainFrame.this.getContentPane(), name);// changed
		currentCard = name;
	}

	public void setTimerListener(TimerStopListener timerListener) {
		this.timerListener = timerListener;
	}

	public void setKeyListener(listeners.KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setSaveGameListener(SaveGameListener listener) {
		this.saveGameListener = listener;
	}

	public void setMakeGameListener(MakeGameListener listener) {
		this.makeGameListener = listener;
	}

	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}

	public HighScoresPanel getHighScoresPanel() {
		return highScoresPanel;
	}
}