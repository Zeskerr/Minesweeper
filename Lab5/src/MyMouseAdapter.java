import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();

	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1: // Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3: // Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame) c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default: // Some other button (2 = Middle mouse button, etc.)
			// Do nothing
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1: // Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				// Had pressed outside
				// Do nothing
				System.out.println("Im outside");
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					// Is releasing outside
					// Do nothing
					System.out.println("-1");
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						// Released the mouse button on a different cell where
						// it was pressed
						// Do nothing
					} else {
						// all left click code goes here
						if (myPanel.notClickable[gridX][gridY]) {
							// grid not clickable
						} else {
							if (myPanel.bombPlacement[gridX][gridY] == false) {
								int count = 0;
								for (int xi = gridX - 1; xi <= gridX + 1; xi++) {
									for (int yi = gridY - 1; yi <= gridY + 1; yi++) {
										if (myPanel.notClickable[xi][yi]) {
											continue;
										} else if (myPanel.bombPlacement[xi][yi]) {
											count++;
										} else if (!myPanel.bombPlacement[xi][yi]) {
											myPanel.colorArray[xi][yi] = Color.WHITE;
											myPanel.notClickable[xi][yi] = true;
										}
									}
								}
								System.out.println(count);
							} else {
								// game over
								System.out.println("Boom");
								myPanel.colorArray[gridX][gridY] = Color.BLACK;
								myPanel.notClickable[gridX][gridY] = true;
								myPanel.gameOver();
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3: // Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame) c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			gridX = myPanel.getGridX(x, y);
			gridY = myPanel.getGridY(x, y);
			// all right click code goes here
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				// Had pressed outside
				// Do nothing
				System.out.println("Im outside");
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					// Is releasing outside
					// Do nothing
					System.out.println("-1");
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						// Released the mouse button on a different cell where
						// it was pressed
						// Do nothing
					} else {
						// all left click code goes here
						if (myPanel.colorArray[gridX][gridY].equals(Color.GRAY)) {
							myPanel.colorArray[gridX][gridY] = Color.RED;
						} else if (myPanel.colorArray[gridX][gridY].equals(Color.RED)) {
							myPanel.colorArray[gridX][gridY] = Color.GRAY;
						}
					}
				}
			}
			myPanel.repaint();
			break;
		default: // Some other button (2 = Middle mouse button, etc.)
			// Do nothing
			break;
		}
	}

	public Color newRandomColor() {
		Color randomColor = null;
		switch (generator.nextInt(5)) {
		case 0:
			randomColor = Color.YELLOW;
			break;
		case 1:
			randomColor = Color.MAGENTA;
			break;
		case 2:
			randomColor = Color.BLACK;
			break;
		case 3:
			randomColor = new Color(0x964B00);
			break;
		case 4:
			randomColor = new Color(0xB57EDC);
			break;
		}
		return randomColor;
	}
}