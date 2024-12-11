package pl.edu.uwr.pum.lista3;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.uwr.pum.lista3.databinding.ItemLayoutBinding;
import pl.edu.uwr.pum.lista3.databinding.OneWordBinding;

public class WordListAdapter extends RecyclerView.Adapter<WordViewHolder> {
    private ArrayList<String> wordList;
    private ArrayList<ExerciseList> exerciseLists;

    public WordListAdapter(ArrayList<ExerciseList> wordList){this.exerciseLists = wordList;
        onItemClickListener = null;
    }

    private final WordViewHolder.OnItemClickListener onItemClickListener;
    public WordListAdapter(ArrayList<ExerciseList> wordList, WordViewHolder.OnItemClickListener onItemClickListener) {
        this.exerciseLists = wordList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordViewHolder(ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        ExerciseList currentItem = exerciseLists.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return exerciseLists.size();
    }
}