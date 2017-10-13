package com.tmsdurham.tridroidredux.common.inject.component;


import com.tmsdurham.tridroidredux.common.inject.module.AppModule;
import com.tmsdurham.tridroidredux.common.inject.module.DataModule;
import com.tmsdurham.tridroidredux.common.inject.module.PresentationModule;
import com.tmsdurham.tridroidredux.common.inject.module.UseCaseModule;
import com.tmsdurham.tridroidredux.event_discover.view.DiscoverViewImpl;
import com.tmsdurham.tridroidredux.TridroidApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by billyparrish on 3/3/17 for Shortlist.
 */


@Singleton
@Component(modules = {AppModule.class, DataModule.class, PresentationModule.class, UseCaseModule.class})
public interface ShortListComponent {

    void inject(DiscoverViewImpl discoverViewImpl);

    void inject(TridroidApp tridroidApp);
}
