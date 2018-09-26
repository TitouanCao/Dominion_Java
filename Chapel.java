package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chapelle (Chapel)
 * 
 * Écartez jusqu'à 4 cartes de votre main.
 */
public class Chapel extends ActionCard {
	public Chapel(){
		super("Chapel",2);
	}
	
	public void play(Player p){
		int i = 0;
		String toTrashChoice = p.chooseCard("Choose a card to trash up or pass with enter.", p.cardsInHand(), true);
		while (i < 4 & toTrashChoice != ""){
			i++;
			p.getGame().mooveToTrash(p,p.removeCard(toTrashChoice));
			if (i < 4){
				toTrashChoice = p.chooseCard("Choose a card to trash up or pass with enter.", p.cardsInHand(), true);
			}
		}
	}
}
