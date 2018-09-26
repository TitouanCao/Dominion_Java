package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Mine
 * 
 * Écartez une carte Trésor de votre main. Recevez une carte Trésor coûtant jusqu'à 3 Pièces de plus ; ajoutez cette carte à votre main.
 */
public class Mine extends ActionCard {
	public Mine(){
		super("Mine",5);
	}
	
	public void play(Player p){
		if(!p.getTreasureCards().isEmpty()){
			String choice = p.chooseCard("Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 more, add it to your hand",p.getTreasureCards(),false);
			if(choice.equals("Copper")){
				p.addToHand(p.getGame().getFromSupply("Silver"));
				p.getGame().removeFromSupply("Silver");
			}
			if(choice.equals("Silver")){
				p.addToHand(p.getGame().getFromSupply("Gold"));
				p.getGame().removeFromSupply("Gold");
			}
			if(choice.equals("Gold")){
				p.addToHand(p.getGame().getFromSupply("Gold"));
				p.getGame().removeFromSupply("Gold");
			}
			p.removeCard(choice);
		}
	}
}
