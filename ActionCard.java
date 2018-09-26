package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Action
 */
public abstract class ActionCard extends Card {
	public ActionCard(String name, int cost) {
		super(name,cost);
		types.add(CardType.values()[1]);
	}
}
