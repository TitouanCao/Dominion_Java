package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Forgeron (Smithy)
 * 
 * +3 Cartes.
 */
public class Smithy extends ActionCard {
	public Smithy(){
		super("Smithy",4);
	}
	
	public void play(Player p){
		p.addToHand(p.drawCard());
		p.addToHand(p.drawCard());
		p.addToHand(p.drawCard());
	}
}
