package org.redwind.autotest.beluga.utils;

public enum Environment {

    CHROME("Chrome"),
    FIREFOX("Firefox"),
    EDGE("Edge"),
    IE("Ie"),
    SAFARI("Safari"),
    ANDROID("Android"),
    IOS("iOS"),
    IOS_SIMULATOR("iOSSimulator"),
    REST("Restful");

    Environment (String platform) {
        this.platform =  platform;
    }

    private String platform;

    public String getPlatform() {
        return platform;
    }

    public static Environment setPlatform(String platform) {
        Environment currentPlatform = null;
        for(Environment checked:Environment.values()) {
            if(checked.platform.equals(platform)) {
                currentPlatform = checked;
                break;
            }
        }
        if(currentPlatform==null) {
            throw new IllegalArgumentException("incorrect platform "+ platform);
        }
        return currentPlatform;
    }
}
