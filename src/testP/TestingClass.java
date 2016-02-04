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
		
		UnitType c1 = new UnitType("Rycerz","crusader","castle",10,10,1);
		UnitType c2 = new UnitType("Halabardier","halberdier","castle",10,10,5);
		UnitType c3 = new UnitType("Pikinier","pikeman","castle",10,10,1);
		UnitType c4 = new UnitType("Miecznik","swordsman","castle",10,10,1);

		UnitType d1 = new UnitType("Troglodyta","troglodyte","dungeon",10,10,1);
		UnitType d2 = new UnitType("Piekielny troglodyta","infernaltroglodyte","dungeon",10,10,2);
		UnitType d3 = new UnitType("Minotaur","minotaur","dungeon",10,10,1);
		UnitType d4 = new UnitType("Uberminotaur","minotaurking","dungeon",10,10,1);

		UnitType s1 = new UnitType("ogre","ogre","stronghold",10,10,1);
		UnitType s2 = new UnitType("wolf","wolf","stronghold",10,10,1);
		UnitType s3 = new UnitType("hobgoblin","hobgoblin","stronghold",10,10,1);
		UnitType s4 = new UnitType("behemoth","behemoth","stronghold",10,10,1);
		
		UnitInfo u1  = new UnitInfo(10, c1, UnitCommander.PLAYER1);
		UnitInfo u2  = new UnitInfo(10, c2,UnitCommander.PLAYER1);
		UnitInfo u3  = new UnitInfo(10, c3,UnitCommander.PLAYER1);
		UnitInfo u4  = new UnitInfo(10, c4,UnitCommander.PLAYER1);
		
		player1.addToArmy(2, u2);
		player1.addToArmy(1, u1);
		player1.addToArmy(4, u4);
		player1.addToArmy(3, u3);
		
		
		UnitInfo u11  = new UnitInfo(10, d1,UnitCommander.PLAYER2);
		UnitInfo u21  = new UnitInfo(10, d2,UnitCommander.PLAYER2);
		UnitInfo u31  = new UnitInfo(10, d3,UnitCommander.PLAYER2);
		UnitInfo u41  = new UnitInfo(10, d4,UnitCommander.PLAYER2);
		
		player2.addToArmy(2, u21);
		player2.addToArmy(1, u11);
		player2.addToArmy(4, u31);
		player2.addToArmy(3, u41);

		
		controller = new  BattleController(1,player1,player2,UnitCommander.PLAYER1);
		
		
		controller.BattleInit();

	}

}

