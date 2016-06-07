import java.util.*;
import javafx.animation.FadeTransition;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// TODO: Auto-generated Javadoc
/**
 * The Class Game.
 */
public class Game extends Application implements IGame {
	
	/** The window. */
	private Stage window; 
	
	/** The scene 1, scene 2, scene 3 and scene 4. */
	private Scene scene1, scene2, scene3, scene4;
	
	/**  Notify about special card activity and game activity,. */
	private Label GlobAnnounce; 
	
	/**  The third scene. */
	private BorderPane layout3 = new BorderPane();
	
	/** Store and display player's hand card. */
	private HBox playerCard = new HBox(); 
	
	/** Display the current top pile card. */
	private ImageView pileCard = new ImageView(); 
	
	/** Display the current player's number.  */
	private ImageView playerCount = new ImageView(); 
	
	/**  Default suit(4) or nominated suit(not 4). */
	private int suitNom = 4; 
	
	/** The player's turn's index number. */
	private int indexNo = 0; 
	
	/** The number of player. */
	private static int playerlimit; 
	
	/** The current direction. */
	private boolean clockwise = true; 
	
	/** The skip turn count. */
	private int skipTurn = 0; //
	
	/** The player list. */
	private List<Player> playerList = new ArrayList<Player>();
	
	/** The stock. */
	private List<Card> stock = new ArrayList<Card>();
	
	/** The pile. */
	private List<Card> pile = new ArrayList<Card>(); 
	
	/** The Global announcement. */
	VBox GlobAnn = new VBox(); 
	/**
	 * Instantiates a new game.
	 */
	public Game(){}
	
	/* (non-Javadoc)
	 * @see IGame#reverse()
	 * Reverse the direction of players' turn
	 */
	public void reverse(){
		if(clockwise) clockwise = false;
		else clockwise = true;
	}
	
	/* (non-Javadoc)
	 * @see IGame#skipturn()
	 * Skip the next player
	 */
	public void skipturn(){
		nextPlayer();
	}
	
	/* (non-Javadoc)
	 * @see IGame#oneMoreTurn()
	 */
	public void oneMoreTurn() {
		if(clockwise){
			if(indexNo == 0) indexNo = playerlimit - 1;
			else indexNo--;
		}
		else{
			if(indexNo == playerlimit - 1 ) indexNo = 0;
			else indexNo++;
		}
	}
	
	/* (non-Javadoc)
	 * @see IGame#showMessage(int)
	 * Display activity
	 */
	public void showMessage(int choice){
		
		if(choice == 0){ //A queen is drawn
			GlobAnnounce = new Label("PLAYER " + (indexNo+1) + " SKIPS THE TURN");
		}
		else if(choice == 1){ //An Eight is drawn
			GlobAnnounce = new Label("DIRECTION REVERSED");
		}
		else if(choice == 2){
			GlobAnnounce = new Label("PLAYER " + (indexNo+1) + " HAS ONE MORE TURN");
		}
		GlobAnnounce.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		GlobAnnounce.setTextFill(Color.web("#DAA520"));
		GlobAnn.getChildren().add(GlobAnnounce);
		GlobAnn.setMinSize(0, 0);
		GlobAnn.setAlignment(Pos.CENTER);
		FadeTransition fader = new FadeTransition(Duration.millis(1500), GlobAnnounce );
		fader.setFromValue(1.0);
		fader.setToValue(0.5);
		fader.play();
		fader.setOnFinished(e-> {
			GlobAnn.getChildren().clear();
			layout3.getChildren().remove(GlobAnn);
		});
		layout3.setCenter(GlobAnn);
	}

