package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Attaque
 * Rmq: les cartes Attaque sont toutes des cartes Action
 */
public abstract class AttackCard extends ActionCard {
	public AttackCard(String name, int cost) {
		super(name,cost);
		types.add(CardType.values()[5]);
	}
}
