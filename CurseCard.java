package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Malédiction
 */
public abstract class CurseCard extends Card {
	
	public CurseCard(String name, int cost) {
		super(name,cost);
		types.add(CardType.values()[3]);
	}
}
