
// TODO: Auto-generated Javadoc
/**
 * The Class Jack.
 */
public class Jack extends Card{

	/**
	 * Instantiates a new jack.
	 *
	 * @param suit the suit
	 * @param rank the rank
	 * @param igame the igame
	 */
	protected Jack(int suit, int rank, IGame igame) {
		super(suit, rank, igame);
	}

	/* (non-Javadoc)
	 * @see Card#action()
	 */
	@Override
	protected void action(){
		igame.showMessage(2);
		igame.oneMoreTurn();
	}
}