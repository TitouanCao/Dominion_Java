package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Sorcière (Witch)
 * 
 * +2 Cartes.
 * Tous vos adversaires recoivent une carte Curse.
 */
public class Witch extends AttackCard {
	public Witch(){
		super("Witch",5);
	}
	
	public void play(Player p){
		p.addToHand(p.drawCard());
		p.addToHand(p.drawCard());
		List<Player> otherPlayers=p.otherPlayers();
		for(int i=0;i<otherPlayers.size();i++){
			otherPlayers.get(i).gain(p.getGame().removeFromSupply("Curse"));
		}
	}
}
