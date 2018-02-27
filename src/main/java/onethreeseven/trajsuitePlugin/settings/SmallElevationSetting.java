package onethreeseven.trajsuitePlugin.settings;

/**
 * The distance in metres that things will be drawn off the ground
 * when they are meant to be on the ground.
 * Note, it really should be a non-zero value to avoid z-fighting.
 * @author Luke Bermingham
 */
public class SmallElevationSetting extends BaseSetting<Integer> {

    protected SmallElevationSetting() {
        super(ProgramSettings.GraphicsCategory,
                "Small Elevation Distance",
                "The distance in metres that things will be drawn off the " +
                        "ground when they are meant to be on the ground. Note, it really should be a" +
                        "non-zero value to avoid z-fighting).",
                10);
    }

    @Override
    public Integer getSetting() {
        return ProgramSettings.getSetting(category, settingName, defaultSettingValue);
    }
}
