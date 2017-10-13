package com.tmsdurham.tridroidredux.event_discover.epoxy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.SimpleEpoxyController;

import java.util.List;

/**
 * Created by alfredovelasco on 8/9/17.
 */

public class EpoxyModelRecyclerView extends RecyclerView {
    private final RecyclerView.LayoutManager layoutManager;
    private SimpleEpoxyController controller;

    public EpoxyModelRecyclerView(Context context, @Nullable AttributeSet attrs, RecyclerView.LayoutManager layoutManager) {
        super(context, attrs);

        this.layoutManager = layoutManager;
        setLayoutManager(this.layoutManager);
    }

    public void setModels(List<? extends EpoxyModel<?>> models){
        if (controller == null) {
            controller = new SimpleEpoxyController();
            controller.setSpanCount(1);
            setAdapter(controller.getAdapter());
        }
        controller.setModels(models);
    }
}
