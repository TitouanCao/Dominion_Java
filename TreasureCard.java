package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Trésor
 */
public abstract class TreasureCard extends Card {
	
	public TreasureCard(String name,int cost){
		super(name,cost);
		types.add(CardType.values()[0]);
	}
}