	/**
	 * Removes the hand card and put in the pile.
	 *
	 * @param pos the pos
	 */
	protected void removeHandCard(int pos){
		pile.add(0,playerList.get(indexNo).cardList.remove(pos));	
	}
	
	
	/**
	 * Update indexNo for the next player's turn.
	 */
	protected void nextPlayer(){
		if(clockwise){
			indexNo = (indexNo + 1) % playerlimit;
		}
		else{
			if(indexNo == 0 )
				indexNo = playerlimit - 1;
			else indexNo--;
		}
	}
	
	
	/**
	 * Shuffle.
	 *
	 * @param stock the stock
	 * @param pile the pile
	 */
	protected void shuffle(List<Card> stock, List<Card> pile){
		Card tempPile = null;
		if(!pile.isEmpty()) {
			tempPile = (pile.remove(0)); 
		}
		stock.addAll(pile);
		pile.clear(); 
		if(tempPile != null){
		pile.add(tempPile); 
		}
		Collections.shuffle(stock); 
		if(pile.isEmpty()){
			pile.add(stock.remove(0));
			}
	}
	
	/**
	 * Creates the deck.
	 *
	 * @param stock the stock
	 */
	protected void createDeck(List<Card> stock){
		for(int i = 0 ; i < 52 ; i++){
			if((i%13)+1 == 1) stock.add(new Ace(i/13, 1, this));
			else if((i%13)+1 == 12)	stock.add(new Queen(i/13, 12, this));
			else if((i%13)+1 == 8) stock.add(new Eight(i/13, 8, this));
			else if((i%13)+1 == 11) stock.add(new Jack(i/13, 11, this));
			else stock.add(new Card(i/13, (i%13)+1, this));
		}
	} 	
	
	/**
	 * Create player instances, distribute card and sort card.
	 *
	 * @param noOfPlayer the no of player
	 */
	protected void distributeCard(int noOfPlayer){
		for(int i =  0 ; i < noOfPlayer; i++){
			playerList.add(new Player());
			for(int j = 0 ; j < 5 ; j++){
				playerList.get(i).addCard(stock.remove(0));
			}
			Collections.sort(playerList.get(i).cardList);
		}
	}
	
	/* (non-Javadoc)
	 * @see IGame#passCard(Ace)
	 * set temporary suit after player nominate a particular suit
	 */

	public void passCard(Ace card){
		playerCard.getChildren().clear();
		HBox SuitNom = new HBox(); 
		Player curplayer = playerList.get(indexNo);
		for(int i = 0  ; i < curplayer.cardList.size() ; i++){
			ImageView handCard = new ImageView();
			ImageView overlay = new ImageView();
			overlay.setImage(new Image("overlay.png"));
			handCard.setImage(new Image(String.format("%d%d.png",curplayer.cardList.get(i).getSuit(), curplayer.cardList.get(i).getRank())));
			handCard.setFitWidth(120);
			handCard.setPreserveRatio(true);
			handCard.setCache(true);
			overlay.setFitWidth(121);
			overlay.setPreserveRatio(true);	
			overlay.setCache(true);			
			Group unplayable = new Group();
			unplayable.setBlendMode(BlendMode.SRC_OVER);
			unplayable.getChildren().addAll(handCard, overlay);
			playerCard.getChildren().add(unplayable);
			playerCard.setAlignment(Pos.BOTTOM_CENTER);
			layout3.setBottom(playerCard);
		}
		for(int i = 0 ; i < 4 ; i++){
			final int temp = i;
			ImageView suitCard = new ImageView();
			suitCard.setImage(new Image(String.format("%d.png", i)));
			SuitNom.setSpacing(10);
			SuitNom.getChildren().add(suitCard);
			SuitNom.setAlignment(Pos.CENTER);
			layout3.setCenter(SuitNom);
			suitCard.setFitWidth(120);
			suitCard.setPreserveRatio(true);
			suitCard.setCache(true);
			
			//Bind events to suitCard
			suitCard.setOnMouseClicked(e -> {
				suitNom = temp;
				card.setTempSuit(suitNom);
				pileCard.setImage(new Image(String.format("%d%d.png", suitNom, 1)));
				pileCard.setFitWidth(120);
				pileCard.setPreserveRatio(true);
				pileCard.setCache(true);
				playerCount.setImage(new Image(String.format("Player %d.png", (indexNo+1))));
				playerCount.setFitWidth(160);
				playerCount.setPreserveRatio(true);
				playerCount.setCache(true);
				SuitNom.getChildren().clear();
				ContinueGame();
			});
		}
	}

