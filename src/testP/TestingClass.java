package testP;

import dataClasses.HeroInfo;
import dataClasses.UnitCommander;
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
		
		UnitType c1 = new UnitType("crusader","crusader","castle",10,10);
		UnitType c2 = new UnitType("halberdier","halberdier","castle",7,10);
		UnitType c3 = new UnitType("pikeman","pikeman","castle",10,10);
		UnitType c4 = new UnitType("swordsman","swordsman","castle",10,10);

		UnitType d1 = new UnitType("troglodyte","troglodyte","dungeon",10,10);
		UnitType d2 = new UnitType("infernaltroglodyte","infernaltroglodyte","dungeon",10,10);
		UnitType d3 = new UnitType("minotaur","minotaur","dungeon",10,10);
		UnitType d4 = new UnitType("minotaurking","minotaurking","dungeon",10,10);

		UnitType s1 = new UnitType("ogre","ogre","stronghold",10,10);
		UnitType s2 = new UnitType("wolf","wolf","stronghold",10,10);
		UnitType s3 = new UnitType("hobgoblin","hobgoblin","stronghold",10,10);
		UnitType s4 = new UnitType("behemoth","behemoth","stronghold",10,10);
		
		UnitInfo u1  = new UnitInfo(5, c1, UnitCommander.PLAYER1);
		UnitInfo u2  = new UnitInfo(7, c2,UnitCommander.PLAYER1);
		UnitInfo u3  = new UnitInfo(7, c3,UnitCommander.PLAYER1);
		UnitInfo u4  = new UnitInfo(7, c4,UnitCommander.PLAYER1);
		
		player1.addToArmy(2, u2);
		//player1.addToArmy(1, u1);
		//player1.addToArmy(4, u4);
		//player1.addToArmy(3, u3);
		
		
		UnitInfo u11  = new UnitInfo(15, d1,UnitCommander.PLAYER2);
		UnitInfo u21  = new UnitInfo(10, d2,UnitCommander.PLAYER2);
		UnitInfo u31  = new UnitInfo(15, d3,UnitCommander.PLAYER2);
		UnitInfo u41  = new UnitInfo(2, d4,UnitCommander.PLAYER2);
		
		player2.addToArmy(2, u21);
		//player2.addToArmy(1, u11);
		//player2.addToArmy(4, u31);
		//player2.addToArmy(3, u41);

		
		controller = new  BattleController(1,player1,player2);
		
		
		controller.BattleInit();

	}

}

