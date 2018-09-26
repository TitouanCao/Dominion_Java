package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Jardins (Gardens)
 * 
 * Vaut 1VP pour chaque 10 cartes dans votre deck (arrondi à l'unité inférieure).
 */
public class Gardens extends VictoryCard {
	public Gardens(){
		super("Gardens",4);
	}
	
	public void play(Player p){
		
	}
	
	public int victoryValue(Player p){
		int value = 0;
		value = (p.totalCards().size()) / 10;
		return value;
	}
}
