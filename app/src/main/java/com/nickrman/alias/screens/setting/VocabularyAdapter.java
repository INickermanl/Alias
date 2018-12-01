package com.nickrman.alias.screens.setting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.data.models.VocabularyItem;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {

    private List<VocabularyItem> vocabularyItemList;
    private SelectVocabularyCallback callback;

    public VocabularyAdapter(List<VocabularyItem> vocabularyItemList, SelectVocabularyCallback callback) {
        this.vocabularyItemList = vocabularyItemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vocabulary, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VocabularyItem item = vocabularyItemList.get(position);
        Log.d("LOG", item.getNameVocabulary().toString());
        holder.nameVocabulary.setText(item.getNameVocabulary());


        holder.container.setOnClickListener(v -> callback.itemVocabularyCallback(item));
    }

    @Override
    public int getItemCount() {
        return vocabularyItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView nameVocabulary;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container_vocabulary);
            nameVocabulary = itemView.findViewById(R.id.vocabulary_name);
        }
    }
}
