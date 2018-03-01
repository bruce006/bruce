package com.konka.demo.module;

public enum EnumModuleState {
    OFF(0),
    ON(1),
    DEMO(2),
    LOW(3),
    MIDDLE(4),
    HIGH(5);

    private int value;

    EnumModuleState(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

}
