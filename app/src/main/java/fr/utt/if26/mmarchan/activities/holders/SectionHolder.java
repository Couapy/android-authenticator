package fr.utt.if26.mmarchan.activities.holders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.utt.if26.mmarchan.databinding.FragmentSectionItemBinding;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;

public class SectionHolder extends RecyclerView.ViewHolder {

    private final FragmentSectionItemBinding binding;

    public SectionHolder(@NonNull FragmentSectionItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(SectionEntity section) {
        binding.setSection(section);
        binding.executePendingBindings();
    }

}