package battleScreen;

import dataClasses.HeroInfo;

public class BattleController {

	private BattleInfo model;
	private BattleView view;
	
	
	public BattleController(BattleInfo model, BattleView view)
	{
		this.model = model;
		this.view = view;
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
	public void BattleInit()
	{
		view.DrawBattleScreen(model.getTerraintype());
		view.PlaceUnits(model.getPlayer1(), model.getPlayer2());
	}
	
}
