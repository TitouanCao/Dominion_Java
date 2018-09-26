package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;



/**
 * Carte Atelier (Workshop)
 * 
 * Recevez une carte coûtant jusqu'à 4 Pièces.
 */
public class Workshop extends ActionCard {
	public Workshop(){
		super("Workshop",3);
	}
	
	public void play(Player p){
		String choice=p.chooseCard("Gain a card up to 4",p.getGame().getFromSupply(0,4),false);
		p.gain(p.getGame().removeFromSupply(choice));
	}
			
}
	
