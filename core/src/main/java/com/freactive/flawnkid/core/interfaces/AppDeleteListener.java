package com.freactive.flawnkid.core.interfaces;

import java.util.List;

public interface AppDeleteListener<A extends AbstractApp> {
    /**
     * @param apps list of apps
     * @return true, if the listener should be removed
     */
    boolean onAppDeleted(List<A> apps);
}
