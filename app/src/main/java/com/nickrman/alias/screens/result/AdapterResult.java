package com.nickrman.alias.screens.result;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickrman.alias.R;
import com.nickrman.alias.base.App;
import com.nickrman.alias.data.models.ItemAnswer;

import java.util.List;

import io.reactivex.Observable;

public class AdapterResult extends RecyclerView.Adapter<AdapterResult.ViewHolder> {

    private List<ItemAnswer> listItemAnswer;
    private ListItemCallback callback;

    public AdapterResult(List<ItemAnswer> listItemAnswer, ListItemCallback callback) {
        this.listItemAnswer = listItemAnswer;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ItemAnswer item = listItemAnswer.get(position);

        if (item.isAnswer()) {
            holder.backRightAnswer.setBackgroundResource(R.drawable.background_right_answer);
            holder.imageRight.setBackgroundResource(R.drawable.ic_right_answer_visible);
            holder.backWrongAnswer.setBackgroundResource(R.color.colorWhite);
            holder.imageWrong.setBackgroundResource(R.drawable.ic_wrong_answer_unvisible);

        } else {
            holder.backWrongAnswer.setBackgroundResource(R.drawable.background_wrong_answer);
            holder.imageWrong.setBackgroundResource(R.drawable.ic_wrong_answer_visible);
            holder.backRightAnswer.setBackgroundResource(R.color.colorWhite);
            holder.imageRight.setBackgroundResource(R.drawable.ic_right_answer_unvisible);
        }

        holder.word.setText(item.getWord());

    }

    public Observable<List<ItemAnswer>> getListItemAnswer() {
        return Observable.just(listItemAnswer);
    }

    @Override
    public int getItemCount() {
        return listItemAnswer.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView word;
        View backRightAnswer;
        View backWrongAnswer;

        ImageView imageRight;
        ImageView imageWrong;

        public ViewHolder(View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
            backRightAnswer = itemView.findViewById(R.id.background_right_answer);
            backWrongAnswer = itemView.findViewById(R.id.background_wrong_answer);
            imageRight = itemView.findViewById(R.id.image_right_answer);
            imageWrong = itemView.findViewById(R.id.image_wrong_answer);

            backRightAnswer.setOnClickListener(this);
            backWrongAnswer.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.background_right_answer:
                    listItemAnswer.get(getAdapterPosition()).setAnswer(true);
                    break;
                case R.id.background_wrong_answer:
                    listItemAnswer.get(getAdapterPosition()).setAnswer(false);
                    break;
            }
            notifyDataSetChanged();
            int countRightAnswer = 0;
            int countWrongAnswer = 0;

            for (ItemAnswer itemAnswer : listItemAnswer) {
                if (itemAnswer.isAnswer()) countRightAnswer++;
                else countWrongAnswer++;


            }
            int result = countRightAnswer - countWrongAnswer;

            callback.back(result > 0 ? result : 0);
        }
    }
}
