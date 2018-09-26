package dominion.card.common;
import dominion.*;
import dominion.card.*;

/**
 * Carte Or (Gold)
 * 
 * 3 Pièces
 */
public class Gold extends TreasureCard {
	public Gold(){ 
		super("Gold", 6);	
	}
	
	public void play(Player p){
		p.incrementMoney(3);
	}
	
	public int treasureValue() {
		return 3;
	}
}
