package dominion;
import java.util.*;
import dominion.card.*;
import dominion.card.common.*;

/**
 * Class représentant une partie de Dominion
 */
public class Game {
	/**
	 * Tableau contenant les joueurs de la partie
	 */
	private Player[] players;
	
	/**
	 * Index du joueur dont c'est actuellement le tour
	 */
	private int currentPlayerIndex;
	
	/**
	 * Liste des piles dans la réserve du jeu.
	 * 
	 * On suppose ici que toutes les listes contiennent des copies de la même
	 * carte.
	 * Ces piles peuvent être vides en cours de partie si toutes les cartes de 
	 * la pile ont été achetées ou gagnées par les joueurs.
	 */
	private List<CardList> supplyStacks;
	
	/**
	 * Liste des cartes qui ont été écartées (trash)
	 */
	private CardList trashedCards;
	
	/**
	 * Scanner permettant de lire les entrées au clavier
	 */
	private Scanner scanner;
	
	/**
	 * Constructeur
	 * 
	 * @param playerNames liste des noms des joueurs qui participent à la 
	 * partie. Le constructeur doit créer les objets correspondant aux joueurs
	 * @param kingdomStacks liste de piles de réserve à utiliser correspondant 
	 * aux cartes "royaume" à utiliser dans la partie, auxquelles le 
	 * constructeur doit ajouter les piles "communes":
	 * - 60 Copper
	 * - 40 Silver
	 * - 30 Gold
	 * - 8 (si 2 joueurs) ou 12 (si 3 ou 4 joueurs) Estate, Duchy et Province 	 
	 * - 10 * (n-1) Curse où n est le nombre de joueurs dans la partie
	 */
	public Game(String[] playerNames, List<CardList> kingdomStacks) {
		trashedCards=new CardList();
		scanner = new Scanner(System.in);
		int nbPlayers=playerNames.length;
		supplyStacks = new ArrayList<CardList>();
		//--------Ajout des cartes Trésor----------------------------
		Copper cuivre=new Copper();
		CardList CopperCardList=new CardList();
		for(int i=0;i<60;i++){
			CopperCardList.add(cuivre);
		}
		this.supplyStacks.add(CopperCardList);
		
		
		Silver argent=new Silver();
		CardList SilverCardList=new CardList();
		for(int i=0;i<40;i++){
			SilverCardList.add(argent);
		}
		this.supplyStacks.add(SilverCardList);
		
		Gold or=new Gold();
		CardList GoldCardList=new CardList();
		for(int i=0;i<30;i++){
			GoldCardList.add(or);
		}
		this.supplyStacks.add(GoldCardList);
		//-------Ajout des cartes royaumes (kingdomStacks)-----------
		for(int i=0;i<kingdomStacks.size();i++){
			this.supplyStacks.add(kingdomStacks.get(i));
		}
		
		//------Ajout des cartes Estate,Duchy,Province et Curse------
		int nbVictoryCard;
		if(nbPlayers==2) nbVictoryCard=8;
		else nbVictoryCard=12;
		
		Estate estateCard=new Estate();
		CardList EstateCardList=new CardList();
		
		Province provinceCard=new Province();
		CardList ProvinceCardList=new CardList();
		
		Duchy duchyCard=new Duchy();
		CardList DuchyCardList=new CardList();
		
		Curse curseCard=new Curse();
		CardList CurseCardList=new CardList();
		
		for(int a=0;a<nbVictoryCard;a++){
			EstateCardList.add(estateCard);
			ProvinceCardList.add(provinceCard);
			DuchyCardList.add(duchyCard);	
		}
		for(int a=0;a<(10*(nbPlayers-1));a++){
			CurseCardList.add(curseCard);
		}
		
		this.supplyStacks.add(EstateCardList);
		this.supplyStacks.add(ProvinceCardList);
		this.supplyStacks.add(DuchyCardList);
		this.supplyStacks.add(CurseCardList);
		/*int i2=0;
		while(i2<this.supplyStacks.size()){
			System.out.println(this.supplyStacks.get(i2).toString()+" end\n");
			i2++;
		}*/
		//---------Ajout des Joueurs---------------------------------
		this.players= new Player[nbPlayers];
		for(int a=0;a<nbPlayers;a++){
			Player p=new Player(playerNames[a],this);
			this.players[a]=p;
		}
		
	}
	
	/**
	 * Renvoie le joueur correspondant à l'indice passé en argument
	 * On suppose {@code index} est un indice valide du tableau 
	 * {@code this.players}
	 * 
	 * @param index indice dans le tableau des joueurs du joueur à renvoyer
	 */
	public Player getPlayer(int index) {
		return this.players[index];
	}
	
