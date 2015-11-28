package battleScreen;

import dataClasses.HeroInfo;

public class BattleInfo {

	private HeroInfo player1;
	private HeroInfo player2;
	private HeroInfo[] spectators;
	private int terrainType;
	//TODO: Dodaæ battleGrid trzymaj¹cy info o terenie, zrobiæ klasê dla pola terenu
	
	public BattleInfo()
	{
		player1 = null;
		player2 = null;
		spectators = null;
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
