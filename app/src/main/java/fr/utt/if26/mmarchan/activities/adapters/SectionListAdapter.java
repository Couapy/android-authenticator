package fr.utt.if26.mmarchan.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.utt.if26.mmarchan.activities.EditSectionActivity;
import fr.utt.if26.mmarchan.databinding.FragmentSectionItemBinding;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;

public class SectionListAdapter extends ListAdapter<SectionEntity, SectionListAdapter.SectionHolder> {

    public class SectionHolder extends RecyclerView.ViewHolder {
        public final FragmentSectionItemBinding binding;

        public SectionHolder(@NonNull FragmentSectionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public static class SectionDiff extends DiffUtil.ItemCallback<SectionEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull SectionEntity oldSection, @NonNull SectionEntity newSection) {
            return oldSection == newSection;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SectionEntity oldItem, @NonNull SectionEntity newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }

    public class SectionListClickListener {
        public boolean onClick(int sectionId) {
            Intent intent = new Intent(context, EditSectionActivity.class);
            intent.putExtra("sectionId", sectionId);
            context.startActivity(intent);
            return true;
        }
    }

    private Context context;
    private final SectionListClickListener handler;

    public SectionListAdapter(@NonNull DiffUtil.ItemCallback<SectionEntity> diffCallback, Context ctx) {
        super(diffCallback);
        context = ctx;
        handler = new SectionListClickListener();
    }

    @Override
    public SectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentSectionItemBinding binding = FragmentSectionItemBinding.inflate(inflater, parent, false);
        return new SectionHolder(binding);
    }

    @Override
    public void onBindViewHolder(SectionHolder holder, int position) {
        SectionEntity section = getItem(position);
        holder.binding.setSection(section);
        holder.binding.setHandler(handler);
        holder.binding.executePendingBindings();
    }

}

