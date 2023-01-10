package com.github.ioannuwu.errorlens.data;

import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;

public interface AbstractDataSettingsService extends PersistentStateComponent<SettingsState> {

    @Override @NotNull SettingsState getState();

    @Override void loadState(@NotNull SettingsState state);
}
