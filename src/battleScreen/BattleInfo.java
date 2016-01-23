package battleScreen;

import java.awt.Point;
import java.util.ArrayList;
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
	
	//--sta�e do rysowania--
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
		BattlefieldInfo = new BattlefieldCell[14][10];
		for(int i=0;i<14;i++)
		{
			for(int j=0;j<10;j++)
			{
				BattlefieldInfo[i][j] = new BattlefieldCell(offsetX+(cellWidth*i),offsetY+(cellHeight*(j+1)),new Point(i,j));
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
		for(int i=2;i<12;i++)
		{
			for(int j=0;j<10;j++)
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
			u.currentPos = new Point(0,(key*2)-1);
			u.setUnitDisplay(new Point(BattlefieldInfo[0][(key*2)-1].drawingPoint));
			BattlefieldInfo[0][(key*2)-1].contains = CellEntity.UNIT;
			BattlefieldInfo[0][(key*2)-1].unit = u;
			BattleQueue.enqueue(u);
			//System.out.println(u.currentPos);
		}
		

		//--sprawdzanie czy armia 2 to gracz czy wolny oddzia�
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
			UnitInfo u = player2Army.get(key);
			u.currentPos = new Point(13,(key*2)-1);
			u.setUnitDisplay(new Point(BattlefieldInfo[13][(key*2)-1].drawingPoint));
			BattlefieldInfo[13][(key*2)-1].contains = CellEntity.UNIT;
			BattlefieldInfo[13][(key*2)-1].unit = u;
			BattleQueue.enqueue(u);
			//System.out.println(u.unitID+", "+u.unitSize);
		}
		/*
		try {
			activeUnit = BattleQueue.dequeue();
			activeUnit.setAsActive();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		//activeUnit.unitDisplay.setAsActive();
	}
	
	/**
	 * Zmienia aktywny oddzial
	 */
	public void SetNextActiveUnit()
	{
		if(activeUnit!=null)
		{
			BattleQueue.enqueue(activeUnit);
			activeUnit.setAsNotActive();
		}
		try {
			activeUnit = BattleQueue.dequeue();
			activeUnit.setAsActive();
		} catch (InterruptedException e) {
			System.out.println("BRAK JEDNOSTEK, BITWA SKONCZONA");
			e.printStackTrace();
		}
	}

	public ArrayList<Point> getMoveRange()
	{
		int range = activeUnit.unitType.speed;
		ArrayList<Point> moveRange = new ArrayList<Point>();
		for(int i=0;i<14;i++)
		{
			for(int j=0;j<10;j++)
			{
				int distance = Math.abs(BattlefieldInfo[i][j].location.x - activeUnit.currentPos.x) + Math.abs(BattlefieldInfo[i][j].location.y - activeUnit.currentPos.y);
				if(distance <= range)
				{
					moveRange.add(new Point(50+50*i,110+43*j));
				}
			}
		}
		return moveRange;
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
