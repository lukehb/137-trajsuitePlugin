package onethreeseven.trajsuitePlugin.settings;

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
        ProgramSettings.initialiseSetting(category, settingName, settingDescription, defaultSettingValue.toString());
    }

    public void changeSetting(T newValue){
        ProgramSettings.setSetting(category, settingName, settingDescription, newValue.toString());
    }

    public abstract T getSetting();

}
