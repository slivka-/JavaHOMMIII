package battleScreen;

import java.awt.Point;
import java.util.HashMap;

import dataClasses.CellEntity;
import dataClasses.HeroInfo;
import dataClasses.UnitInfo;
/**
 * @author slivka
 * Klasa g³ówna, kontroluje przep³yw informacji
 */
public class BattleController {

	private BattleInfo model;
	private BattleView view;
	
	private int width = 808;
	private int height = 586;
		
	public BattleController(int terrainType, HeroInfo Player1, HeroInfo Player2)
	{
		this.model = new BattleInfo();
		this.model.setPlayer1(Player1);
		this.model.setPlayer2(Player2);
		this.model.setTerraintype(terrainType);
		this.view = new BattleView(width,height,this);
	}
	
	public BattleController(int terrainType, HeroInfo Player1, HashMap<Integer, UnitInfo> Enemy)
	{
		this.model = new BattleInfo();
		this.model.setPlayer1(Player1);
		this.model.setCPUarmy(Enemy);
		this.model.setTerraintype(terrainType);
		this.view = new BattleView(width,height,this);
	}

//============================GET/SET==================================\\	
	
	public void SetPlayer1(HeroInfo heroInfo)
	{
		model.setPlayer1(heroInfo);
	}
	
	public void SetPlayer2(HeroInfo heroInfo)
	{
		model.setPlayer2(heroInfo);
	}
	
	public void SetSpectators(HeroInfo[] heroInfo)
	{
		model.setSpectators(heroInfo);
	}
	
	
	public void SetTerrain(int terrain)
	{
		model.setTerraintype(terrain);
	}
	
//============================CONTROL==================================\\	
	/**
	 * Metoda inicjalizuj¹ca bitwe
	 */
	public void BattleInit()
	{
		model.GenerateBattlefield();
		model.PalceUnits();
		
		view.DrawBattleScreen(model.getTerraintype(),model.getBattlefieldInfo());
	}
	
	/**
	 * Przesuwa jednostke na wybrane pole
	 * @param targetCell pole na które jednostka ma siê przesun¹æ
	 */
	public void MoveUnit(Point targetCell)
	{
		
		model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].contains = CellEntity.EMPTY;
		model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].unit = null;
		
		model.BattlefieldInfo[targetCell.x][targetCell.y].contains = CellEntity.UNIT;
		model.BattlefieldInfo[targetCell.x][targetCell.y].unit = model.activeUnit;
		model.activeUnit.currentPos = targetCell;
		view.moveUnit(model.BattlefieldInfo[targetCell.x][targetCell.y]);
		model.SetNextActiveUnit();
		
	}
}
