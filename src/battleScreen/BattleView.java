package battleScreen;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import battleDisplay.BackgroundPanel;
import battleDisplay.GraphicsPanel;
import dataClasses.BattlefieldCell;
import dataClasses.CellEntity;
import dataClasses.UnitInfo;


/**
 * @author slivka
 * Klasa kontroluj¹ca wyœwietlanie pola bitwy
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
				System.out.println((e.getX()-50)/50+", "+((e.getY()-110)/43));
				Point p = new Point(((e.getY()-110)/43),(e.getX()-50)/50);
				mainController.MoveUnit(p);
			}
		};
	}
	
	public void DrawBattleScreen(int terraintype, BattlefieldCell[][] BattlefieldInfo)
	{
		InitializeDisplay();
		InitializeBattlefield(BattlefieldInfo);
	}
	
	/**
	 * Metoda inicjalizuje i wyœwietla pole bitwy
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
		BackgroundPanel Background = new BackgroundPanel("assets\\terrain\\BG\\BG0.png",_mListener);
		Background.setOpaque(true);
		lPane.add(Background, new Integer(0),0);
		mainBattleFrame.setVisible(true);
	}
		
	/**
	 * Metoda wyœwietla jednostki na polu bitwy
	 * @param BattlefiledInfo tablica zawierajaca informacje o polu bitwy
	 */
	private void InitializeBattlefield(BattlefieldCell[][] BattlefieldInfo)
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<14;j++)
			{
				if(BattlefieldInfo[i][j].contains == CellEntity.UNIT)
				{
					BattlefieldCell c = BattlefieldInfo[i][j];
					UnitInfo u = c.unit;
					GraphicsPanel g = new GraphicsPanel(u.unitType.spritepath, c.drawingPoint.x, c.drawingPoint.y ,u.unitID,u.unitSize);
					u.unitDisplay = g;
					if(j==13)
					{
						g.flipFacing();
					}
					g.setOpaque(false);
					lPane.add(g, i,0);
				}
				if(BattlefieldInfo[i][j].contains == CellEntity.OBSTACLE)
				{
					BattlefieldCell c = BattlefieldInfo[i][j];
					GraphicsPanel g = new GraphicsPanel(c.imgPath, c.drawingPoint.x, c.drawingPoint.y ,0,0);
					g.setOpaque(false);
					lPane.add(g, i,0);
				}
			}
		}
	}
	
	/**
	 * Metoda poruszajaca jednostki
	 * @param cell polena ktorym ma znalezc sie jednostka
	 */
	public void moveUnit(BattlefieldCell cell)
	{
		
		UnitInfo uInfo = cell.unit;
		GraphicsPanel unitGraphics = uInfo.unitDisplay;
		System.out.println(cell.drawingPoint.x+", "+cell.drawingPoint.y);
		unitGraphics.movePanel(cell.drawingPoint);
	}
}
