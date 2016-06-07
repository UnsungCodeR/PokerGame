
// TODO: Auto-generated Javadoc
/**
 * The Class Eight.
 */
public class Eight extends Card{

	/**
	 * Instantiates a new eight.
	 *
	 * @param suit the suit
	 * @param rank the rank
	 * @param igame the igame
	 */
	protected Eight(int suit, int rank, IGame igame) {
		super(suit, rank, igame);
	}
	
	/* (non-Javadoc)
	 * @see Card#action()
	 */
	@Override
	protected void action(){
		igame.reverse();	
		igame.showMessage(1);
	}
}