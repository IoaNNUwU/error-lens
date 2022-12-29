package com.github.ioannuwu.errorlens.data;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "com.github.ioannuwu.errorlens.data.MySettingsState",
        storages = @Storage("com.github.ioannuwu.errorlens.settings.xml"))
public class DataSettingsService implements PersistentStateComponent<SettingsState> {

    private SettingsState myState;

    @Override
    public com.github.ioannuwu.errorlens.data.@Nullable SettingsState getState() {
        return myState;
    }

    @Override
    public void loadState(@NotNull SettingsState state) {
        XmlSerializerUtil.copyBean(state, myState);
    }
}
