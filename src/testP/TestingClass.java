package testP;

import dataClasses.HeroInfo;
import dataClasses.UnitInfo;
import dataClasses.UnitType;
import battleScreen.BattleController;
import battleScreen.BattleInfo;
import battleScreen.BattleView;

public class TestingClass {

	private static BattleInfo model;
	private static BattleView view;
	private static BattleController controller;
	
	public static void main(String[] args) {
		model = new BattleInfo();
		view = new BattleView();
		controller = new  BattleController(model,view);
		
		HeroInfo player1 = new HeroInfo();
		
		UnitType t1 = new UnitType("def1");
		UnitType t2 = new UnitType("def2");
		
		
		
		UnitInfo u1  = new UnitInfo(5, t1);
		UnitInfo u2  = new UnitInfo(7, t2);
		
		player1.addToArmy(2, u2);
		player1.addToArmy(1, u1);
		
		
		
		controller.SetPlayer1(player1);
		controller.SetPlayer2(null);
		
		controller.SetTerrain(1);
		controller.BattleInit();

	}

}