	/**
	 * Draw card.
	 */
	protected void drawCard(){
		//VBox GlobAnn = new VBox(); //A VBox to store and display several events sequentially
		if(playerList.get(indexNo).cardList.size() == 5){
			GlobAnnounce = new Label("PLAYER " + (indexNo+1) + " SKIPS");
			skipTurn++;
		}
		else{
			playerList.get(indexNo).addCard(stock.remove(0));
			Collections.sort(playerList.get(indexNo).cardList);
			GlobAnnounce = new Label("PLAYER " + (indexNo+1) + " DRAWS A CARD AND PASSES THE TURN");
			skipTurn = 0;
		}
		GlobAnnounce.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		GlobAnnounce.setTextFill(Color.web("#DAA520"));
		GlobAnn.getChildren().add(GlobAnnounce);
		GlobAnn.setAlignment(Pos.CENTER);
		FadeTransition fader = new FadeTransition(Duration.millis(1500), GlobAnnounce );
		fader.setFromValue(1.0);
		fader.setToValue(0.5);
		fader.play();
		fader.setOnFinished(e-> {
			GlobAnn.getChildren().clear();
			layout3.getChildren().remove(GlobAnn);
		});
		layout3.setCenter(GlobAnn);
	}
		
	/**
	 * Mouse action binding for the ContinueGame method.
	 *
	 * @param index the index
	 * @param image the image
	 */
	protected void MouseAction(int index, ImageView image){
		final int idx = index;
		Player curplayer = playerList.get(indexNo);
		image.setOnMouseClicked(e -> {
			if(pile.get(0).match(curplayer.cardList.get(idx)) && curplayer.cardList.get(idx) instanceof Ace){
				removeHandCard(idx);
				pile.get(0).action();
				skipTurn  = 0;
				nextPlayer();
				if(curplayer.cardList.isEmpty()){
					declareWinner();
				}
			}
			else if(pile.get(0).match(curplayer.cardList.get(idx)) && !(curplayer.cardList.get(idx) instanceof Ace)){
				removeHandCard(idx);
				pile.get(0).action();
				skipTurn  = 0;
				nextPlayer();
				pileCard.setImage(new Image(String.format("%d%d.png", pile.get(0).getSuit(), pile.get(0).getRank())));
				pileCard.setFitWidth(120);
				pileCard.setPreserveRatio(true);
				pileCard.setCache(true);
				playerCount.setImage(new Image(String.format("Player %d.png", (indexNo+1))));
				playerCount.setFitWidth(160);
				playerCount.setPreserveRatio(true);
				playerCount.setCache(true);
				if(curplayer.cardList.isEmpty()){
					declareWinner();
				}
				else ContinueGame();
			}
		});
	}

