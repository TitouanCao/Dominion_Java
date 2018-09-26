package dominion.card.common;
import dominion.*;
import dominion.card.*;

/**
 * Carte Cuivre (Copper)
 * 
 * 1 Pièce
 */
public class Copper extends TreasureCard {
	public Copper() { super("Copper", 0);	}
	
	public void play(Player p){
		p.incrementMoney(1);
	}
	
	public int treasureValue() {
		return 1;
	}
}
