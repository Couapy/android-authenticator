package fr.utt.if26.mmarchan.activities.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.utt.if26.mmarchan.activities.DeleteCodeActivity;
import fr.utt.if26.mmarchan.activities.ListSectionActivity;
import fr.utt.if26.mmarchan.databinding.FragmentAuthIssuerItemBinding;
import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;
import fr.utt.if26.mmarchan.room.repositories.AuthIssuerRepository;

/**
 * Adapter for RecyclerView using fragment_auth_issuer_item layout.
 */
public class AuthIssuerListAdapter extends ListAdapter<AuthIssuerEntity, AuthIssuerListAdapter.AuthIssuerHolder> {

    public class AuthIssuerHolder extends RecyclerView.ViewHolder {
        private FragmentAuthIssuerItemBinding binding;

        public AuthIssuerHolder(@NonNull FragmentAuthIssuerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class AuthIssuerDiff extends DiffUtil.ItemCallback<AuthIssuerEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull AuthIssuerEntity oldItem, @NonNull AuthIssuerEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AuthIssuerEntity oldItem, @NonNull AuthIssuerEntity newItem) {
            return oldItem.id == newItem.id;
        }
    }

    private final AppCompatActivity context;
    private final AuthIssuerRepository repository;

    public AuthIssuerListAdapter(@NonNull DiffUtil.ItemCallback<AuthIssuerEntity> diffCallback, AppCompatActivity context) {
        super(diffCallback);
        this.context = context;
        this.repository = new AuthIssuerRepository(context.getApplication());
    }

    @Override
    public AuthIssuerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        /* Note: Using FragmentAuthIssuerItemBinding defines we are using the fragment_auth_issuer_item layout */
        FragmentAuthIssuerItemBinding binding = FragmentAuthIssuerItemBinding.inflate(inflater, parent, false);
        return new AuthIssuerHolder(binding);
    }

    /**
     * Bind issuer and click handler (this adapter) to the fragment using data-binding.
     * Note: it also refresh the code before display the issuer informations.
     *
     * @param position
     */
    @Override
    public void onBindViewHolder(AuthIssuerHolder holder, int position) {
        AuthIssuerEntity issuer = getItem(position);
        issuer.refreshCode();
        holder.binding.setIssuer(issuer);
        holder.binding.setHandler(this);
        holder.binding.executePendingBindings();
    }

    /**
     * Handle the click on the item: it just copies the current code to the clipboard.
     *
     * @param issuerId issuer identifier
     * @return true
     */
    public boolean onClick(int issuerId) {
        AuthIssuerEntity issuer = repository.getAuthIssuerById(issuerId);
        issuer.refreshCode();
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", issuer.code);
        clipboard.setPrimaryClip(clip);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /**
     * Handle the long click on the item: it opens the delete code activity.
     *
     * @param issuerId issuer identifier
     * @return true
     */
    public boolean onLongClick(int issuerId) {
        Intent intent = new Intent(context, DeleteCodeActivity.class);
        intent.putExtra("issuerId", issuerId);
        context.startActivity(intent);
        return true;
    }

}

