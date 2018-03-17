package com.freactive.flawnkid.core.util;

public interface RevertibleAction {
    void revertLastItem();

    void consumeRevert();

    void setLastItem(Object... args);
}
