
// TODO: Auto-generated Javadoc
/**
 * The Interface IGame.
 */
public interface IGame {
	
	/**
	 * Reverse.
	 */
	public abstract void reverse();
	
	/**
	 * Skip turn.
	 */
	public abstract void skipturn();
	
	/**
	 * One more turn.
	 */
	public abstract void oneMoreTurn();
	
	/**
	 * Pass card.
	 *
	 * @param card the card
	 */
	public abstract void passCard(Ace card);
	
	/**
	 * Show message.
	 *
	 * @param choice the choice
	 */
	public abstract void showMessage(int choice);
	}

