package fr.utt.if26.mmarchan.activities.adapters;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.utt.if26.mmarchan.activities.EditSectionActivity;
import fr.utt.if26.mmarchan.databinding.FragmentSectionItemBinding;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;
import fr.utt.if26.mmarchan.room.viewmodels.AuthIssuerViewModel;

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

    private AppCompatActivity context;

    public SectionListAdapter(@NonNull DiffUtil.ItemCallback<SectionEntity> diffCallback, AppCompatActivity ctx) {
        super(diffCallback);
        context = ctx;
    }

    @Override
    public SectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentSectionItemBinding binding = FragmentSectionItemBinding.inflate(inflater, parent, false);
        return new SectionHolder(binding);
    }

    /**
     * Bind section and click handler (this adapter) to the fragment using data-binding.
     * Note: it also refresh all children (code provider fragments) before display issuers information.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(SectionHolder holder, int position) {
        SectionEntity section = getItem(position);

        AuthIssuerViewModel viewModel = new ViewModelProvider(context).get(AuthIssuerViewModel.class);
        final AuthIssuerListAdapter childAdapter = new AuthIssuerListAdapter(new AuthIssuerListAdapter.AuthIssuerDiff(), context);
        viewModel.getIssuersBySection(section.id).observe(context, issuers -> {
            childAdapter.submitList(issuers);
        });

        holder.binding.setSection(section);
        holder.binding.setHandler(this);
        holder.binding.sectionItemRecyclerView.setAdapter(childAdapter);
        holder.binding.executePendingBindings();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                childAdapter.notifyDataSetChanged();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
    }

    /**
     * Handle the click action on the section: it just open the edit section activity.
     *
     * @param sectionId
     * @return
     */
    public boolean onClick(int sectionId) {
        Intent intent = new Intent(context, EditSectionActivity.class);
        intent.putExtra("sectionId", sectionId);
        context.startActivity(intent);
        return true;
    }

}