	/**
	 * Continue the game.
	 */
	protected void ContinueGame(){
		playerCard.setSpacing(10); 
		while(skipTurn != playerlimit){
			if(stock.size()==0){
				shuffle(stock, pile);
			}
			if(playerList.get(indexNo).isPlayable(pile.get(0))){
				Player curplayer = playerList.get(indexNo);
				playerCard.getChildren().clear();
				for(int i = 0  ; i < curplayer.cardList.size() ; i++){
					ImageView handCard = new ImageView();
					ImageView overlay = new ImageView();
					overlay.setImage(new Image("overlay.png"));
					handCard.setImage(new Image(String.format("%d%d.png",curplayer.cardList.get(i).getSuit(), curplayer.cardList.get(i).getRank())));
					handCard.setFitWidth(120);
					handCard.setPreserveRatio(true);
					handCard.setCache(true);
					overlay.setFitWidth(121);
					overlay.setPreserveRatio(true);	
					overlay.setCache(true);
					
					//Top pile card is Ace and it is not a nominated Ace
					if(pile.get(0) instanceof Ace && suitNom==4){
						
						//The hand card match the pile normal Ace
						if(pile.get(0).match(curplayer.cardList.get(i)) || curplayer.cardList.get(i) instanceof Ace){
							playerCard.getChildren().add(handCard);
							playerCard.setAlignment(Pos.BOTTOM_CENTER);
							layout3.setBottom(playerCard);
							MouseAction(i, handCard);
						}
						
						//Pile's normal Ace do not match the particular hand card
						else{
							Group unplayable = new Group();
							unplayable.setBlendMode(BlendMode.SRC_OVER);
							unplayable.getChildren().addAll(handCard, overlay);
							playerCard.getChildren().add(unplayable);
							playerCard.setAlignment(Pos.BOTTOM_CENTER);
							layout3.setBottom(playerCard);
							MouseAction(i, overlay);
						}
					}
					
					//Top pile card is Ace and is nominated Ace
					else if(pile.get(0) instanceof Ace && suitNom !=4){
						//The hand card match the pile nominated Ace
						if(pile.get(0).match(curplayer.cardList.get(i)) || curplayer.cardList.get(i) instanceof Ace ){
						
							playerCard.getChildren().add(handCard);
							playerCard.setAlignment(Pos.BOTTOM_CENTER);
							layout3.setBottom(playerCard);
							MouseAction(i, handCard);
						}
						//The hand card does not match the pile nominated Ace
						else{
							Group unplayable = new Group();
							unplayable.setBlendMode(BlendMode.SRC_OVER);
							unplayable.getChildren().addAll(handCard, overlay);
							playerCard.getChildren().add(unplayable);
							playerCard.setAlignment(Pos.BOTTOM_CENTER);
							layout3.setBottom(playerCard);
							MouseAction(i, overlay);
						}
					}
					
					//Other type of card
					else{
						//The hand card matches the pile card
						if(pile.get(0).match(curplayer.cardList.get(i))|| curplayer.cardList.get(i) instanceof Ace){
							
							playerCard.getChildren().add(handCard);
							playerCard.setAlignment(Pos.BOTTOM_CENTER);
							layout3.setBottom(playerCard);
							MouseAction(i, handCard);
						}
						//The hand card do not match the pile card
						else{
							Group unplayable = new Group();
							unplayable.setBlendMode(BlendMode.SRC_OVER);
							unplayable.getChildren().addAll(handCard, overlay);
							playerCard.getChildren().add(unplayable);
							playerCard.setAlignment(Pos.BOTTOM_CENTER);
							layout3.setBottom(playerCard);
							MouseAction(i, overlay);
						}
					}
				}
				
				break;
			}
			else{
				drawCard();
				nextPlayer();
				playerCount.setImage(new Image(String.format("Player %d.png", (indexNo+1))));
				playerCount.setFitWidth(160);
				playerCount.setPreserveRatio(true);
				playerCount.setCache(true);
				
			}
		}
		if(skipTurn == playerlimit){
			declareWinner();
		}
	}
	
	/**
	 * Generate game.
	 *
	 * @param totalP the total player number
	 */
	protected void generateGame(int totalP){
		createDeck(stock);
		shuffle(stock, pile);
		distributeCard(playerlimit);
		
		layout3.setId("ThirdScene");
		scene3 = new Scene(layout3,  1400, 800);
		scene3.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		//Hold stockCard and pileCard
		HBox gameCard = new HBox();
		gameCard.setSpacing(10);
		
		playerCount.setImage(new Image("Player 1.png"));
		playerCount.setFitWidth(160);
		playerCount.setPreserveRatio(true);
		playerCount.setCache(true);
		
		//Decorated stock card
		ImageView stockCard = new ImageView();
		stockCard.setImage(new Image("Cover.png"));
		stockCard.setFitWidth(120);
		stockCard.setPreserveRatio(true);
		stockCard.setCache(true);
				
		pileCard.setImage(new Image(String.format("%d%d.png", pile.get(0).getSuit(), pile.get(0).getRank())));
		pileCard.setFitWidth(120);
		pileCard.setPreserveRatio(true);
		pileCard.setCache(true);
				
		gameCard.getChildren().addAll(playerCount, stockCard, pileCard);
		gameCard.setAlignment(Pos.CENTER);
		layout3.setTop(gameCard);
		ContinueGame();
	}
	
