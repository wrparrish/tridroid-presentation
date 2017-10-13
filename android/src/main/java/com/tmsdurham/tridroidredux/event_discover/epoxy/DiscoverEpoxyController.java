package com.tmsdurham.tridroidredux.event_discover.epoxy;

import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelClickListener;
import com.airbnb.epoxy.TypedEpoxyController;
import com.tmsdurham.tridroidredux.event_discover.epoxy.banners.HeaderViewModel_;
import com.tmsdurham.tridroidredux.event_discover.epoxy.banners.LoaderView_;
import com.tmsdurham.tridroidredux.event_discover.epoxy.filter.EverythingFilterModelGroup;
import com.tmsdurham.tridroidredux.event_discover.epoxy.list.ListEventModel_;
import com.tmsdurham.tridroidredux.event_discover.epoxy.list.ListModel_;
import com.tmsdurham.tridroidredux.event_discover.epoxy.models.ErrorModel;
import com.tmsdurham.tridroidredux.event_discover.epoxy.models.ErrorModel_;
import com.tmsdurham.tridroidredux.event_discover.view.DiscoverViewImpl;

import java.util.ArrayList;
import java.util.List;

import domain_model.discover.Event;
import com.tmsdurham.DiscoverModel;


public class DiscoverEpoxyController extends TypedEpoxyController<DiscoverModel.State> {
    private DiscoverViewImpl.DiscoverCallbacks callbacks;

    public DiscoverEpoxyController(DiscoverViewImpl.DiscoverCallbacks callbacks) {
        this.callbacks = callbacks;
        setDebugLoggingEnabled(true);
    }


    @AutoModel
    HeaderViewModel_ header;
    @AutoModel
    ListModel_ listModel;

    @AutoModel
    LoaderView_ loaderView;
    @AutoModel
    ErrorModel_ errorModel;


    @Override
    protected void buildModels(DiscoverModel.State state) {
        String cityName = state.getCity().getName();
        header.title("Shows this week in")
                .caption(cityName)
                .callback(callbacks);
        add(header);


        // if we are loading,  or in an error state we short circuit out of the additional model building

        if (state.isLoading()) {
            add(loaderView
                    .progressString(state.getProgressString()));
            return;
        } else if (state.isInErrorState()) {

            add(errorModel
                    .clickListener(new OnModelClickListener<ErrorModel_, ErrorModel.ErrorHolder>() {
                        @Override
                        public void onClick(ErrorModel_ errorModel_, ErrorModel.ErrorHolder errorHolder, View view, int i) {
                            callbacks.onTryAgainClicked();
                        }
                    }));
            return;
        }


        if (state.getGenreMap().size() > 0) {
            add(new EverythingFilterModelGroup(state, callbacks));
        }

        if (state.getGenreFilteredEventList().size() > 0) {
            getListModels(state.getGenreFilteredEventList());
            ArrayList<ListEventModel_> verticalEventModels = getListModels(state.getGenreFilteredEventList());
            for (ListEventModel_ model : verticalEventModels) {
                add(model);
            }


        }

    }


        public ArrayList<ListEventModel_> getListModels (List < Event > events) {
            ArrayList<ListEventModel_> list = new ArrayList<>();

            for (Event event : events) {
                ListEventModel_ model = new ListEventModel_()
                        .id(event.getId())
                        .event(event)
                        .clickListener(new OnModelClickListener() {
                            @Override
                            public void onClick(EpoxyModel model, Object parentView, View clickedView, int position) {

                            }
                        });

                list.add(model);
            }
            return list;
        }
    }





