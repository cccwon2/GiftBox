package me.memorytalk.common.code;

public enum DeviceOs {

    IOS("ios"),
    ANDROID("android");

    private String name;

    DeviceOs(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return this.toString();
    }

}
