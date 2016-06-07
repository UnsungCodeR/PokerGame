import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player {
	
	/** The card list. */
	protected List<Card> cardList = new ArrayList<Card>(); 
	/** The penalty point. */
	private int penalPoint = 0; 
	
	/**
	 * Play card.
	 *
	 * @param index the index
	 */
	//Remove a hand card to the pile
	protected void playCard(int index){
		cardList.remove(index);
	}
	
	/**
	 * Checks if is playable.
	 *
	 * @param card the card
	 * @return true, if is playable
	 */
	//Check any of the player's hand card is playable
	protected boolean isPlayable(Card card){
		for(int i = 0 ; i < cardList.size(); i++){
			if(card.match(cardList.get(i)))
				return true;
		}
		return false;
	}
	
	/**
	 * Adds a card.
	 *
	 * @param card the card
	 */
	protected void addCard(Card card){
		cardList.add(card);
	}
	
	/**
	 * Gets the total point.
	 *
	 * @return the total point
	 */
	public int getTotalPoint(){
		for(int i = 0 ; i < cardList.size(); i++){
			penalPoint += cardList.get(i).returnScore();
		}
		return penalPoint;
	}
}