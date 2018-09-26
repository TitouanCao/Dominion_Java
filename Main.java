import java.util.*;
import dominion.*;
import dominion.card.*;
import dominion.card.base.*;

/**
 * Classe pour l'exécution d'une partie de Dominion
 */
class Main {
	public static void main(String[] args) {
		// Noms des joueurs de la partie
		// (le nombre total de joueurs correspond au nombre de noms dans le 
		// tableau)
		String[] playerNames = new String[]{"Jules", "Aloïs","Charlotte"};
		// Prépare les piles "royaume" de la réserve (hors cartes communes)
		List<CardList> kingdomStacks = new ArrayList<CardList>();
		CardList stack=new CardList();
		// Ajouter un bloc pour chaque carte royaume à utiliser
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Village());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Spy());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Thief());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Witch());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Smithy());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Bureaucrat());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Mine());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Gardens());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Militia());
		}
		kingdomStacks.add(stack);
		
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Cellar());
		}
		kingdomStacks.add(stack);
		// Instancie et exécute une partie
		Game g = new Game(playerNames, kingdomStacks);
		g.run();
	}
}
