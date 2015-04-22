package nl.tudelft.bw4t.server.util;

import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import repast.simphony.context.Context;

/**
 * Utility class to get an object given its ID.
 */
public final class Id2Object {

    /**
     * makes sure that this is not instantiated as an object
     */
    private Id2Object() {
    }

    /**
     * Returns the {@link BoundedMoveableObject} with the given ID.
     * 
     * @param context
     *          the context from which to get the object
     * @param id
     *            The id to get the room for.
     * @return The {@link BoundedMoveableObject}.
     */
    public static BoundedMoveableObject getObject(Context<Object> context, long id) {
        for (Object o : context.getObjects(BoundedMoveableObject.class)) {
            BoundedMoveableObject obj = (BoundedMoveableObject) o;
            if (obj.getId() == id) {
                return obj;
            }
        }
        return null;
    }
}
