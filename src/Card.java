
// TODO: Auto-generated Javadoc
/**
 * The Class Card.
 */
public class Card implements Comparable<Card>{
	
	/** The igame. */
	protected IGame igame;
	
	/** The Constant A, J, Q, K. */
	private final static int A = 1, J = 11, Q = 12, K = 13;
	
	/** The suit. Spade = 0, Heart = 1, Club = 2, Diamond = 3 */
	protected int suit;
	
	/** The rank. */
	protected int rank;
	
	/**
	 * Sets the i game.
	 *
	 * @param igame the new i game
	 */
	protected void setIGame(IGame igame){
		this.igame = igame;
	}
	
	/**
	 * Instantiates a new card.
	 *
	 * @param suit the suit
	 * @param rank the rank
	 * @param igame the igame
	 */
	protected Card(int suit, int rank, IGame igame){
		this.suit = suit;	
		this.rank = rank;
		this.igame = igame;
	}
	
	/**
	 * Sets the suit.
	 *
	 * @param suit the new suit
	 */
	protected void setSuit(int suit){
		this.suit = suit;
	}
	
	/**
	 * Gets the suit.
	 *
	 * @return the suit
	 */
	protected int getSuit(){
		return suit;
	}
	
	/**
	 * Sets the rank.
	 *
	 * @param rank the new rank
	 */
	protected void setRank(int rank){
		this.rank = rank;
	}
	
	/**
	 * Gets the rank.
	 *
	 * @return the rank
	 */
	protected int getRank(){
		return rank;
	}
	
	/**
	 * Card Matching.
	 *
	 * @param card the card
	 * @return true, if successful
	 */
	protected boolean match(Card card){
		if(rank == card.rank || suit == card.suit || card.rank == 1 ) 
			return true;
		else return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Card card){
		if (suit == card.suit) {
			return rank - card.rank;
		} else {
			return suit - card.suit;
		}
	}
	
	/**
	 * Action.
	 */
	protected void action(){}
	
	/**
	 * Return score of the card.
	 *
	 * @return the int
	 */
	protected int returnScore(){
		if(rank == A ) return 20;
		else if(rank == J || rank == Q || rank == K) return 10;
		else return rank;
	}
}