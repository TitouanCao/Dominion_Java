package dominion;
import java.util.*;
import dominion.card.*;
import dominion.card.common.*;
 
/**
 * Un joueur de Dominion
 */
public class Player {
    /**
     * Nom du joueur
     */
    private String name;
     
    /**
     * Nombre d'actions disponibles
     */
    private int actions;
     
    /**
     * Nombre de pièces disponibles pour acheter des cartes
     */
    private int money;
     
    /**
     * Nombre d'achats disponibles
     */
    private int buys;
     
    /**
     * Référence vers la partie en cours
     */
    private Game game;
     
    /**
     * Liste des cartes dans la main du joueur
     */
    private CardList hand;
     
    /**
     * Liste des cartes dans la défausse du joueur
     */
    private CardList discard;
     
    /**
     * Liste des cartes dans la pioche du joueur
     */
    private CardList draw;
     
    /**
     * Listes des cartes qui ont été jouées pendant le tour courant
     */
    private CardList inPlay;
     
    /**
     * Constructeur
     * 
     * Initialise les différentes piles de cartes du joueur, place 3 cartes
     * Estate et 7 cartes Copper dans la défausse du joueur puis fait piocher 5
     * cartes en main au joueur.
     * 
     * @param name: le nom du joueur
     * @param game: le jeu en cours
     * 
     * Indications: On peut utiliser la méthode {@code this.endTurn()} pour 
     * préparer la main du joueur après avoir placé les cartes dans la défausse.
     */
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
        this.hand = new CardList();
        this.discard = new CardList();
        this.draw = new CardList();
        this.inPlay = new CardList();
        Copper copperCard = new Copper();
        Estate estateCard = new Estate();
        for(int i=0; i<7 ; i++){
            this.discard.add(copperCard);
        }
        for(int i=0; i<3 ; i++){
            this.discard.add(estateCard);
            
        }
        this.discard.shuffle();
        while(this.hand.size()<5){
			this.hand.add(this.drawCard());
		}
		
    }
 
    /**
     * Getters et setters
     */
    public String getName() {
        return this.name;
    }
     
    public int getActions() {
        return this.actions;
    }
     
    public int getMoney() {
        return this.money;
    }
     
    public int getBuys() {
        return this.buys;
    }
     
    public Game getGame() {
        return this.game;
    }
    
    public CardList getInPlay() {
		return this.inPlay;
		}
     
    /**
     * Incrémente le nombre d'actions du joueur
     * 
     * @param n nombre d'actions à ajouter (ce nombre peut être négatif si l'on
     * souhaite diminuer le nombre d'actions)
     */
    public void incrementActions(int n) {
        this.actions = this.actions + n;
        if (this.actions < 0){
            this.actions = 0;
            }
    }
     
    /**
     * Incrémente le nombre de pièces du joueur
     * 
     * @param n nombre de pièces à ajouter (ce nombre peut être négatif si l'on
     * souhaite diminuer le nombre de pièces)
     */
    public void incrementMoney(int n) {
        this.money = this.money + n;
        if (this.money < 0){
            this.money = 0;
            }
    }
     
    /**
     * Incrémente le nombre d'achats disponibles du joueur
     * 
     * @param n nombre d'achats à ajouter (ce nombre peut être négatif si l'on
     * souhaite diminuer le nombre d'achats)
     */
    public void incrementBuys(int n) {
        this.buys = this.buys + n;
        if (this.buys < 0){
            this.buys = 0;
            }
    }
    
    public CardList cardsInDraw() {
        CardList cardsInDraw = new CardList(this.draw);
        return cardsInDraw;
    }
 
    /**
     * Renvoie une liste des cartes que le joueur a en main.
     * La liste renvoyée doit être une nouvelle {@code CardList} dont les 
     * éléments sont les mêmes que ceux de {@code this.hand}.
     */
    public CardList cardsInHand() {
        CardList cardsInHand = new CardList(this.hand);
        return cardsInHand;
    }
     
    /**
     * Renvoie une liste de toutes les cartes possédées par le joueur
     * (le deck complet c'est-à-dire toutes les cartes dans la main, la
     * défausse, la pioche et en jeu)
     */
    public CardList totalCards() {
        CardList totalCards = new CardList();
        totalCards.addAll(this.hand);
        totalCards.addAll(this.draw);
        totalCards.addAll(this.discard);
        totalCards.addAll(this.inPlay);
        return totalCards;
    }
     
    /**
     * Renvoie le nombre total de points de victoire du joueur
     * 
     * Ce total est calculé en ajoutant les valeurs individuelles de toutes les
     * cartes dans le deck du joueur (en utilisant la méthode
     * {@code victoryValue()}) des cartes
     */
    public int victoryPoints() {
        int n=0;
        int sum=0;
        while (n < this.totalCards().size()){
            sum = sum + this.totalCards().get(n).victoryValue(this);
            n++;
		}
        return sum;
        
    }
     
    /**
     * Renvoie une liste des autres joueurs de la partie.
     * 
     * Les adversaires sont listés dans l'ordre de jeu, c'est-à-dire que le
     * premier de la liste est celui qui joue immédiatement après le joueur,
     * puis le suivant, et ainsi de suite jusqu'au joueur qui joue immédiatement
     * avant le joueur.
     * 
     * Rmq: Cette méthode fait appel à la méthode {@code otherPlayers(Player p)}
     * de la classe {@code Game}.
     */
    public List<Player> otherPlayers() {
        return this.game.otherPlayers(this);
    }
     
    /**
     * Pioche une carte dans la pioche du joueur.
     * 
     * Si la pioche du joueur est vide, on commence par mélanger la défausse
     * et transférer toutes les cartes de la défausse dans la pioche.
     * On retire et renvoie ensuite la première carte de la pioche si elle n'est
     * pas vide (sinon la méthode ne fait rien et renvoie {@code null})
     * 
     * @return la carte piochée, {@code null} si aucune carte disponible
     */
    public Card drawCard() {
        if (this.draw.isEmpty()){
            this.discard.shuffle();
            while (this.discard.isEmpty() == false){
                this.draw.add(this.discard.get(this.discard.size()-1));
                this.discard.remove(this.discard.size()-1);
            }
        }
        if (this.draw.isEmpty() && this.discard.isEmpty()){
            return null;
        }
        return this.draw.remove(this.draw.size()-1);
    }
     
    /**
     * Renvoie une représentation de l'état du joueur sous forme d'une chaîne
     * de caractères.
     * 
     * Cette représentation comporte
     * - le nom du joueur
     * - le nombre d'actions, de pièces et d'achats du joueur
     * - le nombre de cartes dans la pioche et dans la défausse du joueur
     * - la liste des cartes en jeu du joueur
     * - la liste des cartes dans la main du joueur
     */
    public String toString() {
        String r = String.format("     -- %s --\n", this.name);
        r += String.format("Actions: %d     Money: %d     Buys: %d     Draw: %d     Discard: %d\n", this.actions, this.money, this.buys, this.draw.size(), this.discard.size()); 
        r += String.format("In play: %s\n", this.inPlay.toString());
        r += String.format("Hand: %s\n", this.hand.toString());
        return r;
    }
     
    /**
     * Renvoie la liste de toutes les cartes Trésor dans la main du joueur
     */
    public CardList getTreasureCards() {
        CardList playerTreasureCards = new CardList();
        int n = 0;
        int m=0;
        while (n < this.hand.size()){
            if (this.hand.get(n).getTypes().contains(CardType.Treasure)){
                playerTreasureCards.add(this.hand.get(n));
                m++;
            }
            n++;
        }
        if (m > 0) {
			return playerTreasureCards;
		}
        else return null;
    }
     
    /**
     * Renvoie la liste de toutes les cartes Action dans la main du joueur
     */
    public CardList getActionCards() {
        CardList playerActionCards = new CardList();
        int n = 0;
        int m = 0;
        while (n < this.hand.size()){
            if (this.hand.get(n).getTypes().contains(CardType.Action)){
                playerActionCards.add(this.hand.get(n));
                m++;
            }
        n++;
        }
        if (m > 0) {
			return playerActionCards;
		}
        else return null;
    }
     
    /**
     * Renvoie la liste de toutes les cartes Victoire dans la main du joueur
     */
    public CardList getVictoryCards() {
        CardList playerVictoryCards = new CardList();
        int n = 0;
        int m = 0;
        while (n < this.hand.size()){
            if (this.hand.get(n).getTypes().contains(CardType.Victory)){
                playerVictoryCards.add(this.hand.get(n));
                m++;
            }
        n++;
        }
        if (m > 0) {
			return playerVictoryCards;
		}
        else return null;
    }
     
    /**
     * Joue une carte de la main du joueur.
     * 
     * @param c carte à jouer
     * 
     * Cette méthode ne vérifie pas que le joueur a le droit de jouer la carte,
     * ni même que la carte se trouve effectivement dans sa main.
     * La méthode retire la carte de la main du joueur, la place dans la liste
     * {@code inPlay} et exécute la méthode {@code play(Player p)} de la carte.
     */
    public void playCard(Card c) {
		boolean moatUsed=false;
		int nbPlayerMoat=0;
		Player[] bufferPlayer=new Player[this.getGame().numberOfPlayers()-1];
        this.inPlay.add(this.hand.getCard(c.getName()));
        this.hand.remove(c.getName());
        if(c.getTypes().contains(CardType.Attack)){
			for(int i=0;i<this.otherPlayers().size();i++){
				
				for(int a=0;a<this.otherPlayers().get(i).cardsInHand().size();a++){
					if(this.otherPlayers().get(i).cardsInHand().get(a).getName().equals("Moat")){
						List<String> choices = Arrays.asList("y", "n");
						String choice = this.otherPlayers().get(i).choose("Do you want to ignore the effect of the Attack card (you have a Moat card)? (y/n)", choices, false);
						if(choice.equals("y")){
							moatUsed=true;
							bufferPlayer[nbPlayerMoat]=this.otherPlayers().get(i);
							nbPlayerMoat++;
							this.getGame().removePlayer(this.otherPlayers().get(i).getName());
						}
						
					}
				}
				
			}
		}
        this.inPlay.get(this.inPlay.size()-1).play(this);
        if(moatUsed) {
			for(int i=0;i<nbPlayerMoat;i++){
				this.getGame().addPlayer(bufferPlayer[i]);
			}
		}
    }
     
    /**
     * Joue une carte de la main du joueur.
     * 
     * @param cardName nom de la carte à jouer
     * 
     * S'il existe une carte dans la main du joueur dont le nom est égal au
     * paramètre, la carte est jouée à l'aide de la méthode 
     * {@code playCard(Card c)}. Si aucune carte ne correspond, la méthode ne
     * fait rien.
     */
    public void playCard(String cardName) {
        if(this.hand.getCard(cardName) != null){
			int nbPlayerMoat=0;
			boolean moatUsed=false;
			Player[] bufferPlayer=new Player[this.getGame().numberOfPlayers()-1];
            this.inPlay.add(this.hand.getCard(cardName));
            this.hand.remove(cardName);
            if(this.inPlay.getCard(cardName).getTypes().contains(CardType.Attack)){
				for(int i=0;i<this.otherPlayers().size();i++){
					for(int a=0;a<this.otherPlayers().get(i).cardsInHand().size();a++){
						if(this.otherPlayers().get(i).cardsInHand().get(a).getName().equals("Moat")){
							List<String> choices = Arrays.asList("y", "n");
							String choice = this.otherPlayers().get(i).choose("Do you want to ignore the effect of the Attack card (you have a Moat card)? (y/n)", choices, false);
							if(choice.equals("y")){
								moatUsed=true;
								bufferPlayer[nbPlayerMoat]=this.otherPlayers().get(i);
								nbPlayerMoat++;
								this.getGame().removePlayer(this.otherPlayers().get(i).getName());
							}
						}
					}
				}
			}
            this.inPlay.getCard(cardName).play(this);
            if(moatUsed) {
				for(int i=0;i<nbPlayerMoat;i++){
					this.getGame().addPlayer(bufferPlayer[i]);
				}
			}
        }
    }
     
    /**
     * Le joueur gagne une carte.
     * 
     * @param c carte à gagner (éventuellement {@code null})
     * 
     * Si la carte n'est pas {@code null}, elle est placée sur la défausse du
     * joueur. On suppose que la carte a correctement été retirée de son 
     * emplacement précédent au préalable.
     */
    public void gain(Card c) {
        this.discard.add(c);
    }
     
    public void addToHand(Card c){
		this.hand.add(c);
	}
	public void addToDraw(Card c){
		this.draw.add(c);
	}
        
    
     
    /**
     * Le joueur gagne une carte de la réserve
     * 
     * @param cardName nom de la carte à gagner. S'il existe une carte dans la 
     * réserve ayant ce nom, cette carte est retirée de la réserve et placée 
     * sur la défausse du joueur.
     * @return la carte qui a été ajoutée à la défausse du joueur, ou {@code 
     * null} si aucune carte n'a été prise dans la réserve.
     */
    public Card gain(String cardName) {
        if (this.game.getFromSupply(cardName) != null){
            this.discard.add(this.game.removeFromSupply(cardName));
            return this.discard.getCard(cardName);
            }
        else {
            return null;
            }
    }
     
    /**
     * Le joueur achète une carte de la réserve
     * 
     * La méthode cherche une carte dans la réserve dont le nom est égal au
     * paramètre, puis vérifie que le joueur a assez de pièces pour l'acheter 
     * et au moins un achat disponible.
     * Si le joueur peut acheter la carte, le coût de la carte est soustrait à
     * l'argent du joueur, le nombre d'achats disponibles est décrémenté de 1 
     * et la carte est gagnée par le joueur.
     * 
     * @param cardName nom de la carte à acheter
     * @return la carte qui a été gagnée ou {@code null} si l'achat n'a pas eu 
     * lieu
     */
    public Card buyCard(String cardName) {
        if (this.game.getFromSupply(cardName) != null){
            if (this.money >= this.game.getFromSupply(cardName).getCost() && this.buys >= 1){
                this.money = this.money - this.game.getFromSupply(cardName).getCost();
                this.buys = this.buys - 1;
                return this.gain(cardName);
                }
            else {
                return null;
                }
            }
        else {
            return null;
            }
    }
     
    /**
     * Attend une entrée de la part du joueur (au clavier) et renvoie le choix
     *  du joueur.
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     * la nature du choix qui est attendu
     * @param choices une liste de chaînes de caractères correspondant aux
     * choix valides attendus du joueur (la liste sera convertie en ensemble 
     * par la fonction pour éliminer les doublons, ce qui permet de compter 
     * correctement le nombre d'options disponibles)
     * @param canPass booléen indiquant si le joueur a le droit de passer sans
     * faire de choix. S'il est autorisé à passer, c'est la chaîne de
     * caractères vide ("") qui signifie qu'il désire passer.
     * 
     * @return la méthode lit l'entrée clavier jusqu'à ce qu'un choix valide
     * soit entré par l'utilisateur (un élément de {@code choices} ou
     * éventuellement la chaîne vide si l'utilisateur est autorisé à passer).
     * Lorsqu'un choix valide est obtenu, il est renvoyé.
     * 
     * Si l'ensemble {@code choices} ne comporte qu'un seul élément et que
     * {@code canPass} est faux, l'unique choix valide est automatiquement
     * renvoyé sans lire l'entrée de l'utilisateur.
     * 
     * Si l'ensemble des choix est vide, la chaîne vide ("") est 
     * automatiquement renvoyée par la méthode (indépendamment de la valeur de 
     * {@code canPass}).
     * 
     * Exemple d'utilisation pour demander à un joueur de répondre à une 
     * question :
     * <pre>
     * {@code
     * List<String> choices = Arrays.asList("y", "n");
     * String input = p.choose("Do you want to ...? (y/n)", choices, false);
     * }
     * </pre>
     */
    public String choose(String instruction, List<String> choices, boolean canPass) {
        // La liste de choix est convertie en ensemble pour éviter les doublons
        Set<String> choiceSet = new HashSet<String>();
        for (String c: choices) {
            choiceSet.add(c);
        }
        if (choiceSet.isEmpty()) {
            // Aucun choix disponible
            return "";
        } else if (choiceSet.size() == 1 && !canPass) {
            // Un seul choix possible (renvoyer cet unique élément)
            return choiceSet.iterator().next();
        } else {
            String input;
            // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
            while (true) { 
                System.out.print("\n\n");
                // affiche l'état du jeu
                System.out.print(this.game);
                System.out.print("\n");
                // affiche l'état du joueur
                System.out.print(this);
                System.out.print("\n");
                // affiche l'instruction
                System.out.println(">>> " + instruction);
                System.out.print("> ");
                // lit l'entrée de l'utilisateur au clavier
                input = this.game.readLine();
                if (choiceSet.contains(input) || (canPass && input.equals(""))){
                    // si une réponse valide est obtenue, elle est renvoyée
                    return input;
                }
            }
        }
    }
     
    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * Dans cette méthode, la liste des choix est donnée sous la forme d'une 
     * liste de cartes et le joueur doit choisir le nom d'une de ces cartes.
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     * la nature du choix qui est attendu
     * @param choices liste de cartes parmi lesquelles il faut en choisir une
     * parmi lesquelles l'utilisateur doit choisir
     * @param canPass booléen indiquant si le joueur a le droit de passer sans
     * faire de choix. S'il est autorisé à passer, c'est la chaîne de
     * caractères vide ("") qui signifie qu'il désire passer.
     * 
     * La méthode commence par construire une liste de tous les noms des cartes 
     * dans {@code choices} puis appelle la méthode précédente pour faire 
     * choisir un nom parmi cette liste à l'utilisateur.
     * 
     * Exemple d'utilisation pour faire choisir le nom d'une carte Action de sa
     * main à un joueur (dans cet exemple le joueur n'a pas le droit de passer 
     * s'il a au moins une carte Action en main, mais la méthode peut quand 
     * même renvoyer {@code ""} s'il n'a aucune carte Action en main) :
     * <pre>
     * {@code
     * CardList choices = new CardList();
     * for (Card c: p.cardsInHand()) {
     *   if (c.getTypes().contains(CardType.Action)) {
     *     choices.add(c);
     *   }
     * }
     * String input = p.chooseCard("Choose an Action card.", choices, false);
     * </pre>
     */
    public String chooseCard(String instruction, CardList choices, boolean canPass) {
        // liste de noms de cartes
        List<String> stringChoices = new ArrayList<String>();
        for (Card c: choices) {
            // tous les noms sont ajoutés à l'ensemble
            stringChoices.add(c.getName());
        }
        // appel de la méthode précédente en passant l'ensemble de noms
        return this.choose(instruction, stringChoices, canPass);
    }
    
    public Card removeCard(String cardName) {
			return this.hand.remove(cardName);
		}
     
    public Card removeFromInPlay(String cardName){
			return this.inPlay.remove(cardName);
		}
     
    /**
     * Démarre le tour du joueur
     * 
     * Les compteurs d'actions et achats sont mis à 1
     */
    public void startTurn() {
        this.buys = 1;
        this.actions = 1;
    }
     
    /**
     * Termine le tour du joueur
     * 
     * - Les compteurs d'actions, argent et achats du joueur sont remis à 0
     * - Les cartes en main et en jeu sont défaussées
     * - Le joueur pioche 5 cartes en main
     */
    public void endTurn() {
        this.buys = 0;
        this.actions = 0;
        this.money = 0;
        while (!this.hand.isEmpty()) {
            this.discard.add(this.hand.get(this.hand.size()-1));
            this.hand.remove(this.hand.get(this.hand.size()-1).getName());
        }
        while (!this.inPlay.isEmpty()) {
            this.discard.add(this.inPlay.get(this.inPlay.size()-1));
            this.inPlay.remove(this.inPlay.get(this.inPlay.size()-1).getName());
        }
        for (int n = 0; n < 5; n++){
            this.addToHand(this.drawCard());
            }
    }
     
    /**
     * Exécute le tour d'un joueur
     * 
     * Cette méthode exécute successivement les 5 phases du tour d'un joueur:
     * 
     * 1. (Préparation) la méthode {@code startTurn()} est appelée
     * 
     * 2. (Action) Tant que le joueur a des actions disponibles, on lui demande 
     * de choisir le nom d'une carte Action de sa main à jouer. Il peut passer à
     * tout moment à la phase suivante (soit de manière forcée s'il n'a plus de 
     * carte Action en main soit volontairement en entrant la chaîne vide). 
     * Lorsqu'il joue une carte Action, la fonction décrémente son nombre 
     * d'actions puis joue la carte.
     * 
     * 3. (Trésor) Le joueur joue toutes les cartes Trésor de sa main 
     * automatiquement (dans le jeu de base il n'y a aucune raison de ne pas 
     * jouer tous les trésors automatiquement).
     * 
     * 4. (Achat) Tant que le joueur a au moins un achat disponible, on lui 
     * demande de choisir le nom d'une carte de la réserve qu'il veut acheter. 
     * Il ne peut acheter que des cartes dont le prix est inférieur à l'argent 
     * dont il dispose. Le joueur peut passer (et terminer son tour) à tout 
     * moment pendant cette phase.
     * 
     * 5. (Fin) La méthode {@code endTurn()} est appelée pour terminer le tour 
     * du joueur
     */
    public void playTurn() {
         
        //Démarrage du tour
        this.startTurn();
         
        //Phase d'action
        String choiceActions = "";
        if (this.getActionCards() != null){
			choiceActions = this.chooseCard("Action phase - choose an Action card to play or skip with ENITER", this.hand, true);
		}
        while (this.actions > 0 & !choiceActions.equals("")){
			System.out.println("\n\n\n Le joueur "+this.name+" a joué une carte Action");
            this.playCard(this.hand.getCard(choiceActions));
            this.incrementActions(-1);
            if (this.actions > 0){
                choiceActions = this.chooseCard("Action phase - choose an Action card to play or skip with ENITER", this.hand, true);
                }
            }
             
        //Phase de trésor
        while (this.getTreasureCards() != null){
            this.playCard(this.getTreasureCards().get(this.getTreasureCards().size()-1));
            }
         
        //Phase d'achat
        
        String choiceBuys = this.chooseCard("Buy phase - choose a card to buy or skip with ENTER", this.getGame().availableSupplyCards(), true);
        while (this.buys > 0 &&  !choiceBuys.equals("")){
            this.buyCard(choiceBuys);
            this.incrementBuys(-1);
            if (this.buys > 0){
                choiceBuys = this.chooseCard("Buy phase - choose a card to buy or skip with ENTER", this.getGame().availableSupplyCards(), true);
                }
            }
        //Fin du tour
        this.endTurn();
    }
}
