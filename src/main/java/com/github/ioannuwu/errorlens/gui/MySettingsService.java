package com.github.ioannuwu.errorlens.gui;

import com.github.ioannuwu.errorlens.appearance.DefaultSettingsList;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(name = "com.github.ioannuwu.errorlens.gui.MySettingsService",
        storages = @Storage("com.github.ioannuwu.errorlens.settings.xml"))
public class MySettingsService implements PersistentStateComponent<MySettingsService> {

    @Override public @NotNull MySettingsService getState() {
        return this;
    }

    @Override public void loadState(@NotNull MySettingsService state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static @NotNull MySettingsService getInstance() {
        return ApplicationManager.getApplication().getService(MySettingsService.class);
    }

    public int numberWhitespaces = DefaultSettingsList.INSTANCE.getSPACES();

    public @NotNull Settings error = DefaultSettingsList.INSTANCE.getERROR();
    public @NotNull Settings warning = DefaultSettingsList.INSTANCE.getWARNING();
    public @NotNull Settings weakWarning = DefaultSettingsList.INSTANCE.getWEAK_WARNING();
    public @NotNull Settings information = DefaultSettingsList.INSTANCE.getINFORMATION();
    public @NotNull Settings other = DefaultSettingsList.INSTANCE.getOTHER();

    public @NotNull String hideList = DefaultSettingsList.INSTANCE.getHIDE_LIST();

    public void applyChanges(SettingsState newSettings) {
        numberWhitespaces = newSettings.numberWhitespaces;

        error = newSettings.error;
        warning = newSettings.warning;
        weakWarning = newSettings.weakWarning;
        information = newSettings.information;
        other = newSettings.other;

        hideList = newSettings.hideList;
    }
}

