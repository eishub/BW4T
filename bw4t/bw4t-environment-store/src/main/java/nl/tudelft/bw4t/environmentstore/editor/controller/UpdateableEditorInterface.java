package nl.tudelft.bw4t.environmentstore.editor.controller;

/**
 * Updates the map and the map preview
 */
public interface UpdateableEditorInterface {
    /**
     * Call this function when the model changed and the View needs to be updated.
     */
    void update();
}
