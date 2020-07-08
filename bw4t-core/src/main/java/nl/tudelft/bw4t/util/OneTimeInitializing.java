package nl.tudelft.bw4t.util;

/**
 * This interface is a support tool that allows us to use 'equals' on objects
 * that are not thread safe in a class that is supposed to be thread safe.
 * 
 * Classes that implement this must ensure that they correctly implement the
 * functions. If not, the classes that rely on these may end up being not thread
 * safe at all.
 * 
 * @author W.Pasman 27oct2014
 *
 */
public interface OneTimeInitializing {
	/**
	 * Initially, objects may be in a not initialized state and return false.
	 * This is for example the case with serialized objects. At some point the
	 * object may change to initialized state. After that point, the object must
	 * stay in initialized state.
	 * 
	 * By returning true, the object indicates that the result from calls to
	 * <code>equals(Object)</code> will be stable and thread safe. This usually
	 * means that the fields in the object that are relevant for the equals
	 * function are frozen.
	 * 
	 * @return true if the object has done its initializations.
	 */
	public boolean isInitialized();

}
