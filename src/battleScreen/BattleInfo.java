package battleScreen;

import java.awt.Point;
import java.util.*;


import dataClasses.*;
import sun.misc.Queue;

/**
 * @author slivka
 * Klasa przechowuje informacje o bitwie
 */
public class BattleInfo {

	private MiniHeroInfo player1;
	private MiniHeroInfo player2;
	private HashMap<Integer, UnitInfo> CPUarmy;
	private int terrainType;
	public BattlefieldCell[][] BattlefieldInfo;
	private Queue<UnitInfo> BattleQueue;
	public UnitInfo activeUnit;
	public UnitInfo nextActiveUnit;
	
	//--sta�e do rysowania--
	private final int cellWidth = 50;
	private final int cellHeight = 43;
	private final int offsetX = 75;
	private final int offsetY = 100;
	
	
	
	public BattleInfo()
	{
		player1 = null;
		player2 = null;
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
		nextActiveUnit = null;
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
		ArrayList<UnitInfo> unsortedUnits = new ArrayList<UnitInfo>();
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
			unsortedUnits.add(u);
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
			unsortedUnits.add(u);
		}

		Collections.sort(unsortedUnits, new Comparator<UnitInfo>()
		{
			@Override
			public int compare(UnitInfo o1, UnitInfo o2)
			{
				int output = 0;
				if(o1.unitType._initiative < o2.unitType._initiative)
				{
					output = 1;
				}
				else if(o1.unitType._initiative > o2.unitType._initiative)
				{
					output = -1;
				}
				else if(o1.unitType._initiative == o2.unitType._initiative)
				{
					output = 0;
				}
				return output;
			}
		});

		for(UnitInfo i : unsortedUnits)
		{
			BattleQueue.enqueue(i);
		}
	}
	
	/**
	 * Zmienia aktywny oddzial
	 */
	public void SetNextActiveUnit()
	{
		try {
			if(activeUnit == null && nextActiveUnit == null)
			{
				activeUnit = BattleQueue.dequeue(10);
				activeUnit.setAsActive();
				nextActiveUnit = BattleQueue.dequeue(10);

			}
			else
			{
				activeUnit.setAsNotActive();
				BattleQueue.enqueue(activeUnit);
				if (nextActiveUnit != null)
				{
					activeUnit = nextActiveUnit;
				} else
				{
					activeUnit = BattleQueue.dequeue(10);
				}
				activeUnit.setAsActive();
				nextActiveUnit = BattleQueue.dequeue(10);
			}
		}
		catch (InterruptedException e) {
			System.out.println("BRAK JEDNOSTEK, BITWA SKONCZONA");
			e.printStackTrace();

		}
	}

	public void deleteUnitFromQueue(UnitInfo killedUnit)
	{
		try
		{
			if(nextActiveUnit != killedUnit)
			{
				Queue<UnitInfo> newBattleQueue = new Queue<UnitInfo>();
				while (!BattleQueue.isEmpty())
				{
					UnitInfo i = BattleQueue.dequeue();
					if (!i.equals(killedUnit))
					{
						newBattleQueue.enqueue(i);
					}
				}
				BattleQueue = newBattleQueue;
			}
			else
			{
				nextActiveUnit = null;
			}
			if(killedUnit.commander == player1.ID)
			{
				player1.removeFromArmy(killedUnit);
				System.out.println(player1.getArmy());
			}
			else if(player2!= null && killedUnit.commander == player2.ID)
			{
				player2.removeFromArmy(killedUnit);
				System.out.println(player2.getArmy());
			}
			else
			{
				Iterator<Integer> CPUArmyI = CPUarmy.keySet().iterator();
				while(CPUArmyI.hasNext())
				{
					Integer key = CPUArmyI.next();
					UnitInfo u = CPUarmy.get(key);
					if(u.equals(killedUnit))
					{
						CPUarmy.remove(key,killedUnit);
						break;
					}
				}
				System.out.println(CPUarmy);
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	public boolean checkForEnd()
	{
		if(player1.getArmy().isEmpty())
		{
			System.out.println("Gracz2 Wygrywa");
			return true;
		}
		else
		{
			if(player2!= null)
			{
				if(player2.getArmy().isEmpty())
				{
					System.out.println("Gracz1 Wygrywa");
					return true;
				}

			}else if(CPUarmy!= null)
			{
				if(CPUarmy.isEmpty())
				{
					System.out.println("Gracz1 Wygrywa");
					return true;
				}
			}
		}
		return false;
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
	public MiniHeroInfo getPlayer1()
	{
		return player1;
	}
	
	public void setPlayer1(MiniHeroInfo player1)
	{
		this.player1 = player1;
	}
	
//============================PLAYER2  GET/SET==================================\\	
	public MiniHeroInfo getPlayer2()
	{
		return player2;
	}

	public void setPlayer2(MiniHeroInfo player2)
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