	/**
	 * Main menu when the program is launched.
	 */
	protected void MainMenu(){
		//Start button
		ImageView startButton = new ImageView();
		startButton.setImage(new Image("PLAY GAME.png"));
		startButton.setOnMouseClicked(e -> window.setScene(scene2));
		startButton.setOnMouseEntered(e->{
			startButton.setImage(new Image("PLAY GAME HOVER.png"));
		});
		startButton.setOnMouseExited(e->{
			startButton.setImage(new Image("PLAY GAME.png"));
		});
		
		//Exit button
		ImageView exitButton = new ImageView();
		exitButton.setImage(new Image("QUIT GAME.png"));
		exitButton.setOnMouseClicked(e->System.exit(0));
		exitButton.setOnMouseEntered(e->{
			exitButton.setImage(new Image("QUIT GAME HOVER.png"));
		});
		exitButton.setOnMouseExited(e->{
			exitButton.setImage(new Image("QUIT GAME.png"));
		});
		
		
		//Back button
		ImageView backButton = new ImageView();
		backButton.setImage(new Image("BACK.png"));
		backButton.setOnMouseClicked(e-> window.setScene(scene1));
		backButton.setOnMouseEntered(e->{
			backButton.setImage(new Image("BACK HOVER.png"));
		});
		backButton.setOnMouseExited(e->{
			backButton.setImage(new Image("BACK.png"));
		});
		
		BorderPane layout1 = new BorderPane();
		layout1.setId("FirstScene");
		VBox startexitBox = new VBox();
		startexitBox.setSpacing(10);
		startexitBox.getChildren().addAll(startButton, exitButton);
		startexitBox.setAlignment(Pos.CENTER);
		layout1.setCenter(startexitBox);
		
		scene1 = new Scene(layout1, 1400, 800);
		scene1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		//Two player button
		ImageView player2 = new ImageView();
		player2.setImage(new Image("TWO.png"));
		//Binding events
		player2.setOnMouseClicked(e-> {
			playerlimit = 2;
			generateGame(playerlimit);
			window.setScene(scene3);
			});
		//Binding events
		player2.setOnMouseEntered(e->{
			player2.setImage(new Image("TWO HOVER.png"));
		});
		//Binding events
		player2.setOnMouseExited(e->{
			player2.setImage(new Image("TWO.png"));
		});
		
		//Three player button
		ImageView player3 = new ImageView();
		player3.setImage(new Image("THREE.png"));
		//Binding events
		player3.setOnMouseClicked(e-> {
			playerlimit = 3;
			generateGame(playerlimit);
			window.setScene(scene3);
			});
		//Binding events
		player3.setOnMouseEntered(e->{
			player3.setImage(new Image("THREE HOVER.png"));
		});
		player3.setOnMouseExited(e->{
			player3.setImage(new Image("THREE.png"));
		});
		
		//Four player button
		ImageView player4 = new ImageView();
		player4.setImage(new Image("FOUR.png"));
		player4.setOnMouseClicked(e-> {
			playerlimit = 4;
			generateGame(playerlimit);
			window.setScene(scene3);
			});
		player4.setOnMouseEntered(e->{
			player4.setImage(new Image("FOUR HOVER.png"));
		});
		player4.setOnMouseExited(e->{
			player4.setImage(new Image("FOUR.png"));
		});
		
		//layout2
		BorderPane layout2 = new BorderPane();
		layout2.setId("SecondScene");
		
		//A VBox to store and display the 2-4 player selection button for user to make selection
		VBox amtPlayerSelectionBox = new VBox();
		amtPlayerSelectionBox.setSpacing(10);
		amtPlayerSelectionBox.getChildren().addAll(player2, player3, player4, backButton);
		amtPlayerSelectionBox.setAlignment(Pos.CENTER);
		layout2.setCenter(amtPlayerSelectionBox);
		scene2 = new Scene(layout2, 1400, 800);
		scene2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		
		//Display scene 1 at first
        window.setScene(scene1);
	}
	
