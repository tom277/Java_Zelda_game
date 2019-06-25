package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import listeners.StartPanelListener;
import model.MyUtils;

public class TitleScreenPane extends JPanel {

	
	/**
	 * This creates the start screen with the game title 
	 */
	private static final long serialVersionUID = 7724194754648582023L;
	private BufferedImage background;
	private Font titleFont;
	private Font littleFont;
	private Color textColor;
	private StartPanelListener listener;
	/**
	 * Create the panel.
	 */
	public TitleScreenPane() {
		try {
			background = ImageIO.read(getClass().getResource("/legend_of_zulda_snip2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		titleFont = MyUtils.getFont(Font.BOLD, 80);
		littleFont =MyUtils.getFont(Font.BOLD, 30);
		textColor = new Color(251, 208, 158);
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				fireStartGame();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
	}
		@Override
		protected void paintComponent(Graphics g) {

			// Draw background
			g.drawImage(background, 0, 0, 1024, 900, null);

			g.setFont(titleFont);
			g.setColor(textColor);
			
			g.drawString("Legend of Papyrus", 150, 100);
			
			g.setFont(littleFont);
			g.drawString("Click anywhere to start", 350, 800);
			
		}

		public void update() {
			repaint();
		}
		public void setListener(StartPanelListener listener) {
			this.listener = listener;
		}

		private void fireStartGame() {
			if (listener != null)
				listener.startGame(MainFrame.START_PANEL);
		}
}
