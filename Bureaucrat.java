package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Bureaucrate (Bureaucrat)
 * 
 * Recevez une carte Argent; placez-la sur votre deck.
 * Tous vos adversaires dévoilent une carte Victoire et la placent sur leur deck (sinon ils dévoilent leur main afin que vous puissiez voir qu'ils n'ont pas de cartes Victoire).
 */
public class Bureaucrat extends AttackCard {
	
	public Bureaucrat(){
		super("Bureaucrat",4);
	}
	
	public void play(Player p){
		p.addToDraw(p.getGame().removeFromSupply("Silver"));
		for(int i=0;i<p.otherPlayers().size();i++){
			if(p.otherPlayers().get(i).getVictoryCards()!=null){
				String choice=p.otherPlayers().get(i).chooseCard("Choose a card, put it on top of your deck : "+p.otherPlayers().get(i).getVictoryCards().toString(),p.otherPlayers().get(i).getVictoryCards(),false);
				System.out.println("Revealed Card : "+p.otherPlayers().get(i).cardsInHand().getCard(choice).toString());
				p.otherPlayers().get(i).addToDraw(p.otherPlayers().get(i).removeCard(choice));
			}
			else{
				System.out.println(p.otherPlayers().get(i).cardsInHand().toString());
			}
		}
	}
	
}
