
// TODO: Auto-generated Javadoc
/**
 * The Class Queen.
 */
public class Queen extends Card{
	
	/**
	 * Instantiates a new queen.
	 *
	 * @param suit the suit
	 * @param rank the rank
	 * @param igame the igame
	 */
	protected Queen(int suit, int rank, IGame igame) {
		super(suit, rank, igame);
	}

	/* (non-Javadoc)
	 * @see Card#action()
	 */
	@Override
	protected void action(){
		igame.skipturn();
		igame.showMessage(0);
	}
}
