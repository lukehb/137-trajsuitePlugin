package onethreeseven.trajsuitePlugin.settings;

import java.util.prefs.Preferences;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class ProgramSettings {

    public static final String GraphicsCategory = "Graphics";
    public static final String GeneralCategory = "General";
    public static final String MiningCategory = "Mining";
    public static final String MiscCategory = "Misc";

    private static final Preferences store;

    static {
        store = Preferences.userRoot().node("TrajSuite");
        //look
    }

    private static Preferences descriptionNode(String category){
        return store.node(category + "-description");
    }

    public static void initialiseSetting(String category, String settingName, String description, String value){
        String emptySetting = "no_setting";
        String storedValue = store.node(category).get(settingName, emptySetting);
        if(storedValue.equals(emptySetting)){
            setSetting(category, settingName, description, value);
        }
    }

    public static void setSetting(String category, String settingName, String description, String value){
        store.node(category).put(settingName, value);
        descriptionNode(category).put(settingName, description);
    }

    public static String getDescription(String category, String settingName){
        return descriptionNode(category).get(settingName, "");
    }

    public static Boolean getSetting(String category, String settingName, Boolean defaultValue){
        return store.node(category).getBoolean(settingName, defaultValue);
    }

    public static String getSetting(String category, String settingName, String defaultValue){
        return store.node(category).get(settingName, defaultValue);
    }

    public static Integer getSetting(String category, String settingName, Integer defaultValue){
        return store.node(category).getInt(settingName, defaultValue);
    }

    public static Double getSetting(String category, String settingName, Double defaultValue){
        return store.node(category).getDouble(settingName, defaultValue);
    }

}
