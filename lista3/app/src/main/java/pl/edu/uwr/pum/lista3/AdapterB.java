package pl.edu.uwr.pum.lista3;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.uwr.pum.lista3.databinding.ItemLayoutBinding;

public class AdapterB extends RecyclerView.Adapter<ViewHolderB> {
    private ArrayList<SubjectSummary> subjectSummaries;

    public AdapterB(ArrayList<SubjectSummary> wordList){this.subjectSummaries = wordList;
    }

//    private final ViewHolderB.OnItemClickListener onItemClickListener;
    public AdapterB(ArrayList<SubjectSummary> wordList, ViewHolderB.OnItemClickListener onItemClickListener) {
        this.subjectSummaries = wordList;
    }

    @NonNull
    @Override
    public ViewHolderB onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderB(ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderB holder, int position) {
        SubjectSummary currentItem = subjectSummaries.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return subjectSummaries.size();
    }
}