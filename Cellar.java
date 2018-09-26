package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Cave (Cellar)
 * 
 * +1 Action.
 * Défaussez autant de cartes que vous voulez.
 * +1 Carte par carte défaussée.
 */
public class Cellar extends ActionCard {
	
	public Cellar(){
		super("Cellar",2);
	}
	
	public void play(Player p){
		p.incrementActions(1);
		String discardChoice = p.chooseCard("Choose a card to discard or pass with enter.", p.cardsInHand(), true);
		int discardedCardsCount = 0;
		while ((discardedCardsCount < p.cardsInHand().size()) & (!discardChoice.equals(""))){
			p.gain(p.cardsInHand().getCard(discardChoice));
			p.removeCard(discardChoice);
			discardChoice = p.chooseCard("Choose a card to discard or pass with enter.", p.cardsInHand(), true);
			discardedCardsCount++;
			}
		int i = 0;
		while (i < discardedCardsCount){
			p.addToHand(p.drawCard());
			i++;
			}
	}
}