	public void removePlayer(String playerName){
		for(int i=0;i<this.players.length;i++){
			if(this.players[i].getName().equals(playerName)) this.players[i]=null;
		}
	}
	
	public void addPlayer(Player p){
		boolean empty=false;
		for(int i=0;i<this.players.length;i++){
			if(this.players[i]==null) {
				this.players[i]=p;
				empty=true;
			}
		}
		if(empty==false) System.out.println("\nError : max player number reached\n");
	}
	
	/**
	 * Renvoie le nombre de joueurs participant à la partie
	 */
	public int numberOfPlayers() {
		return this.players.length;
	}
	
	/**
	 * Renvoie l'indice du joueur passé en argument dans le tableau des 
	 * joueurs, ou -1 si le joueur n'est pas dans le tableau.
	 */
	private int indexOfPlayer(Player p) {
		int i=0;
		while(this.players[i]!=p & i<this.players.length){
			i++;
			if(i>=this.players.length) i=-1;
		}
		return i;
		
	}
	
	/**
	 * Renvoie la liste des adversaires du joueur passé en argument, dans 
	 * l'ordre dans lequel ils apparaissent à partir du joueur {@code p}.
	 * 
	 * @param p joueur dont on veut renvoyer la liste des adversaires. On 
	 * suppose que {@code p} est bien dans le tableau des joueurs.
	 * @return un {@code ArrayList} contenant les autres joueurs de la partie 
	 * en commençant par celui qui se trouve juste après {@code p} et en 
	 * terminant par celui qui se trouve juste avant (le tableau est considéré 
	 * comme cyclique c'est-à-dire qu'après le premier élément on revient au 
	 * premier).
	 */
	public List<Player> otherPlayers(Player p) {
		List<Player> otherPlayersList=new ArrayList<Player>();
		int start=-1;
		int nbPlayers=this.players.length;
		for(int i=0;i<nbPlayers;i++){
			if(this.players[i]==p) start=i+1;
		}
		if(start==-1) System.out.println("\nError, player not found \n");
		for(int i=start;i<nbPlayers;i++){
			if(this.players[i]!=null) otherPlayersList.add(this.players[i]);
		}
		for(int i=0;i<start-1;i++){
			if(this.players[i]!=null) otherPlayersList.add(this.players[i]);
		}
		return otherPlayersList;
	}
	
	/**
	 * Renvoie la liste des cartes qui sont disponibles à l'achat dans la 
	 * réserve.
	 * 
	 * @return une liste de cartes contenant la première carte de chaque pile 
	 * non-vide de la réserve (cartes royaume et cartes communes)
	 */
	public CardList availableSupplyCards() {
		CardList availableSupplyCardsList= new CardList();
		for(int i=0;i<this.supplyStacks.size();i++){
			if(!this.supplyStacks.get(i).isEmpty()) availableSupplyCardsList.add(this.supplyStacks.get(i).get(0));
			if(i>=this.supplyStacks.size()) i=-1;
		}
		return availableSupplyCardsList;
	}
	
	/**
	 * Renvoie une représentation de l'état de la partie sous forme d'une chaîne
	 * de caractères.
	 * 
	 * Cette représentation comporte
	 * - le nom du joueur dont c'est le tour
	 * - la liste des piles de la réserve en indiquant pour chacune :
	 *   - le nom de la carte
	 *   - le nombre de copies disponibles
	 *   - le prix de la carte
	 *   si la pile n'est pas vide, ou "Empty stack" si la pile est vide
	 */
	public String toString() {
		Player currentPlayer = this.players[this.currentPlayerIndex];
		String r = String.format("     -- %s's Turn --\n", currentPlayer.getName());
		for (List<Card> stack: this.supplyStacks) {
			if (stack.isEmpty()) {
				r += "[Empty stack]   ";
			} else {
				Card c = stack.get(0);
				r += String.format("%s x%d(%d)   ", c.getName(), stack.size(), c.getCost());
			}
		}
		r += "\n";
		return r;
	}
	
	/**
	 * Renvoie une carte de la réserve dont le nom est passé en argument.
	 * 
	 * @param cardName nom de la carte à trouver dans la réserve
	 * @return la carte trouvée dans la réserve ou {@code null} si aucune carte 
	 * ne correspond
	 */
	public Card getFromSupply(String cardName) {
		int i=0;
		boolean cardFound=false;
		while(cardFound==false & i<this.supplyStacks.size()){
			if(this.supplyStacks.get(i).getCard(cardName)==null) i++;
			else cardFound=true;
		}
		if(cardFound==false) return null;
		else return this.supplyStacks.get(i).getCard(cardName);
		
	}
	
