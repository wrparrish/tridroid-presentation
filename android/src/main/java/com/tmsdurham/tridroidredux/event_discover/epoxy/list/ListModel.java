package com.tmsdurham.tridroidredux.event_discover.epoxy.list;

import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithView;
import com.tmsdurham.tridroidredux.event_discover.epoxy.EpoxyModelRecyclerView;


import java.util.List;

/**
 * Created by alfredovelasco on 7/20/17.
 */
@EpoxyModelClass
public abstract class ListModel extends EpoxyModelWithView<EpoxyModelRecyclerView> {
    @EpoxyAttribute
    List<? extends EpoxyModel<?>> models;

    @Override
    public boolean shouldSaveViewState() {
        return false;
    }

    @Override
    protected EpoxyModelRecyclerView buildView(ViewGroup parent) {
        EpoxyModelRecyclerView epoxyModelRecyclerView = new EpoxyModelRecyclerView(parent.getContext(),
                null,
                new LinearLayoutManager(parent.getContext(), LinearLayoutManager.VERTICAL, false));
        return epoxyModelRecyclerView;
    }

    @Override
    public void bind(EpoxyModelRecyclerView epoxyModelRecyclerView) {
        epoxyModelRecyclerView.setModels(models);
    }
}
