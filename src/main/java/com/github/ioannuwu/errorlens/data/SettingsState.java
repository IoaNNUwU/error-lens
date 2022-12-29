package com.github.ioannuwu.errorlens.data;

import com.github.ioannuwu.errorlens.data.defaultsettings.DefaultSettingsList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SettingsState {

    public int numberOfWhitespaces = DefaultSettingsList.NUMBER_OF_WHITESPACES;

    public @NotNull ErrorTypeSettingsState error = DefaultSettingsList.ERROR;
    public @NotNull ErrorTypeSettingsState warning = DefaultSettingsList.WARNING;
    public @NotNull ErrorTypeSettingsState weakWarning = DefaultSettingsList.WEAK_WARNING;
    public @NotNull ErrorTypeSettingsState information = DefaultSettingsList.INFORMATION;
    public @NotNull ErrorTypeSettingsState other = DefaultSettingsList.OTHER;

    public @NotNull List<String> ignoreList = DefaultSettingsList.IGNORE_LIST;

    public static class ErrorTypeSettingsState {

        public boolean showGutterIcon;
        public boolean showBackground;
        public int backgroundColor;
        public boolean showText;
        public int textColor;

        // Empty constructor for XML Serialization to work
        public ErrorTypeSettingsState() {}

        public ErrorTypeSettingsState(boolean showGutterIcon, boolean showBackground, int backgroundColor,
                                      boolean showText, int textColor) {
            this.backgroundColor = backgroundColor;
            this.showBackground = showBackground;
            this.showGutterIcon = showGutterIcon;
            this.showText = showText;
            this.textColor = textColor;
        }

        public ErrorTypeSettingsState(ErrorTypeSettingsState dataState) {
            this.backgroundColor = dataState.backgroundColor;
            this.textColor = dataState.textColor;
            this.showBackground = dataState.showBackground;
            this.showText = dataState.showText;
            this.showGutterIcon = dataState.showGutterIcon;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingsState that = (SettingsState) o;
        return numberOfWhitespaces == that.numberOfWhitespaces && error.equals(that.error)
                && warning.equals(that.warning) && weakWarning.equals(that.weakWarning)
                && information.equals(that.information) && other.equals(that.other)
                && ignoreList.equals(that.ignoreList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfWhitespaces, error, warning, weakWarning, information, other, ignoreList);
    }
}
