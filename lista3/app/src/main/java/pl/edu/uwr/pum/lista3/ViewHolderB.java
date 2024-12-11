package pl.edu.uwr.pum.lista3;

import androidx.recyclerview.widget.RecyclerView;

import pl.edu.uwr.pum.lista3.databinding.ItemLayoutBinding;

public class ViewHolderB extends RecyclerView.ViewHolder {
    private final ItemLayoutBinding binding;
    public ViewHolderB(ItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

//        binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
    }

    public void bind(SubjectSummary item) {
        binding.subjectName.setText(item.getSubjectName());
        binding.averageGrade.setText("");
        binding.exerciseCount.setText("List count: " + item.getListCount());
        binding.listCount.setText("Average: " + item.getAverageGrade());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
