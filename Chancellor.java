package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chancellier (Chancellor)
 * 
 * +2 Pièces.
 * Vous pouvez immédiatement défausser votre deck.
 */
public class Chancellor extends ActionCard {
	public Chancellor(){
		super("Chancellor",3);
	}
	
	public void play(Player p){
		p.incrementMoney(2);
		List<String> choices = Arrays.asList("y", "n");
        String playerChoice = p.choose("Do you want to put your deck into your discard pile ? (y/n)", choices, false);
        if (playerChoice == "y"){
			while (p.cardsInDraw().size() != 0){
				p.gain(p.drawCard());
				}
			while (p.cardsInHand().size() != 0){
				p.gain(p.removeCard(p.cardsInHand().get(p.cardsInHand().size()).getName()));
				}
			}
	}
}
