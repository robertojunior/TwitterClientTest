package br.com.faru.twittertest.di;

import br.com.faru.twittertest.di.component.TwitterTestComponent;

public class Injector {
    private static Injector instance = new Injector();
    private static TwitterTestComponent component;

    public static Injector getInstance() {
        return instance;
    }

    private Injector() {
    }

    public static void initialize(TwitterTestComponent twitterTestComponent) {
        component = twitterTestComponent;
    }

    public static TwitterTestComponent getComponent() {
        if (component != null) {
            return component;
        } else {
            throw new IllegalArgumentException("Impossible to get the component instance. This class must be initialized before");
        }
    }
}
