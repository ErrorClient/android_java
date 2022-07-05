package com.android.javaututapp.presentation.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.data.models.ImageDataModel;
import com.android.javaututapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    public List<ImageDataModel> imageList;
    private final Fragment context;

    TableAdapter(List<ImageDataModel> imageList, Fragment context) {
        this.imageList = imageList;
        this.context = context;
    }

    private OnImageClickListener imageClickListener;
    public ImageView clickedImageView;

    interface OnImageClickListener {
        void onImageClick(int position);
    }

    void setOnImageClickListener(OnImageClickListener listener) {
        imageClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_table, parent, false);

        return new ViewHolder(view, imageClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = imageList.get(position).url;

        Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.empty)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void addItems(List<ImageDataModel> newImageList) {

        ItemDiffUtilCall diffCallback = new ItemDiffUtilCall(this.imageList, newImageList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback, false);
        diffResult.dispatchUpdatesTo(this);

        this.imageList = newImageList;
    }

    private static class ItemDiffUtilCall extends DiffUtil.Callback {

        private final List<ImageDataModel> oldItemList;
        private final List<ImageDataModel> newItemList;

        ItemDiffUtilCall (
                List<ImageDataModel> oldItemList,
                List<ImageDataModel> newItemList
        ) {
            this.oldItemList = oldItemList;
            this.newItemList = newItemList;
        }

        @Override
        public int getOldListSize() {
            return oldItemList.size();
        }

        @Override
        public int getNewListSize() {
            return newItemList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            String oldId =  oldItemList.get(oldItemPosition).id;
            String newId =  newItemList.get(newItemPosition).id;

            return oldId.equals(newId);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            String oldUrl =  oldItemList.get(oldItemPosition).url;
            String newUrl =  newItemList.get(newItemPosition).url;

            return oldUrl.equals(newUrl);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final OnImageClickListener listener;
        public ViewHolder(@NonNull View itemView, OnImageClickListener listener) {
            super(itemView);
            this.listener = listener;
        }

        ImageView imageView = itemView.findViewById(R.id.small_image);

        {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedImageView = imageView;
                    imageView.setTransitionName(context.getString(R.string.transition_name));
                    listener.onImageClick(getAdapterPosition());
                }
            };
            imageView.setOnClickListener(onClickListener);
        }
    }
}
