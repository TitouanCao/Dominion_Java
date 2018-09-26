package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Aventurier (Adventurer)
 * 
 * Dévoilez des cartes de votre deck jusqu'à ce que 2 cartes Trésor soient dévoilées. Ajoutez ces cartes Trésor à votre main et défaussez les autres cartes dévoilées.
 */
public class Adventurer extends ActionCard {
	public Adventurer(){
		super("Adventurer",6);
	}
	
	public void play(Player p){
		Card[] cardTab = new Card[1];
		int n = 0;
		while (n < 2){
			cardTab[0]=p.drawCard();
			if (cardTab[0].getTypes().contains(CardType.Treasure)){
				p.addToHand(cardTab[0]);
				n++;
			}
			else p.gain(cardTab[0]);	
		}
	}
}
