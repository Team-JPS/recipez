package com.recipez.util;

import javafx.scene.text.Font;

public class GlobalValues {
    public static final Integer APP_WIDTH = 900;
    public static final Integer APP_HEIGHT = 800;
    public static final Integer NAV_HEIGHT = 50;
    public static final Integer VIEW_HEIGHT = APP_HEIGHT - NAV_HEIGHT;
    public static final String COLOR_PRIMARY = "-fx-background-color: #fccdb6;";
    public static final String COLOR_TEST_FORMATTING_ONE = "-fx-background-color:rgb(182, 252, 188);"; //Green
    public static final String COLOR_TEST_FORMATTING_TWO = "-fx-background-color:rgb(182, 187, 252);"; //Purple   
    public static final String COLOR_TEST_FORMATTING_THREE = "-fx-background-color:rgb(255, 131, 189);"; //Red
    public static final Font SMALL_FONT = new Font("Arial", 18);
    public static final Font MEDIUM_FONT = new Font("Arial", 24);
    public static final Font LARGE_FONT = new Font("Arial", 36);

    public static final String SMALL_FONT_SIZE_STRING = "-fx-font-size: 18px;";
    public static final String SMALL_FONT_FAMILY_STRING = "-fx-font-family: Arial;";

    
    public static final String[] VOLUMEVALUES = {" ", "1", "1/2", "1/3", "1/4", "1/8"};
    public static final String[] UNITSOFVOLUMEVALUES = {" ","tsp", "tbsp", "cup", "pint", "qrt", "gal"};     
}
