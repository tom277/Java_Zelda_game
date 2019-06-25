package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;

import listeners.PurchaseEvent;
import listeners.PurchaseListener;
import model.MyUtils;
import model.Weapon;

public class Shop extends JComponent {

	/**
	 * This creates the shop interface
	 */
	private static final long serialVersionUID = 8621013881178686712L;
	private BufferedImage buffer;
	private ArrayList<Weapon> weapons;
	private ArrayList<Weapon> weaponsOwned;

	private int selectedItem;
	private boolean confirm;
	private int selectedConfirmItem;

	private PurchaseListener listener;

	public Shop() {
		weapons = new ArrayList<Weapon>();
		weaponsOwned = new ArrayList<Weapon>();
		Weapon weapon1 = new Weapon(Weapon.RING_1);
		weaponsOwned.add(weapon1);
		weapons.add(weapon1);
		weapons.add(new Weapon(Weapon.RING_2));
		weapons.add(new Weapon(Weapon.RING_3));
		weapons.add(new Weapon(Weapon.RING_4));
		selectedItem = 0;
		Cursor hiddenCursor = getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "");
		setCursor(hiddenCursor);
	}

	public void increaseSelectedConfirmItem() {
		if (selectedConfirmItem < 1) {
			selectedConfirmItem++;
		} else {
			selectedConfirmItem = 0;
		}
		update();
	}

	public void increaseSelectedItem() {
		if (selectedItem < weapons.size() - 1) {
			selectedItem++;
		} else {
			selectedItem = 0;
		}
		update();
	}

	public void decreaseSelectedConfirmItem() {
		if (selectedConfirmItem > 0) {
			selectedConfirmItem--;
		} else {
			selectedConfirmItem = 0;
		}
		update();
	}

	public void decreaseSelectedItem() {
		if (selectedItem > 0) {
			selectedItem = selectedItem - 1;
		} else {
			selectedItem = weapons.size() - 1;
		}
		update();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (buffer == null) {
			buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		}

		Graphics2D g2 = (Graphics2D) buffer.getGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(new Color(62, 54, 54));
		g2.fillRect(0, 0, 2000, 2000);

		g2.setColor(new Color(251, 208, 158));
		Font font = MyUtils.getFont(Font.BOLD, 25);
		g2.setFont(font);
		g2.drawString("To leave the shop press 'E', to select press space", 200, 60);

		int startY = 100;
		Rectangle2D.Double foo = new Rectangle2D.Double(200, startY, 600, 100);

		// g2.setColor(new Color(251, 208, 158));
		for (int i = 0; i < weapons.size(); i++) {
			// System.out.println(selectedItem);
			if (selectedItem == i) {
				// System.out.println("yes");
				g2.setColor(new Color(62, 208, 54));
			} else {
				g2.setColor(new Color(251, 208, 158));
			}

			foo.y = startY + 130 * i;

			g2.fill(foo);
			BufferedImage tmp = weapons.get(i).getImageCurrent();
			Image tmp2 = tmp.getScaledInstance(tmp.getHeight() * 3, tmp.getHeight() * 3, BufferedImage.SCALE_DEFAULT);

			g2.drawImage(tmp2, 700, (int) (foo.y + 20), this);
			g2.setColor(new Color(62, 54, 54));
			font = MyUtils.getFont(Font.BOLD, 30);
			g2.setFont(font);
			g2.drawString(weapons.get(i).getWeaponName(), 220, (int) (foo.y + 50));
			font = MyUtils.getFont(Font.BOLD, 20);
			g2.setFont(font);
			g2.drawString("$" + weapons.get(i).getPrice(), 230, (int) (foo.y + 90));
		}

		g2.setColor(new Color(251, 208, 158));
		font = MyUtils.getFont(Font.BOLD, 25);
		g2.setFont(font);
		g2.drawString(weapons.get(this.selectedItem).getDescription(), 200, 660);

		if (confirm) {
			g2.setColor(new Color(62, 54, 54));
			g2.fillRect(150 - 2, 200 - 2, 700 + 4, 300 + 4);
			g2.setColor(new Color(251, 208, 158));
			g2.fillRect(150, 200, 700, 300);
			g2.setColor(new Color(62, 54, 54));
			font = MyUtils.getFont(Font.BOLD, 40);
			g2.setFont(font);
			g2.drawString("Are you sure", 370, 250);

			if (selectedConfirmItem == 0) {
				// System.out.println("yes");
				g2.setColor(new Color(62, 208, 54));
			} else {
				g2.setColor(new Color(62, 54, 54));
			}
			g2.fillRect(200, 350, 120, 100);
			if (selectedConfirmItem == 1) {
				// System.out.println("yes");
				g2.setColor(new Color(62, 208, 54));
			} else {
				g2.setColor(new Color(62, 54, 54));
			}
			g2.fillRect(550, 350, 120, 100);

			font = MyUtils.getFont(Font.BOLD, 30);
			g2.setFont(font);
			g2.setColor(new Color(251, 208, 158));
			g2.drawString("YES", 200 + 20, 350 + 50);
			g2.drawString("NO", 550 + 20, 350 + 50);
		}

		g.drawImage(buffer, 0, 0, null);
	}

	public void purchase(int coins) {
		if (confirm) {
			if (selectedConfirmItem == 0) {
				firePurchaseEvent(weapons.get(selectedItem).getPrice(), weapons.get(selectedItem));
			}
			selectedConfirmItem = 0;
			confirm = false;
		} else {
			if (weapons.get(selectedItem).getPrice() <= coins) {
				confirm = true;
			} else {
			}
		}
		update();
	}

	public void firePurchaseEvent(int price, Weapon weapon) {
		if (listener != null) {
			this.listener.purchase(new PurchaseEvent(price, weapon));
		}
	}

	public void setPurchaseListener(PurchaseListener listener) {
		this.listener = listener;
	}

	public void update() {
		repaint(); // do not call paint component directly
	}
}
