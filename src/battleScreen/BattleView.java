package battleScreen;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

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
	
	public BattleView(int width, int height, BattleController mControl)
	{
		mainController = mControl;
		_bfWidth = width;
		_bfHeight = height;
		_mListener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println((e.getX()-50)/50+", "+((e.getY()-110)/43));
				//Point p = new Point((e.getX()-50)/50,((e.getY()-110)/43));
				RangeIndicator r = (RangeIndicator)e.getSource();
				//System.out.println(r.location);
				mainController.MoveUnit(r.location);
			}
		};
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
		RangeIndicator r = new RangeIndicator(new Point(0,0),_mListener);
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
				r = new RangeIndicator(p, null);
			}
			else
			{
				r = new RangeIndicator(p, _mListener);
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
}
