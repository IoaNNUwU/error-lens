package com.github.ioannuwu.errorlens.gui;

public class SettingsState {

    public int numberWhitespaces;

    public Settings error;
    public Settings warning;
    public Settings weakWarning;
    public Settings information;
    public Settings other;

    public String hideList;

    public SettingsState(int numberWhitespaces, Settings error, Settings warning,
                         Settings weakWarning, Settings information, Settings other,
                         String hideList) {

        this.numberWhitespaces = numberWhitespaces;
        this.error = error;
        this.warning = warning;
        this.weakWarning = weakWarning;
        this.information = information;
        this.other = other;
        this.hideList = hideList;
    }
}
