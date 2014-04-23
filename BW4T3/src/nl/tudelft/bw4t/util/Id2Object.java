package nl.tudelft.bw4t.util;

import nl.tudelft.bw4t.BoundedMoveableObject;
import repast.simphony.context.Context;

/**
 * Utility class to get an object given its ID.
 * 
 * @author W.Pasman
 */
public final class Id2Object {

	private Id2Object() {
	}

	/**
	 * Returns the {@link BoundedMoveableObject} with the given ID.
	 * 
	 * @param id
	 *            The id to get the room for.
	 * @return The {@link BoundedMoveableObject}.
	 */
	public static BoundedMoveableObject getObject(Context<Object> context,
			long id) {

		for (Object o : context.getObjects(BoundedMoveableObject.class)) {

			BoundedMoveableObject obj = (BoundedMoveableObject) o;
			if (obj.getId() == id) {
				return obj;
			}
		}
		return null;
	}
}
