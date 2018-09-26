package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Espion (Spy)
 * 
 * +1 Carte.
 * +1 Action.
 * Tous les joueurs (vous aussi) dévoilent la première carte de leur deck. Vous décidez ensuite si chaque carte dévoilée est défaussée ou replacée sur son deck.
 */
public class Spy extends AttackCard {
	public Spy(){
		super("Spy",4);
	}
	
	public void play(Player p){
		p.addToHand(p.drawCard());
		p.incrementActions(1);
		Card[] spiedCards = new Card[1];
		List<Player> allPlayers =new ArrayList<Player>();
		allPlayers.add(p);
		allPlayers.addAll(p.otherPlayers());
		for(int i=0;i<allPlayers.size();i++){
			System.out.println("\n\nPlayer : "+allPlayers.get(i).getName()+"\n\n");
			spiedCards[0]=allPlayers.get(i).drawCard();
			System.out.println("The card of "+allPlayers.get(i).getName()+" is "+spiedCards[0].getName());
			List<String> listOfChoices=Arrays.asList("y", "n");
			String choice=p.choose("Choose to discard it(y) or to put it back(n)",listOfChoices,false);
			if(choice.equals("y")) {
				allPlayers.get(i).gain(spiedCards[0]);
			}
			else{
				if(choice.equals("n")) allPlayers.get(i).addToDraw(spiedCards[0]);
				else System.out.println("\nError in card Spy, choice didn't work\n");
			}
		}
	}
		
		
}