	/**
	 * Declare winner and display the player score list.
	 */
	protected void declareWinner(){
		//VBox to hold the playerStatus's HBoxes
		VBox mainPlayerBox = new VBox();	
		mainPlayerBox.setSpacing(10);
		mainPlayerBox.setAlignment(Pos.CENTER);
		BorderPane layout4 = new BorderPane();
		layout4.setId("FourthScene");
		
		//Identify winner
		int WinnerIndex = 0, tempMin = 999999999, minScore;
		
		for(int i = 0 ; i < playerlimit; i++){
			minScore = tempMin;
			tempMin = Math.min(playerList.get(i).getTotalPoint(), minScore);
			if(minScore != tempMin){
				WinnerIndex = i;
			}
		}
		
		for(int i = 0 ; i < playerlimit; i++){
			//HBox to store and display the winner ImageView, player index and the player's score 
			HBox playerStatus = new HBox();
			playerStatus.setMinWidth(350);
			playerStatus.setSpacing(10);
			playerStatus.setAlignment(Pos.CENTER_LEFT);
			Label playerindex = new Label("Player " + (i+1) + "           ");
			playerindex.setFont(Font.font("Cambria", FontPosture.ITALIC, 40));
			Label playerScore = new Label("Penalty Points: " + playerList.get(i).getTotalPoint());
			playerScore.setFont(Font.font("Cambria", FontPosture.ITALIC, 40));
			if(WinnerIndex == i){
				//Store and display image of winner stamp
				ImageView winner = new ImageView();
				winner.setImage(new Image("Winner Stamp.png"));
				playerStatus.getChildren().addAll(winner, playerindex,playerScore);
			}
			else{
				//a dummy image for consistency alignment for the GUI
				ImageView dummy = new ImageView();
				dummy.setImage(new Image("dummy.png"));
				playerStatus.getChildren().addAll(dummy, playerindex,playerScore);
			}
			
			//a dummy VBox for consistency alignment for the GUI
			VBox dummmy2 = new VBox();
			dummmy2.setPrefSize(370,200);
			mainPlayerBox.getChildren().addAll(playerStatus);
			layout4.setLeft(dummmy2);
			layout4.setCenter(mainPlayerBox);
		}
	
		//Buttons
		HBox buttons = new HBox();
		buttons.setSpacing(10);
		
		//Main menu button
		ImageView MainMenu = new ImageView();
		MainMenu.setImage(new Image("MAIN MENU.png"));
		//Binding events
		MainMenu.setOnMouseClicked(e-> {
			window.close();
			Game game = new Game();
			game.start(new Stage());
			});
		//Binding events
		MainMenu.setOnMouseEntered(e->{
			MainMenu.setImage(new Image("MAIN MENU HOVER.png"));
		});
		//Binding events
		MainMenu.setOnMouseExited(e->{
			MainMenu.setImage(new Image("MAIN MENU.png"));
		});
		
		//Exit button
		ImageView exitButton = new ImageView();
		exitButton.setImage(new Image("QUIT GAME.png"));
		exitButton.setOnMouseClicked(e->System.exit(0));
		//Binding events
		exitButton.setOnMouseEntered(e->{
			exitButton.setImage(new Image("QUIT GAME HOVER.png"));
		});
		//Binding events
		exitButton.setOnMouseExited(e->{
			exitButton.setImage(new Image("QUIT GAME.png"));
		});

		//Dummy image
		ImageView dummy3 = new ImageView();
		dummy3.setImage(new Image("dummy3.png"));
		buttons.getChildren().addAll(dummy3 ,MainMenu, exitButton);
		mainPlayerBox.getChildren().addAll(buttons);
		
		scene4 = new Scene(layout4, 1400, 800);
		scene4.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		window.setScene(scene4);
	}
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage){
		window = primaryStage;
		MainMenu();
		window.setTitle("Poker Game");
	    window.show();
	}
		
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main (String[] args) {
		launch(args);
	}
}