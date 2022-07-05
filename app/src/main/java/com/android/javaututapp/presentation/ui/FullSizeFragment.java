package com.android.javaututapp.presentation.ui;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.javaututapp.R;
import com.android.javaututapp.databinding.FragmentFullSizeBinding;
import com.bumptech.glide.Glide;

public class FullSizeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSharedElementEnterTransition(
                TransitionInflater
                        .from(getContext())
                        .inflateTransition(android.R.transition.move)
        );
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        com.android.javaututapp.databinding.FragmentFullSizeBinding binding = FragmentFullSizeBinding.inflate(inflater, container, false);
        Bundle args = getArguments();
        String urlImage = args != null ? args.getString("Image") : null;

        Glide.with(this)
                .load(urlImage)
                .error(R.drawable.empty)
                .into(binding.fullSizeImage);

        return binding.getRoot();
    }
}
