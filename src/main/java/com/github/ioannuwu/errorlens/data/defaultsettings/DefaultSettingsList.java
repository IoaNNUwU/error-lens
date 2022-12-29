package com.github.ioannuwu.errorlens.data.defaultsettings;

import com.github.ioannuwu.errorlens.data.SettingsState;

import java.awt.Color;
import java.util.List;

public class DefaultSettingsList {

    public static final int NUMBER_OF_WHITESPACES = 4;

    public static final SettingsState.ErrorTypeSettingsState ERROR = new SettingsState.ErrorTypeSettingsState(
            true,
            true, (new Color(40, 20, 0)).getRGB(),
            true, (new Color(200, 60, 60)).getRGB()
    );
    public static final SettingsState.ErrorTypeSettingsState WARNING = new SettingsState.ErrorTypeSettingsState(
            true,
            true, (new Color(60, 60, 0)).getRGB(),
            true, (new Color(200, 140, 40)).getRGB()
    );
    public static final SettingsState.ErrorTypeSettingsState WEAK_WARNING = new SettingsState.ErrorTypeSettingsState(
            true,
            true, (new Color(60, 30, 0)).getRGB(),
            true, (new Color(200, 200, 100)).getRGB()
    );
    public static final SettingsState.ErrorTypeSettingsState INFORMATION = new SettingsState.ErrorTypeSettingsState(
            true,
            true, (new Color(35, 35, 35)).getRGB(),
            true, (new Color(200, 200, 200)).getRGB()
    );
    public static final SettingsState.ErrorTypeSettingsState OTHER = new SettingsState.ErrorTypeSettingsState(
            true,
            true, (new Color(15, 10, 10)).getRGB(),
            true, (new Color(100, 100, 100)).getRGB()
    );

    public static final List<String> IGNORE_LIST = List.of("TYPO", "TODO", "is never used");
}
