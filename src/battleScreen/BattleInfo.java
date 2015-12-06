package battleScreen;

import java.util.HashMap;

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
	//TODO: Dodaæ battleGrid trzymaj¹cy info o terenie, zrobiæ klasê dla pola terenu
	
	public BattleInfo()
	{
		player1 = null;
		player2 = null;
		spectators = null;
		CPUarmy = null;
		terrainType = 0;
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
