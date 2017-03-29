import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
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
		switch (e.getButton()) {
		case 1: // Left mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				// Had pressed outside
				// Do nothing
			} else if (myPanel.PLAYER_LOST) {
				// do nothing game is over
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
							if (myPanel.colorArray[gridX][gridY].equals(Color.RED)) { // grid
																						// has
																						// a
																						// flag
								// do nothing
							} else if (myPanel.bombPlacement[gridX][gridY] == false) { // clicked
																						// grid
																						// with
																						// no
																						// bomb
																						// in
																						// it
								System.out.println(gridX + " " + gridY);
								checkForBombsAroundGrid(gridX, gridY, myPanel);
							} else { // game over
								myPanel.PLAYER_LOST = true;
								JOptionPane.showMessageDialog(myFrame, "Game Over");
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3: // Right mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) { // Had
																					// pressed
																					// outside
				// Do nothing
			} else if (myPanel.PLAYER_LOST) {
				// do nothing game is over
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

	private void checkForBombsAroundGrid(int gridX, int gridY, MyPanel myPanel) {
		if (myPanel.notClickable[gridX][gridY] == false) {
			myPanel.notClickable[gridX][gridY] = true;
			myPanel.colorArray[gridX][gridY] = Color.WHITE;
			for (int x = gridX - 1; x <= gridX + 1; x++) {
				if ((x < 0 || x > myPanel.getNumColumns())) {
					continue;
				}
				for (int y = gridY - 1; y <= gridY + 1; y++) {
					if ((y < 0 || y > myPanel.getNumColumns())) {
						continue;
					}
					if (x == gridX && y == gridY) {
						myPanel.numOfSquaresToWin--;
						continue;
					}
					if (myPanel.bombPlacement[x][y]) {
						myPanel.bombsAroundGrid[gridX][gridY]++;
					}
				}
			}
			if (myPanel.bombsAroundGrid[gridX][gridY] == 0) {
				for (int x = gridX - 1; x <= gridX + 1; x++) {
					if ((x < 0 || x > myPanel.getNumColumns())) {
						continue;
					}
					for (int y = gridY - 1; y <= gridY + 1; y++) {
						if ((y < 0 || y > myPanel.getNumRows())) {
							continue;
						}
						checkForBombsAroundGrid(x, y, myPanel);
					}
				}
			}
		}
	}
}