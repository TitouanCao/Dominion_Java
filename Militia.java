package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Milice (Militia)
 * 
 * 2 Pièces.
 * Tous vos adversaires défaussent leurs cartes de façon à n'avoir que 3 cartes en main.
 */
public class Militia extends AttackCard {
	public Militia(){
		super("Militia",4);
	}
	
	public void play(Player p){
		p.incrementMoney(2);
		int n = 0;
		while (n < p.otherPlayers().size()){
			while (p.otherPlayers().get(n).cardsInHand().size() > 3) {	
				String toDiscardChoice = p.otherPlayers().get(n).chooseCard("Choose a card to discard : "+p.otherPlayers().get(n).cardsInHand().toString(), p.otherPlayers().get(n).cardsInHand(), false);
				p.otherPlayers().get(n).gain(p.otherPlayers().get(n).cardsInHand().getCard(toDiscardChoice));
				p.otherPlayers().get(n).removeCard(toDiscardChoice);
			}
				
		n++;
		}
	}
}
