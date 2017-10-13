package com.tmsdurham.tridroidredux.event_discover.epoxy.filter;

import android.view.View;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelGroup;


import com.tmsdurham.tridroidredux.R;
import com.tmsdurham.tridroidredux.event_discover.view.DiscoverViewImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmsdurham.DiscoverModel;

/**
 * Created by alfredovelasco on 7/20/17.
 */

public class EverythingFilterModelGroup extends EpoxyModelGroup {


    public EverythingFilterModelGroup(DiscoverModel.State state, DiscoverViewImpl.DiscoverCallbacks callbacks) {
        super(R.layout.model_everything_filter_group, buildModels(state, callbacks));
        id("FilterModelGroup");
    }

    private static List<EpoxyModel<?>> buildModels(DiscoverModel.State state, final DiscoverViewImpl.DiscoverCallbacks callbacks) {
        List<EpoxyModel<?>> models = new ArrayList<>();
        models.add(new EverythingHeaderViewModel_()
                .title(state.getCarouselEventList().size() > 3 ? "Everything This Week" : "")
                .filterText((state.isGenreDrawerVisible()) ? "RESET" : "FILTER")
                .listener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callbacks.onFilterExpandTextClicked();
                    }
                }));

        ArrayList<FilterButtonModel_> filterButtons = getFilterButtonModels(state.getGenreMap(), callbacks);
        models.add(new FilterOptionsModel_()
                .models(filterButtons)
                .show(state.isGenreDrawerVisible()));
        return models;
    }

    private static ArrayList<FilterButtonModel_> getFilterButtonModels(HashMap<String, Boolean> genres, final DiscoverViewImpl.DiscoverCallbacks callbacks) {
        ArrayList<FilterButtonModel_> models = new ArrayList<>();

         for (final Map.Entry<String, Boolean> entry : genres.entrySet()) {
             models.add(new FilterButtonModel_()
                     .id(entry.getKey())
                     .genreName(entry.getKey())
                     .genreEnabled(entry.getValue())
                     .callback(new View.OnClickListener(){
                         @Override
                         public void onClick(View v) {
                             callbacks.onGenrePillClicked(entry.getKey(), entry.getValue());
                         }
                     })
             );
         }
        return models;
    }
}
