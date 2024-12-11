package pl.edu.uwr.pum.lista3;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.uwr.pum.lista3.databinding.ItemLayoutBinding;

public class AdapterC extends RecyclerView.Adapter<ViewHolderC> {

    private ArrayList<Exercise> exercises;

    public AdapterC(ExerciseList wordList) {
        this.exercises = wordList.getExercises();
    }

//    private final ViewHolderC.OnItemClickListener onItemClickListener;
    public AdapterC(ExerciseList wordList, ViewHolderC.OnItemClickListener onItemClickListener) {
        this.exercises = wordList.getExercises();
//        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderC(ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderC holder, int position) {
        Exercise currentItem = exercises.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
}