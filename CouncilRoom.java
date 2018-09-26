package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chambre du conseil (Council Room)
 * 
 * +4 Cartes.
 * +1 Achat.
 * Tous vos adversaires piochent 1 carte.
 */
public class CouncilRoom extends ActionCard {
	public CouncilRoom(){
		super("Council Room",5);
	}
	
	public void play(Player p){
		for (int i = 0; i < 4; i++){
			p.addToHand(p.drawCard());
			}
		p.incrementBuys(1);
		for (int j = 0; j < (p.getGame().numberOfPlayers() - 1); j++) {
			p.getGame().otherPlayers(p).get(j).addToHand(p.getGame().otherPlayers(p).get(j).drawCard());
			}
	}
}
