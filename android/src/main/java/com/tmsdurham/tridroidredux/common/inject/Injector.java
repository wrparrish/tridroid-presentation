package com.tmsdurham.tridroidredux.common.inject;



import com.tmsdurham.tridroidredux.event_discover.view.DiscoverViewImpl;
import com.tmsdurham.tridroidredux.TridroidApp;
import com.tmsdurham.tridroidredux.common.inject.component.ShortListComponent;

import org.jetbrains.annotations.NotNull;

/**
 * Created by billyparrish on 3/3/17 for Shortlist.
 */

public class Injector {

    private static Injector instance;
    private ShortListComponent component;

    private Injector(ShortListComponent component) {
        this.component = component;
    }

    public static Injector get(ShortListComponent component) {
        if (instance == null) {
            instance = new Injector(component);
        }

        return instance;
    }

    public void setComponent(ShortListComponent component) {
        this.component = component;
    }


    public static void inject(@NotNull DiscoverViewImpl discoverViewImpl) {
        instance.component.inject(discoverViewImpl);
    }


    public static void inject(@NotNull TridroidApp tridroidApp) {
        instance.component.inject(tridroidApp);
    }
}
