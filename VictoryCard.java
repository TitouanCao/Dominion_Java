package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Victoire
 */
public abstract class VictoryCard extends Card {

	public VictoryCard(String name, int cost) {
		super(name,cost);
		types.add(CardType.values()[2]);
	}
}
