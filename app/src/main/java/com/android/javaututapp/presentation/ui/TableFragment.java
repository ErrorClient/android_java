package com.android.javaututapp.presentation.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.data.models.ImageDataModel;
import com.android.data.models.StatusLoading;
import com.android.javaututapp.R;
import com.android.javaututapp.databinding.FragmentTableBinding;
import com.android.javaututapp.presentation.vm.ImageViewModel;

import java.util.Collections;
import java.util.List;

public class TableFragment extends Fragment {

    FragmentTableBinding binding;
    TableAdapter adapter;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    Button networkButton;
    TextView tableText;
    GridLayoutManager gridLayoutManager;

    ImageViewModel imageListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTableBinding.inflate(inflater, container, false);
        nestedScrollView = binding.nestedScroll;
        networkButton = binding.networkButton;
        tableText = binding.tableText;

        imageListViewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);

        imageListViewModel.getSize(requireContext());

        int countHorizontal = imageListViewModel.getCountHorizontal();

        recyclerView = binding.recyclerView;
        gridLayoutManager = new GridLayoutManager(requireActivity(), countHorizontal);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new TableAdapter(Collections.emptyList(), this);

        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Observer<StatusLoading> statusObserver = new Observer<StatusLoading>() {
            @Override
            public void onChanged(StatusLoading statusLoading) {
                Log.d("AAA", "statusObserver " + imageListViewModel.status.getValue());

                switch (statusLoading) {
                    case Error:
                        networkButton.setVisibility(View.VISIBLE);
                    case Loading:
                        networkButton.setVisibility(View.GONE);
                    case Success:
                        networkButton.setVisibility(View.GONE);
                }
//                if (statusLoading == StatusLoading.Error) networkButton.setVisibility(View.VISIBLE);
//                else if (statusLoading == StatusLoading.Loading ||
//                statusLoading == StatusLoading.Success) networkButton.setVisibility(View.GONE);
            }
        };

        imageListViewModel.status.observe(getViewLifecycleOwner(), statusObserver);

        Observer<List<ImageDataModel>> imageListObserver = new Observer<List<ImageDataModel>>() {
            @Override
            public void onChanged(List<ImageDataModel> imageList) {
                if (imageList.isEmpty()) {
                    imageListViewModel.addImage(adapter.getItemCount());
                }
                adapter.addItems(imageList);
            }
        };

        imageListViewModel.imageListLiveData.observe(getViewLifecycleOwner(), imageListObserver);
    }

    @Override
    public void onStart() {
        super.onStart();

        View.OnScrollChangeListener onScrollChangeListener =
                new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int scrollY, int i2, int i3) {
                        Log.d("AAA", "OnScrollChangeListener scrollY " + scrollY + "   " + gridLayoutManager.getChildAt(0).getMeasuredHeight() + "   " + gridLayoutManager.getChildAt(adapter.getItemCount()-1).getMeasuredHeight());

                        if (scrollY == view.getHeight()) {
                            Log.d("AAA", "OnScrollChangeListener count " + adapter.getItemCount());
                            imageListViewModel.addImage(adapter.getItemCount());
                        }
                    }
                };

        nestedScrollView.setOnScrollChangeListener(onScrollChangeListener);

        adapter.setOnImageClickListener(new TableAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                String urlImage = adapter.imageList.get(position).url;
                NavDirections action =
                        TableFragmentDirections.actionTableFragmentToFullSizeFragment(urlImage);

                FragmentNavigator.Extras extras =
                        new FragmentNavigator.Extras.Builder()
                                .addSharedElement(adapter.clickedImageView, getString(R.string.transition_name))
                                .build();

                Navigation.findNavController(binding.getRoot()).navigate(action);

//                navController.navigate(action, extras);
            }
        });

        networkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageListViewModel.addImage(adapter.getItemCount());
            }
        });
    }
}
