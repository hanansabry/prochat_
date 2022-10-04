package com.egrobots.prochat.utils;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomBottomSheetFragment extends BottomSheetDialogFragment {

    private View mainLayout;
    private double heightOfScreenPercent;

    public void setMainLayout(View mainLayout, double heightOfScreenPercent) {
        this.mainLayout = mainLayout;
        this.heightOfScreenPercent = heightOfScreenPercent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBottomSheetBehavior();
    }

    protected void setBottomSheetBehavior() {
        BottomSheetBehavior behavior = ((BottomSheetDialog)getDialog()).getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setDraggable(false);
        //set height of sheet dialog to 3/4 screen
        ViewGroup.LayoutParams params = mainLayout.getLayoutParams();
        params.height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * heightOfScreenPercent);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mainLayout.setLayoutParams(params);
        behavior.setPeekHeight(params.height);
    }
}
