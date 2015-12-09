package battleScreen;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;



import sun.misc.Queue;
import dataClasses.BattlefieldCell;
import dataClasses.CellEntity;
import dataClasses.HeroInfo;
import dataClasses.UnitInfo;

/**
 * @author slivka
 * Klasa przechowuje informacje o bitwie
 */
public class BattleInfo {

	private HeroInfo player1;
	private HeroInfo player2;
	private HeroInfo[] spectators;
	private HashMap<Integer, UnitInfo> CPUarmy;
	private int terrainType;
	public BattlefieldCell[][] BattlefieldInfo;
	private Queue<UnitInfo> BattleQueue;
	public UnitInfo activeUnit;
	
	//--sta³e do rysowania--
	private final int cellWidth = 50;
	private final int cellHeight = 43;
	private final int offsetX = 75;
	private final int offsetY = 100;
	
	
	
	public BattleInfo()
	{
		player1 = null;
		player2 = null;
		spectators = null;
		CPUarmy = null;
		terrainType = 0;
		BattlefieldInfo = new BattlefieldCell[10][14];
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<14;j++)
			{
				BattlefieldInfo[i][j] = new BattlefieldCell(offsetX+(cellWidth*j),offsetY+(cellHeight*(i+1)));
			}
		}
		BattleQueue = new Queue<UnitInfo>();
		activeUnit = null;
	}
	
	/**
	 * Generuje przeszkody na polu bitwy
	 */
	public void GenerateBattlefield()
	{
		Random rnd = new Random();
		for(int i=0;i<10;i++)
		{
			for(int j=2;j<12;j++)
			{
				if(rnd.nextInt(100)>95)
				{
					BattlefieldInfo[i][j].contains = CellEntity.OBSTACLE;
					BattlefieldInfo[i][j].imgPath = "assets\\terrain\\OBS\\kamien.png";
				}
				 
			}
		}
	}
	
	/**
	 * umieszcza jednostki na polu bitwy
	 */
	public void PalceUnits()
	{
		
		//--umieszczanie jednostek armii 1
		HashMap<Integer, UnitInfo> player1Army = player1.getArmy();
		Iterator<Integer> army1Iterator = player1Army.keySet().iterator();
		while(army1Iterator.hasNext())
		{
			Integer key = army1Iterator.next();
			UnitInfo u = player1Army.get(key);
			u.currentPos = new Point((key*2)-1,0);
			BattlefieldInfo[(key*2)-1][0].contains = CellEntity.UNIT;
			BattlefieldInfo[(key*2)-1][0].unit = u;
			BattleQueue.enqueue(u);
		}
		
		
		//--sprawdzanie czy armia 2 to gracz czy wolny oddzia³
		HashMap<Integer, UnitInfo> player2Army;
		if(player2 != null){
			player2Army = player2.getArmy();
		}
		else
		{
			player2Army = CPUarmy;
		}
		
		
		//--umieszczanie jednostek armii 2
		Iterator<Integer> army2Iterator = player2Army.keySet().iterator();
		while(army2Iterator.hasNext())
		{
			Integer key = army2Iterator.next();
			UnitInfo u = player1Army.get(key);
			u.currentPos = new Point((key*2)-1,13);
			BattlefieldInfo[(key*2)-1][13].contains = CellEntity.UNIT;
			BattlefieldInfo[(key*2)-1][13].unit = u;
			BattleQueue.enqueue(u);
		}
		try {
			activeUnit = BattleQueue.dequeue();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Zmienia aktywny oddzial
	 */
	public void SetNextActiveUnit()
	{
		BattleQueue.enqueue(activeUnit);
		try {
			activeUnit = BattleQueue.dequeue();
		} catch (InterruptedException e) {
			System.out.println("BRAK JEDNOSTEK, BITWA SKONCZONA");
			e.printStackTrace();
		}
	}
		
	public BattlefieldCell[][] getBattlefieldInfo()
	{
		return BattlefieldInfo;
	}
	
//============================PLAYER 1 GET/SET==================================\\	
	public HeroInfo getPlayer1() 
	{
		return player1;
	}
	
	public void setPlayer1(HeroInfo player1) 
	{
		this.player1 = player1;
	}
	
//============================PLAYER2  GET/SET==================================\\	
	public HeroInfo getPlayer2() 
	{
		return player2;
	}

	public void setPlayer2(HeroInfo player2) 
	{
		this.player2 = player2;
	}
	
//============================CUP army  GET/SET==================================\\		
	public HashMap<Integer, UnitInfo> getCPUarmy() {
		return CPUarmy;
	}

	public void setCPUarmy(HashMap<Integer, UnitInfo> cPUarmy) {
		CPUarmy = cPUarmy;
	}
	
//============================SPECTATORS GET/SET================================\\	
	public HeroInfo[] getSpectators() 
	{
		return spectators;
	}
	
	public void setSpectators(HeroInfo[] spectators) 
	{
		this.spectators = spectators;
	}
	
//==============================================================================\\		
	public int getTerraintype() 
	{
		return terrainType;
	}
	
	public void setTerraintype(int terraintype) 
	{
		this.terrainType = terraintype;
	}
}
