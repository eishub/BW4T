package nl.tudelft.bw4t.map;

/**
 * This Exception is thrown if the map loaded by the application does not conform to the requirements.
 */
public class MapFormatException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5626576370581996604L;

	public MapFormatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MapFormatException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public MapFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MapFormatException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MapFormatException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
