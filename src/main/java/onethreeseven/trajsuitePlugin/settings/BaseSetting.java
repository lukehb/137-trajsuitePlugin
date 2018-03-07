package onethreeseven.trajsuitePlugin.settings;

import javafx.util.StringConverter;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public abstract class BaseSetting<T> {

    protected final String category;
    protected final String settingName;
    protected final String settingDescription;
    protected final T defaultSettingValue;

    protected BaseSetting(String category, String settingName, String settingDescription, T defaultSettingValue) {
        this.category = category;
        this.settingName = settingName;
        this.settingDescription = settingDescription;
        this.defaultSettingValue = defaultSettingValue;
        String storedValued = getConverter().toString(defaultSettingValue);
        ProgramSettings.initialiseSetting(category, settingName, settingDescription, storedValued);
    }

    public void changeSetting(T newValue){
        String storedValued = getConverter().toString(newValue);
        ProgramSettings.setSetting(category, settingName, settingDescription, storedValued);
    }

    public abstract T getSetting();

    public abstract StringConverter<T> getConverter();

}
