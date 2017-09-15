package model;

import java.util.ArrayList;
import java.util.List;

import model.buildings.Building;
import model.buildings.Hospital;
import model.buildings.House;
import model.buildings.Lottery;
import model.buildings.News;
import model.buildings.Origin;
import model.buildings.Park;
import model.buildings.Point;
import model.buildings.Prison;
import model.buildings.Shop_;

/**
 * ?????????
 * 
 * @author MOVELIGHTS
 * 
 */
public class BuildingsModel extends Tick implements Port{
	/**
	 * ????????
	 */
	private List<Building> buildings = null;
	
	private LandModel land = null;

	
	public BuildingsModel (LandModel land){
		this.land = land;
	}


	/**
	 * 
	 * ?????????
	 * 
	 */
	private void initBuilding() {
		// ?????????
		buildings = new ArrayList<Building>();
		// ????????????
		int[][] temp = this.land.getLand();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				switch (temp[i][j]) {
				case LandModel.SPACE:
					Building tempBuidling = new House(i, j);
					// ???ÿ????????????????
					tempBuidling.setPurchasability(true);
					buildings.add(tempBuidling);
					break;
				case LandModel.HOSPITAL:// ??
					buildings.add(new Hospital(i, j));
					//??????????
					LandModel.hospital = new java.awt.Point(j * 60,i * 60);
//					System.out.println(LandModel.hospital );
					break;
				case LandModel.LOTTERY:
					buildings.add(new Lottery(i, j));
					break;
				case LandModel.NEWS:
					buildings.add(new News(i, j));
					break;
				case LandModel.ORIGIN:
					buildings.add(new Origin(i, j));
					break;
				case LandModel.PARK:
					buildings.add(new Park(i, j));
					break;
				case LandModel.PIONT_10:
					buildings.add(new Point(i, j, 10));
					break;
				case LandModel.PIONT_30:
					buildings.add(new Point(i, j, 30));
					break;
				case LandModel.PIONT_50:
					buildings.add(new Point(i, j, 50));
					break;
				case LandModel.SHOP:
					buildings.add(new Shop_(i, j));
					break;
				case LandModel.PRISON:// ????
					buildings.add(new Prison(i, j));
					//????????????
					LandModel.prison = new java.awt.Point(j * 60, i * 60);
//					System.out.println(LandModel.prison );
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * 
	 * ??÷????
	 * 
	 * @return
	 */
	public List<Building> getBuilding(){
		return buildings;
	}
	/**
	 * 
	 * ??õ????÷???
	 * 
	 */
	public Building getBuilding(int x,int y){
		for (Building temp : this.buildings){
			if (temp.getPosX() == x && temp.getPosY() == y){
				return temp;
			}
		}
		return null;
	}
	/**
	 * 
	 * ??????????
	 * 
	 */
	public void startGameInit (){
		// ?????????
		initBuilding();
	}

	@Override
	public void updata(long tick) {
		this.nowTick = tick;
		
	}
}