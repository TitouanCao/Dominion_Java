package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Voleur (Thief)
 * 
 * Tous vos adversaires dévoilent les 2 premières cartes de leur deck. S'ils dévoilent des cartes Trésor, ils en écartent 1 de votre choix. Parmi ces cartes Trésor écartées, recevez celles de votre choix. Les autres cartes dévoilées sont défaussées.
 */
public class Thief extends AttackCard {
	public Thief(){
		super("Thief",4);
	}
	
	public void play(Player p){
		CardList stolenCards=new CardList();
		for(int i=0;i<p.otherPlayers().size();i++){
			int nbTreasurePerPlayer=0;
			CardList bufferCards=new CardList();
			bufferCards.add(p.otherPlayers().get(i).drawCard());
			bufferCards.add(p.otherPlayers().get(i).drawCard());
			if(bufferCards.get(0).getTypes().contains(CardType.Treasure)){
				if(bufferCards.get(1).getTypes().contains(CardType.Treasure)){
					String choice=p.chooseCard("Choose the Treasure card you want between "+bufferCards.get(0).getName()+" and "+bufferCards.get(1).getName(),bufferCards,false);
					stolenCards.add(bufferCards.remove(choice));
					p.otherPlayers().get(i).gain(bufferCards.get(0));
					
				}
				else{
					stolenCards.add(bufferCards.get(0));
					p.otherPlayers().get(i).gain(bufferCards.get(1));
				}
	
			}
			else{
				p.otherPlayers().get(i).gain(bufferCards.get(0));
				if(bufferCards.get(1).getTypes().contains(CardType.Treasure)){
					stolenCards.add(bufferCards.get(1));
				}
				else{
					p.otherPlayers().get(i).gain(bufferCards.get(1));
				}
			}
		}
		if(!stolenCards.isEmpty()) {
			int a=0;
			String choice2=p.chooseCard("Choose the Treasure cards you want to gain among :\n"+stolenCards.toString()+"\nor skip with ENTER",stolenCards,true);
			while(a<stolenCards.size() & !choice2.equals("")){
				a++;
				p.gain(stolenCards.getCard(choice2));
				if(a<stolenCards.size()) choice2=p.chooseCard("Choose the Treasure cards you want to gain among :\n"+stolenCards.toString()+"\nor skip with ENTER",stolenCards,true);
			}
			if(!stolenCards.isEmpty()){
				while(!stolenCards.isEmpty()){
					p.getGame().mooveToTrash(p,stolenCards.remove(stolenCards.get(0).getName()));
				}
			}
		}
		
	}
}
