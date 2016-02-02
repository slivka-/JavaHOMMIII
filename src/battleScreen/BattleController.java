package battleScreen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import battleDisplay.PathFinding;
import dataClasses.*;
import pathFinding.FindPath;

import javax.swing.*;


//TODO: usuwać martwe jednostki z armii boharterów, ogarnąć inicjatywe, zrobić koniec bitwy, ekran końcowy, panel z info(ew obrona i poddane sie)
/**
 * @author slivka
 * Klasa g��wna, kontroluje przep�yw informacji
 */
public class BattleController {

	private BattleInfo model;
	private BattleView view;
	
	private int width = 808;
	private int height = 586;

	//IDENTYFIKACJA GRACZA
	private UnitCommander me = UnitCommander.PLAYER1;
	private boolean isMyTurn = true;
		
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

	private void debugSetMe()
	{
		this.me = model.activeUnit.commander;
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

	public UnitCommander getMe() { return this.me; }
	
//============================CONTROL==================================\\	
	/**
	 * Metoda inicjalizuj�ca bitwe
	 */
	public void BattleInit()
	{
		model.GenerateBattlefield();
		model.PalceUnits();
		
		view.DrawBattleScreen(model.getTerraintype(),model.getBattlefieldInfo());
		newTurn();
	}
	
	/**
	 * Przesuwa jednostke na wybrane pole
	 * @param targetCell pole na kt�re jednostka ma si� przesun��
	 */
	public void MoveUnit(Point targetCell)
	{
		if(isMyTurn)
		{
			view.clearUnitRange();
			Point startingPoint = model.activeUnit.currentPos;

			FindPath f = new FindPath(model.BattlefieldInfo, startingPoint, targetCell);
			ArrayList<Point> path = f.generatePath();
			System.out.println("===================");
			for (Point p : path)
			{
				System.out.println("X: " + p.x + ", Y:" + p.y);
			}
			model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].contains = CellEntity.EMPTY;
			model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].unit = null;

			model.BattlefieldInfo[targetCell.x][targetCell.y].contains = CellEntity.UNIT;
			model.BattlefieldInfo[targetCell.x][targetCell.y].unit = model.activeUnit;
			model.activeUnit.currentPos = targetCell;
			view.moveUnit(model.BattlefieldInfo[targetCell.x][targetCell.y], path);
			this.waitForAnimation(model.activeUnit,null);
		}
	}

	public void AttackUnit(Point attackTarget, Point attackPosition)
	{
		view.clearUnitRange();
		Point startingPoint = model.activeUnit.currentPos;

		FindPath f = new FindPath(model.BattlefieldInfo, startingPoint, attackPosition);
		ArrayList<Point> path = f.generatePath();
		model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].contains = CellEntity.EMPTY;
		model.BattlefieldInfo[model.activeUnit.currentPos.x][model.activeUnit.currentPos.y].unit = null;

		model.BattlefieldInfo[attackPosition.x][attackPosition.y].contains = CellEntity.UNIT;
		model.BattlefieldInfo[attackPosition.x][attackPosition.y].unit = model.activeUnit;

		model.activeUnit.currentPos = attackPosition;
		view.attackUnit(model.BattlefieldInfo[attackPosition.x][attackPosition.y], path);
		this.waitForAnimation(model.activeUnit,attackTarget);
	}

	private void endAttackTurn(Point attackTarget)
	{
		UnitInfo defender = model.BattlefieldInfo[attackTarget.x][attackTarget.y].unit;
		UnitInfo defenderAfterAttack = calculateDamage(model.activeUnit,defender);
		if(defenderAfterAttack != null)
		{
			model.BattlefieldInfo[attackTarget.x][attackTarget.y].unit = defenderAfterAttack;
		}
		else
		{
			defender.unitDeath();
			model.BattlefieldInfo[attackTarget.x][attackTarget.y].contains = CellEntity.EMPTY;
			model.BattlefieldInfo[attackTarget.x][attackTarget.y].unit = null;
			model.deleteUnitFromQueue(defender);
		}
		newTurn();
	}

	private void waitForAnimation(UnitInfo animatedUnit,Point attackTarget)
	{
		Timer timer = new Timer(20, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(animatedUnit.isAnimationFinished())
				{
					Timer t = (Timer)e.getSource();
					t.stop();
					if(attackTarget != null)
					{
						endAttackTurn(attackTarget);
					}
					else
					{
						newTurn();
					}
				}
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
	}

	private UnitInfo calculateDamage(UnitInfo attacker, UnitInfo defender)
	{
		int attackerDamage = attacker.unitSize * attacker.unitType._attack;
		int defenderHealth;
		if(defender.woundedUnitHealth == 0)
		{
			defenderHealth = defender.unitSize * defender.unitType._health;
		}
		else
		{
			defenderHealth = (defender.unitSize-1 * defender.unitType._health)+defender.woundedUnitHealth;
		}

		if(attackerDamage > defenderHealth)
		{
			return null;
		}
		else
		{
			UnitInfo output = defender;
			int remainingHealth = defenderHealth - attackerDamage;
			int remainingUnits = (int)((double)remainingHealth/(double)defender.unitType._health);
			int woundedUnitHealth = remainingHealth % defender.unitType._health;
			output.updateUnitSize(remainingUnits);
			output.woundedUnitHealth = woundedUnitHealth;

			return output;
		}
	}

	private void newTurn()
	{
		model.SetNextActiveUnit();
		ArrayList<Point> moveRange = model.getMoveRange();
		debugSetMe();
		view.drawUnitRange(moveRange);
	}

	public BattlefieldCell[][] getBattlefieldInfo()
	{
		return model.BattlefieldInfo;
	}
}
