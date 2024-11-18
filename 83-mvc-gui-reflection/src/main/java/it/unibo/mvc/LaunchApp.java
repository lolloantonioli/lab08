package it.unibo.mvc;

import java.lang.Class;
import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {
    
    private static final String FIRST_NAME = "it.unibo.mvc.view.DrawNumberSwingView";
    private static final String SECOND_NAME = "it.unibo.mvc.view.DrawNumberViewOut";

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args)
        throws 
        ClassNotFoundException, 
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException, 
        IllegalAccessException {

        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final var firstClazz = Class.forName(FIRST_NAME);
        final var secondClazz = Class.forName(SECOND_NAME);
        for (int i = 0; i < 3; i++) {
            final DrawNumberView firstClazzView = (DrawNumberView)firstClazz.getConstructor().newInstance();
            if (DrawNumberView.class.isAssignableFrom(firstClazzView.getClass())) {
                app.addView(firstClazzView);
            } else {
                throw new IllegalArgumentException();
            }
            
            final DrawNumberView secondClazzView = (DrawNumberView)secondClazz.getConstructor().newInstance();
            if (DrawNumberView.class.isAssignableFrom(secondClazzView.getClass())) {
                app.addView(secondClazzView);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
