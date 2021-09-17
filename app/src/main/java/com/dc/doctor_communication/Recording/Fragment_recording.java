package com.dc.doctor_communication.Recording;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorcommunication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Fragment_recording extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("myapp","녹음탭 열림");
        return inflater.inflate(R.layout.fragment_home,container,false);
    }
}