	public CardList getFromSupply(int costOfCard) {
		CardList costCardList=new CardList();
		int i=0;
		int a=0;
		boolean cardFound=false;
		while(i<this.supplyStacks.size()){
			a=0;
			while(a<this.supplyStacks.get(i).size()){
				if(this.supplyStacks.get(i).getCard(a).getCost()==costOfCard) costCardList.add(this.supplyStacks.get(i).getCard(a));
				a++;
			}
			i++;
		}
		if(costCardList.isEmpty()) return null;
		else return costCardList;
		
	}
	
	public CardList getFromSupply(int costOfCardMin, int costOfCardMax) {
		CardList costCardList=new CardList();
		int i=0;
		int a=0;
		boolean cardFound=false;
		while(i<this.supplyStacks.size()){
			a=0;
			while(a<this.supplyStacks.get(i).size()){
				if(this.supplyStacks.get(i).getCard(a).getCost()>=costOfCardMin & this.supplyStacks.get(i).getCard(a).getCost()<=costOfCardMax) costCardList.add(this.supplyStacks.get(i).getCard(a));
				a++;
			}
			i++;
		}
		if(costCardList.isEmpty()) return null;
		else return costCardList;
		
	}
	
	/**
	 * Retire et renvoie une carte de la réserve
	 * 
	 * @param cardName nom de la carte à retirer de la réserve
	 * @return la carte retirée de la réserve ou {@code null} si aucune carte
	 * ne correspond au nom passé en argument
	 */
	public Card removeFromSupply(String cardName) {
		int i=0;
		boolean cardFound=false;
		while(cardFound==false & i<this.supplyStacks.size()){
			if(this.supplyStacks.get(i).getCard(cardName)==null) i++;
			else cardFound=true;
		}
		if(cardFound==false) return null;
		else {
			CardList cardTampon=new CardList();
			cardTampon.add(this.supplyStacks.get(i).getCard(cardName));
			this.supplyStacks.get(i).remove(cardName);
			return cardTampon.get(0);
		}
	}
	/**
	 * Ajoute une carte a la trash
	 */
	public void mooveToTrash(Player p,Card c){
		this.trashedCards.add(c);
	}
	
	/**
	 * Teste si la partie est terminée
	 * 
	 * @return un booléen indiquant si la partie est terminée, c'est-à-dire si
	 * au moins l'unedes deux conditions de fin suivantes est vraie
	 *  - 3 piles ou plus de la réserve sont vides
	 *  - la pile de Provinces de la réserve est vide
	 * (on suppose que toute partie contient une pile de Provinces, et donc si 
	 * aucune des piles non-vides de la réserve n'est une pile de Provinces, 
	 * c'est que la partie est terminée)
	 */
	public boolean isFinished() {
		int i=0;
		boolean found=false;
		while(i>=0 & found==false){
			if(!this.supplyStacks.get(i).isEmpty()){
				if(this.supplyStacks.get(i).getCard(0).getName().equals("Province")) found=true;
			}
			i++;
			if(i>=this.supplyStacks.size()) i=-1;
		}
		if(found==false) return true;
		int emptyStackCounter=0;
		i=0;
		while(emptyStackCounter<3 & i<this.supplyStacks.size()){
			if(this.supplyStacks.get(i).isEmpty()) emptyStackCounter++;
			i++;
		}
		if(emptyStackCounter>=3) return true;
		else return false;
	}
	
	/**
	 * Boucle d'exécution d'une partie.
	 * 
	 * Cette méthode exécute les tours des joueurs jusqu'à ce que la partie soit
	 * terminée. Lorsque la partie se termine, la méthode affiche le score 
	 * final et les cartes possédées par chacun des joueurs.
	 */
	public void run() {
		while (! this.isFinished()) {
			// joue le tour du joueur courant
			this.players[this.currentPlayerIndex].playTurn();
			// passe au joueur suivant
			this.currentPlayerIndex += 1;
			if (this.currentPlayerIndex >= this.players.length) {
				this.currentPlayerIndex = 0;
			}
		}
		System.out.println("Game over.");
		// Affiche le score et les cartes de chaque joueur
		for (int i = 0; i < this.players.length; i++) {
			Player p = this.players[i];
			System.out.println(String.format("%s: %d Points.\n%s\n", p.getName(), p.victoryPoints(), p.totalCards().toString()));
		}
	}
	
	/**
	 * Lit une ligne de l'entrée standard
	 * 
	 * C'est cette méthode qui doit être appelée à chaque fois qu'on veut lire
	 * l'entrée clavier de l'utilisateur (par exemple dans Player.choose), ce
	 * qui permet de n'avoir qu'un seul Scanner pour tout le programme
	 * 
	 * @return une chaîne de caractères correspondant à la ligne suivante de
	 * l'entrée standard (sans le retour à la ligne final)
	 */
	public String readLine() {
		return this.scanner.nextLine();
	}
	
}
