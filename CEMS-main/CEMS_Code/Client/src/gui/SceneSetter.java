package gui;

import javafx.scene.Scene;
/**
 * The SceneSetter interface defines methods for setting a JavaFX scene and keeping track of the previous screen.
 */
public interface SceneSetter {

    /**
     * Sets the JavaFX scene.
     *
     * @param scene The scene to be set.
     */
	void setScene(Scene scene);
    /**
     * Sets the previous screen.
     *
     * @param str The identifier or name of the previous screen.
     */
	void setPrevScreen(String str);

}