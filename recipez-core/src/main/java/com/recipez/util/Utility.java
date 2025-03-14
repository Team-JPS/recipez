package com.recipez.util;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Utility {

    public static final void fadeIn(Node elementToTransition) {        
        FadeTransition ft = new FadeTransition(Duration.millis(1000), elementToTransition);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();        
    }

    public static final void fadeOut(Node elementToTransition) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), elementToTransition);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }    

}
