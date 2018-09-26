package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Bibliothèque (Library)
 * 
 * Piochez jusqu'à ce que vous ayez 7 cartes en main. Chaque carte Action piochée peut être mise de côté. Défaussez les cartes mises de côté lorsque vous avez terminé de piocher.
 */
public class Library extends ActionCard {
	public Library(){
		super("Library",5);
	}
	
	public void play(Player p){
		CardList aside = new CardList();
		List<String> choices = Arrays.asList("y", "n");
		while (p.cardsInHand().size() != 7){
			aside.add(p.drawCard());
			if (aside.get(aside.size()-1).getTypes().contains(CardType.Action)){
				String playerChoice = p.choose("Do you want to put aside this Action card ? (y/n)", choices, false);
				if (playerChoice == "n"){
					p.addToHand(aside.remove(aside.get(aside.size()-1).getName()));
					}
				}
			else {
				p.addToHand(aside.remove(aside.get(aside.size()-1).getName()));
				
			}
		}
		while (aside.isEmpty() != true) {
			p.gain(aside.remove(aside.get(aside.size()-1).getName()));
		}
	}
}
