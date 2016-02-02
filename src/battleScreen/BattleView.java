package battleScreen;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import battleDisplay.AttackDirection;
import battleDisplay.BackgroundPanel;
import battleDisplay.RangeIndicator;
import battleDisplay.StaticGraphicsPanel;
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
	
	public BattleView(int width, int height, BattleController mControl)
	{
		mainController = mControl;
		_bfWidth = width;
		_bfHeight = height;
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
				RangeIndicator r = (RangeIndicator)e.getSource();
				mainController.MoveUnit(r.location);
				mainBattleFrame.setCursor(Cursor.getDefaultCursor());
			}
		};

		_aListener = new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				RangeIndicator r = (RangeIndicator)e.getSource();
				Point attackPosition = new Point();
				switch (r.attackDirection)
				{
					case UP:
						attackPosition = new Point(r.location.x,r.location.y-1);
						break;
					case DOWN:
						attackPosition = new Point(r.location.x,r.location.y+1);
						break;
					case LEFT:
						attackPosition = new Point(r.location.x-1,r.location.y);
						break;
					case RIGHT:
						attackPosition = new Point(r.location.x+1,r.location.y);
						break;
				}
				mainController.AttackUnit(r.location,attackPosition);
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
				RangeIndicator r = (RangeIndicator)e.getSource();


				if(e.getY()>30 && e.getY()<43 && e.getX()>15 && e.getX()<35 && r.location.y<9)
				{
					mainBattleFrame.setCursor(swordDownCursor);
					r.attackDirection = AttackDirection.DOWN;
				}

				if(e.getY()>0 && e.getY()<13 && e.getX()>15 && e.getX()<35&& r.location.y>0)
				{
					mainBattleFrame.setCursor(swordUpCursor);
					r.attackDirection = AttackDirection.UP;
				}

				if(e.getY()>13 && e.getY()<30 && e.getX()>0 && e.getX()<15&& r.location.x>0)
				{
					mainBattleFrame.setCursor(swordLeftCursor);
					r.attackDirection = AttackDirection.LEFT;
				}

				if(e.getY()>13 && e.getY()<30 && e.getX()>35 && e.getX()<50&& r.location.x<13)
				{
					mainBattleFrame.setCursor(swordRightCursor);
					r.attackDirection = AttackDirection.RIGHT;
				}

			}
		};

	}
		
	/**
	 * Metoda wy�wietla jednostki na polu bitwy
	 * @param BattlefiledInfo tablica zawierajaca informacje o polu bitwy
	 */
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
		RangeIndicator r = new RangeIndicator(new Point(0,0),null,null);
		for(Component cmp : c)
		{
			if(cmp.getClass() == r.getClass())
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
}
