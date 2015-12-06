package battleScreen;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import battleDisplay.BackgroundPanel;
import battleDisplay.GraphicsPanel;
import dataClasses.UnitInfo;


/**
 * @author slivka
 * Klasa kontroluj¹ca wyœwietlanie pola bitwy
 */
public class BattleView {

	private JFrame mainBattleFrame;
	private JLayeredPane lPane;
	private int _bfWidth;
	private int _bfHeight;
	
	public BattleView(int width, int height)
	{
		_bfWidth = width;
		_bfHeight = height;
	}
	
	public void DrawBattleScreen(int terraintype, HashMap<Integer, UnitInfo> army0, HashMap<Integer, UnitInfo> army1)
	{
		InitializeDisplay();
		PlaceUnits(army0, army1);
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
		BackgroundPanel Background = new BackgroundPanel("assets\\terrain\\BG0.png");
		Background.setOpaque(true);
		lPane.add(Background, new Integer(0),0);
		mainBattleFrame.setVisible(true);
	}
		
	/**
	 * Metoda wyœwietla jednostki na polu bitwy
	 * @param army0 Jednostki armii 1
	 * @param army1 Jednostki armii 2
	 */
	private void PlaceUnits(HashMap<Integer, UnitInfo> army0, HashMap<Integer, UnitInfo> army1)
	{
		
		System.out.println("ARMY0");
		Iterator<Integer> army0Iterator = army0.keySet().iterator();
		while(army0Iterator.hasNext())
		{
			Integer key = army0Iterator.next();
			UnitInfo u = army0.get(key);
			System.out.println(key+"| L:"+u.unitSize+" type: "+u.unitType.name);
			GraphicsPanel g = new GraphicsPanel(u.unitType.spritepath, 50, 90*key,u.unitID,u.unitSize);
			g.setOpaque(false);
			lPane.add(g, key,0);
			
		}
		
		System.out.println("ARMY1");
		Iterator<Integer> army1Iterator = army1.keySet().iterator();
		while(army1Iterator.hasNext())
		{
			Integer key = army1Iterator.next();
			UnitInfo u = army1.get(key);
			System.out.println(key+"| L:"+u.unitSize+" type: "+u.unitType.name);
			GraphicsPanel g = new GraphicsPanel(u.unitType.spritepath, 700, 90*key,u.unitID,u.unitSize);
			g.setOpaque(false);
			g.flipFacing();
			lPane.add(g, key,0);
		}
	}
}
