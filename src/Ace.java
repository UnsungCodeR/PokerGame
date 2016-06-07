
// TODO: Auto-generated Javadoc
/**
 * The Class Ace.
 */
public class Ace extends Card{
	
	/** The temporary suit. */
	private int tempSuit = 4;
	
	/**
	 * Instantiates a new ace.
	 *
	 * @param suit the suit
	 * @param rank the rank
	 * @param igame the igame
	 */
	protected Ace(int suit, int rank, IGame igame) {
		super(suit, rank, igame);
	}
	
	/**
	 * Sets the temporary suit.
	 *
	 * @param tempSuit the new temporary suit
	 */
	protected void setTempSuit(int tempSuit){
		this.tempSuit = tempSuit;
	}
	
	/* (non-Javadoc)
	 * @see Card#action()
	 */
	@Override
	protected void action(){
		igame.passCard(this);
	}
	
	/* (non-Javadoc)
	 * @see Card#match(Card)
	 */
	@Override
	protected boolean match(Card card){
		if(tempSuit == 4 && ( rank == card.rank || suit == card.suit )){
			 return true;
		}
		else if(tempSuit != 4 && ( rank == card.rank || tempSuit == card.suit)){
			 return true;
		}
		else return false;
	}
}