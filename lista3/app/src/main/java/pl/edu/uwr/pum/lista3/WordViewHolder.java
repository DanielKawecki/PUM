package pl.edu.uwr.pum.lista3;

import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.uwr.pum.lista3.databinding.ItemLayoutBinding;
import pl.edu.uwr.pum.lista3.databinding.OneWordBinding;

public class WordViewHolder  extends RecyclerView.ViewHolder {
    private final ItemLayoutBinding binding;
    public WordViewHolder(ItemLayoutBinding binding, OnItemClickListener onItemClickListener) {
        super(binding.getRoot());
        this.binding = binding;

        binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
    }

    public void bind(ExerciseList item) {
        binding.subjectName.setText(item.getSubject().name);
        binding.averageGrade.setText("Grade: " + item.getGrade());
        binding.exerciseCount.setText("Exercise count: " + item.getExercises().size());
        binding.listCount.setText("List " + item.getIndex());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
