package pl.edu.uwr.pum.lista3;

import androidx.recyclerview.widget.RecyclerView;

import pl.edu.uwr.pum.lista3.databinding.ItemLayoutBinding;

public class ViewHolderC extends RecyclerView.ViewHolder {
    private final ItemLayoutBinding binding;
    public ViewHolderC(ItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

//        binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
    }

    public void bind(Exercise item) {
        binding.subjectName.setText("Exercise " + item.index);
        binding.averageGrade.setText("");
        binding.exerciseCount.setText(item.content);
        binding.listCount.setText("Pkt. " + item.points);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
