package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Prêteur sur gages (Moneylender)
 * 
 * Écartez une carte Cuivre de votre main.
 * Dans ce cas, +3 Pièces.
 */
public class Moneylender extends ActionCard {
	public Moneylender(){
		super("Moneylender",4);
	}
	
	public void play(Player p){
		CardList cardsInHand=p.cardsInHand();
		if(cardsInHand.getCard("Copper")!=null){
			p.removeCard("Copper");
			p.incrementMoney(3);
		}
	}
}
