package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Rénovation (Remodel)
 * 
 * Écartez une carte de votre main.
 * Recevez une carte coûtant jusqu'à 2 Pièces de plus que la carte écartée.
 */
public class Remodel extends ActionCard {
	public Remodel(){
		super("Remodel",4);
	}
	
	public void play(Player p){
		String choice = p.chooseCard("Trash a card from your hand. Gain a card costing up to 2 more than the trashed card",p.cardsInHand(),false);
		p.getGame().mooveToTrash(p, p.getGame().getFromSupply(choice));
		p.removeCard(choice);
		String choice2 = p.chooseCard("Choose a card to pick up from the supply",p.getGame().getFromSupply(0, this.getCost() + 2),false);
		p.gain(p.getGame().removeFromSupply(choice2));
	}
}
