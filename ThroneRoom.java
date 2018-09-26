package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Salle du trône (Throne Room)
 * 
 * Choisissez 1 carte Action de votre main.
 * Jouez-la deux fois.
 */
public class ThroneRoom extends ActionCard {
	public ThroneRoom(){
		super("Throne Room",4);
	}
	
	public void play(Player p){
		CardList cardsInHandBis=p.cardsInHand();
		String cardName=p.chooseCard("Choose an Action card in your hand. Play it twice",cardsInHandBis,true);
		p.playCard(cardName);
		p.addToHand(p.removeFromInPlay(cardName));
		p.playCard(cardName);
	}
}
