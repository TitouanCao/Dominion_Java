package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Festin (Feast)
 * 
 * Écartez cette carte.
 * Recevez une carte coûtant jusqu'à 5 Pièces.
 */
public class Feast extends ActionCard {
	public Feast(){
		super("Feast",4);
	}
	
	public void play(Player p){
		p.getGame().mooveToTrash(p,this);
		String cardChoice = p.chooseCard("Choose a card to add to your hand.", p.getGame().getFromSupply(0,5), false);
		p.gain(p.getGame().getFromSupply(cardChoice));
		p.getGame().removeFromSupply(cardChoice);
	}
}
