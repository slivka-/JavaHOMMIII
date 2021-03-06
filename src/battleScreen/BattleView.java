package battleScreen;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;

import HeroDisplay.HeroDisplayPanel;
import battleDisplay.*;
import dataClasses.BattlefieldCell;
import dataClasses.CellEntity;
import dataClasses.UnitInfo;


/**
 * @author slivka
 * Klasa kontroluj�ca wy�wietlanie pola bitwy
 */
public class BattleView {

	private BattleController mainController;
	private JFrame mainBattleFrame;
	private JLayeredPane lPane;
	private int _bfWidth;
	private int _bfHeight;
	private MouseListener _mListener;
	private MouseListener _aListener;
	private MouseMotionListener _aDirectionListener;
	private JLabel infoBoxText;
	private ExecutorService ex;

	public BattleView(int width, int height, BattleController mControl)
	{
		mainController = mControl;
		_bfWidth = width;
		_bfHeight = height;
		ex = Executors.newCachedThreadPool();
	}
	
	public void DrawBattleScreen(int terraintype, BattlefieldCell[][] BattlefieldInfo)
	{
		InitializeDisplay();
		InitializeBattlefield(BattlefieldInfo);
	}
	
	/**
	 * Metoda inicjalizuje i wy�wietla pole bitwy
	 */
	private void InitializeDisplay()
	{
		
		mainBattleFrame = new JFrame("Battle");
		mainBattleFrame.setSize(_bfWidth, _bfHeight);
		mainBattleFrame.setLayout(new BorderLayout());
		mainBattleFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });
		
		lPane = new JLayeredPane();
		mainBattleFrame.add(lPane, BorderLayout.CENTER);
		
		lPane.setBounds(0, 0, _bfWidth, _bfHeight);
		BackgroundPanel Background = new BackgroundPanel("assets\\terrain\\BG\\BG0.png");
		Background.setOpaque(true);
		lPane.add(Background, new Integer(0),0);
		mainBattleFrame.setVisible(true);

		JLayeredPane mainBattleInterface = new JLayeredPane();
		mainBattleInterface.setBounds(0,556,808,70);
		mainBattleInterface.setVisible(true);
		mainBattleFrame.add(mainBattleInterface,null,0);

		JPanel runButton = new interfaceButton("assets\\interface\\runButton.png", new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				mainController.battleFlee();
			}
			@Override
			public void mousePressed(MouseEvent e){}
			@Override
			public void mouseReleased(MouseEvent e){}
			@Override
			public void mouseEntered(MouseEvent e){}
			@Override
			public void mouseExited(MouseEvent e){}
		});
		runButton.setBounds(0,556,90,70);
		runButton.setVisible(true);
		mainBattleInterface.add(runButton);

		JPanel defButton = new interfaceButton("assets\\interface\\defendButton.png", new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				mainController.unitDefend();
			}
			@Override
			public void mousePressed(MouseEvent e){}
			@Override
			public void mouseReleased(MouseEvent e){}
			@Override
			public void mouseEntered(MouseEvent e){}
			@Override
			public void mouseExited(MouseEvent e){}
		});
		defButton.setBounds(702,556,90,70);
		defButton.setVisible(true);
		mainBattleInterface.add(defButton);

		JPanel infoBox = new infoBox("assets\\interface\\banner.png");
		infoBox.setBounds(90,556,612,70);
		infoBox.setVisible(true);
		mainBattleInterface.add(infoBox);

		infoBoxText = new JLabel();
		infoBoxText.setBounds(100,586,592,50);
		infoBoxText.setVisible(true);
		infoBoxText.setForeground(Color.white);
		infoBox.add(infoBoxText, -1);
		infoBoxText.setText("BITWA ROZPOCZ\u0118TA");



		//MOUSE LISTENERS+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		_mListener = new MouseListener() {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image moveImage = toolkit.getImage("assets/cursors/move.png");
			Cursor moveCursor = toolkit.createCustomCursor(moveImage,new Point(12,12),"img");

			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {

					mainBattleFrame.setCursor(Cursor.getDefaultCursor());

			}
			@Override
			public void mouseEntered(MouseEvent e) {

					mainBattleFrame.setCursor(moveCursor);

			}
			@Override
			public void mouseClicked(MouseEvent e) {

					RangeIndicator r = (RangeIndicator) e.getSource();
					mainController.MoveUnitSend(r.location);
					mainBattleFrame.setCursor(Cursor.getDefaultCursor());

			}
		};

		_aListener = new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{

					RangeIndicator r = (RangeIndicator) e.getSource();
					Point attackPosition = new Point();
					switch (r.attackDirection)
					{
						case UP:
							attackPosition = new Point(r.location.x, r.location.y - 1);
							break;
						case DOWN:
							attackPosition = new Point(r.location.x, r.location.y + 1);
							break;
						case LEFT:
							attackPosition = new Point(r.location.x - 1, r.location.y);
							break;
						case RIGHT:
							attackPosition = new Point(r.location.x + 1, r.location.y);
							break;
					}
					mainController.AttackUnitSend(r.location, attackPosition);
					mainBattleFrame.setCursor(Cursor.getDefaultCursor());

			}

			@Override
			public void mousePressed(MouseEvent e)
			{

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseExited(MouseEvent e)
			{
					mainBattleFrame.setCursor(Cursor.getDefaultCursor());
			}
		};

		_aDirectionListener = new MouseMotionListener()
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();

			Image swordDownImg = toolkit.getImage("assets/cursors/swordDown.png");
			Cursor swordDownCursor = toolkit.createCustomCursor(swordDownImg,new Point(12,0),"swordDown");

			Image swordUpImg = toolkit.getImage("assets/cursors/swordUp.png");
			Cursor swordUpCursor = toolkit.createCustomCursor(swordUpImg,new Point(12,31),"swordUp");

			Image swordRightImg = toolkit.getImage("assets/cursors/swordRight.png");
			Cursor swordRightCursor = toolkit.createCustomCursor(swordRightImg,new Point(0,12),"swordRight");

			Image swordLeftImg = toolkit.getImage("assets/cursors/swordLeft.png");
			Cursor swordLeftCursor = toolkit.createCustomCursor(swordLeftImg,new Point(31,12),"swordLeft");

			@Override
			public void mouseDragged(MouseEvent e)
			{

			}

			@Override
			public void mouseMoved(MouseEvent e)
			{

					RangeIndicator r = (RangeIndicator) e.getSource();

					if (e.getY() > 30 && e.getY() < 43 && e.getX() > 15 && e.getX() < 35 && r.location.y < 9)
					{
						Point p = new Point(r.location.x,r.location.y+1);
						BattlefieldCell c = mainController.getBattlefieldCell(p);
						if(c!=null)
						{
							if(c.contains == CellEntity.EMPTY || (p.x == mainController.getActiveUnitLocation().x && p.y == mainController.getActiveUnitLocation().y))
							{
								mainBattleFrame.setCursor(swordDownCursor);
								r.attackDirection = AttackDirection.DOWN;
							}
						}
					}

					if (e.getY() > 0 && e.getY() < 13 && e.getX() > 15 && e.getX() < 35 && r.location.y > 0)
					{
						Point p = new Point(r.location.x,r.location.y-1);
						BattlefieldCell c = mainController.getBattlefieldCell(p);
						if(c!=null)
						{
							if(c.contains == CellEntity.EMPTY || (p.x == mainController.getActiveUnitLocation().x && p.y == mainController.getActiveUnitLocation().y))
							{
								mainBattleFrame.setCursor(swordUpCursor);
								r.attackDirection = AttackDirection.UP;
							}
						}
					}

					if (e.getY() > 13 && e.getY() < 30 && e.getX() > 0 && e.getX() < 15 && r.location.x > 0)
					{
						Point p = new Point(r.location.x-1,r.location.y);
						BattlefieldCell c = mainController.getBattlefieldCell(p);
						if(c!=null)
						{
							if (c.contains == CellEntity.EMPTY || (p.x == mainController.getActiveUnitLocation().x && p.y == mainController.getActiveUnitLocation().y))
							{
								mainBattleFrame.setCursor(swordLeftCursor);
								r.attackDirection = AttackDirection.LEFT;
							}
						}
					}

					if (e.getY() > 13 && e.getY() < 30 && e.getX() > 35 && e.getX() < 50 && r.location.x < 13)
					{
						Point p = new Point(r.location.x+1,r.location.y);
						BattlefieldCell c = mainController.getBattlefieldCell(p);
						if(c!=null)
						{
							if (c.contains == CellEntity.EMPTY || (p.x == mainController.getActiveUnitLocation().x && p.y == mainController.getActiveUnitLocation().y))
							{
								mainBattleFrame.setCursor(swordRightCursor);
								r.attackDirection = AttackDirection.RIGHT;
							}
						}
					}
				}

		};

	}
		

	private void InitializeBattlefield(BattlefieldCell[][] BattlefieldInfo)
	{
		for(int i=0;i<14;i++)
		{
			for(int j=0;j<10;j++)
			{
				if(BattlefieldInfo[i][j].contains == CellEntity.UNIT)
				{
					BattlefieldCell c = BattlefieldInfo[i][j];
					UnitInfo u = c.unit;
					if(i==13)
					{
						u.flipFacing();
					}
					lPane.add(u.getUnitDisplay(),j,j+1);
					ex.execute(u.getUnitDisplay());
				}

				if(BattlefieldInfo[i][j].contains == CellEntity.OBSTACLE)
				{
					BattlefieldCell c = BattlefieldInfo[i][j];
					StaticGraphicsPanel g = new StaticGraphicsPanel(c.imgPath, c.drawingPoint.x, c.drawingPoint.y);
					g.setOpaque(false);
					lPane.add(g, j,j+1);
				}

			}
		}
	}

	public void clearUnitRange()
	{
		Component[] c = lPane.getComponents();
		for(Component cmp : c)
		{
			if(cmp.getClass().equals(RangeIndicator.class))
			{
				lPane.remove(cmp);
			}
		}
		lPane.repaint();
	}

	public void drawUnitRange(ArrayList<Point> range)
	{
		for(Point p : range)
		{
			BattlefieldCell c = mainController.getBattlefieldInfo()[(p.x-50)/50][(p.y-110)/43];
			RangeIndicator r;
			if(c.contains == CellEntity.OBSTACLE)
			{
				r = new RangeIndicator(p, null,null);
			}
			else if(c.contains == CellEntity.UNIT)
			{
				if(c.unit.commander != mainController.getMe())
				{
					r = new RangeIndicator(p, _aListener,_aDirectionListener);
				}
				else
				{
					r = new RangeIndicator(p, null,null);
				}
			}
			else
			{
				r = new RangeIndicator(p, _mListener,null);
			}
			r.setVisible(true);
			lPane.add(r, new Integer(0), -1);
			lPane.setLayer(r,1);
		}
		lPane.repaint();
	}
	
	/**
	 * Metoda poruszajaca jednostki
	 * @param cell polena ktorym ma znalezc sie jednostka
	 */
	public void moveUnit(BattlefieldCell cell, ArrayList<Point> path)
	{
		UnitInfo uInfo = cell.unit;
		uInfo.moveUnit(path);
	}

	public void attackUnit(BattlefieldCell cell, ArrayList<Point> path)
	{
		UnitInfo uInfo = cell.unit;
		uInfo.attackUnit(path);
	}

	public void setBattleText(String battleText)
	{
		this.infoBoxText.setText(battleText);
	}

	public void endBattle()
	{
		mainBattleFrame.setVisible(false);
		mainBattleFrame.dispose();
	}
}
