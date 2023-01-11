package fr.utt.if26.mmarchan.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import fr.utt.if26.mmarchan.activities.holders.SectionHolder;
import fr.utt.if26.mmarchan.databinding.FragmentSectionItemBinding;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;

public class SectionListAdapter extends ListAdapter<SectionEntity, SectionHolder> {

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

    private Context context;

    public SectionListAdapter(@NonNull DiffUtil.ItemCallback<SectionEntity> diffCallback, Context ctx) {
        super(diffCallback);
        context = ctx;
    }

    @Override
    public SectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentSectionItemBinding binding = FragmentSectionItemBinding.inflate(inflater, parent, false);
        return new SectionHolder(binding);
    }

    @Override
    public void onBindViewHolder(SectionHolder holder, int position) {
        holder.bind(getItem(position));
    }

}

