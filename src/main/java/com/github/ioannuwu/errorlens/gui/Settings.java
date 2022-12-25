package com.github.ioannuwu.errorlens.gui;

public class Settings {

    public boolean showGutterIcon;
    public boolean showBackground;
    public int backgroundColor;
    public boolean showText;
    public int textColor;

    // Empty constructor for XML Serialization to work
    public Settings() {}

    public Settings(boolean showGutterIcon, boolean showBackground, int backgroundColor,
                    boolean showText, int textColor) {

        this.backgroundColor = backgroundColor;
        this.showBackground = showBackground;
        this.showGutterIcon = showGutterIcon;
        this.showText = showText;
        this.textColor = textColor;
    }
}
