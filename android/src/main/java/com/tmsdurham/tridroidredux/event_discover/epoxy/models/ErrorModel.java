package com.tmsdurham.tridroidredux.event_discover.epoxy.models;

import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.tmsdurham.tridroidredux.R;


/**
 * Created by billyparrish on 8/16/17 for Shortlist-Android.
 */

public class ErrorModel extends EpoxyModelWithHolder<ErrorModel.ErrorHolder> {
    @EpoxyAttribute
    View.OnClickListener clickListener;

    @Override
    protected ErrorHolder createNewHolder() {
        return new ErrorHolder();
    }

    @Override
    public void bind(ErrorHolder holder) {
        holder.errorText.setOnClickListener(clickListener);
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.error_view;
    }

   public static class ErrorHolder extends EpoxyHolder {
        TextView errorText;

        @Override
        protected void bindView(View view) {
            errorText = (TextView) view.findViewById(R.id.tryAgain);
        }
    }
}
