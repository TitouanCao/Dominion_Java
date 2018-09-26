package dominion.card.common;
import dominion.*;
import dominion.card.*;

/**
 * Carte Argent (Silver)
 * 
 * 2 Pièces
 */
public class Silver extends TreasureCard {
	public Silver() { super("Silver", 3);	}
	
	public void play(Player p){
		p.incrementMoney(2);
	}
	
	public int treasureValue() {
		return 2;
	}
}
