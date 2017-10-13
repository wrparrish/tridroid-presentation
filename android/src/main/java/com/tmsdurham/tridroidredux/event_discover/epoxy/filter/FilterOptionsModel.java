package com.tmsdurham.tridroidredux.event_discover.epoxy.filter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithView;
import com.airbnb.epoxy.SimpleEpoxyController;
import com.tmsdurham.tridroidredux.event_discover.epoxy.EpoxyModelRecyclerView;


import java.util.List;

/**
 * Created by alfredovelasco on 7/19/17.
 */

@EpoxyModelClass
public abstract class FilterOptionsModel extends EpoxyModelWithView<EpoxyModelRecyclerView> {
    @EpoxyAttribute
    List<? extends EpoxyModel<?>> models;

    @Override
    protected EpoxyModelRecyclerView buildView(ViewGroup parent) {
        int spanSize = getSpanSize();
        RecyclerView.LayoutManager layoutManager =
                new StaggeredGridLayoutManager(spanSize, StaggeredGridLayoutManager.HORIZONTAL);
        EpoxyModelRecyclerView recView = new EpoxyModelRecyclerView(parent.getContext(), null, layoutManager);
        recView.setLayoutManager(layoutManager);
        SimpleEpoxyController controller = new SimpleEpoxyController();
        recView.setAdapter(controller.getAdapter());
        controller.setModels(models);
        controller.setSpanCount(1);
        return recView;
    }

    @Override
    public void bind(EpoxyModelRecyclerView view) {
        int spanSize = getSpanSize();
        RecyclerView.LayoutManager layoutManager =
                new StaggeredGridLayoutManager(spanSize, StaggeredGridLayoutManager.HORIZONTAL);

        view.setLayoutManager(layoutManager);
        view.setModels(models);
    }

    private int getSpanSize() {
        int spanSize;
        if (models.size() <= 3) {
            spanSize = 1;
        } else if (models.size() <= 6) {
            spanSize = 2;

        } else if (models.size() >= 9) {
            spanSize = 4;

        } else {
            spanSize = 3;
        }
        return spanSize;
    }
}
