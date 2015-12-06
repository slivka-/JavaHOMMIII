package testP;

import dataClasses.HeroInfo;
import dataClasses.UnitInfo;
import dataClasses.UnitType;
import battleScreen.BattleController;
import battleScreen.BattleInfo;
import battleScreen.BattleView;

public class TestingClass {

	private static BattleController controller;
	
	public static void main(String[] args) {
		
		
		
		HeroInfo player1 = new HeroInfo();
		HeroInfo player2 = new HeroInfo();
		
		UnitType t1 = new UnitType("ch³op");
		UnitType t2 = new UnitType("rycerz");
		
		UnitInfo u1  = new UnitInfo(5, t1,1);
		UnitInfo u2  = new UnitInfo(7, t2,2);
		UnitInfo u3  = new UnitInfo(7, t2,3);
		UnitInfo u4  = new UnitInfo(7, t2,4);
		UnitInfo u5  = new UnitInfo(7, t2,5);
		
		player1.addToArmy(2, u2);
		player1.addToArmy(1, u1);
		player1.addToArmy(5, u5);
		player1.addToArmy(4, u4);
		player1.addToArmy(3, u3);
		
		
		UnitInfo u11  = new UnitInfo(15, t1,3);
		UnitInfo u21  = new UnitInfo(2, t2,4);
		
		player2.addToArmy(2, u21);
		player2.addToArmy(1, u11);
		
		controller = new  BattleController(1,player1,player2);
		
		
		controller.BattleInit();

	}

}

