package fr.utt.if26.mmarchan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private class CodeProviderHolder extends RecyclerView.ViewHolder  {
        @NonNull
        private final View view;

        public CodeProviderHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void display(@NonNull CodeProvider provider) {
            TextView providerTextView = view.findViewById(R.id.code_provider_text_view);
            TextView codeTextView = view.findViewById(R.id.code_code_text_view);
            ProgressBar progressBar = view.findViewById(R.id.code_progress_bar);

            if (provider.getUser().length() > 0) {
                providerTextView.setText(provider.getIssuer() + " (" + provider.getUser() + ")");
            } else {
                providerTextView.setText(provider.getIssuer());
            }
            codeTextView.setText(provider.getTOTPCode());
            progressBar.setProgress(provider.getValidSeconds());
        }
    }

    private class CodeProviderAdapter extends RecyclerView.Adapter<CodeProviderHolder> {
        private final ArrayList<CodeProvider> codeProviders;

        CodeProviderAdapter(ArrayList<CodeProvider> provider) {
            codeProviders = provider;
        }

        @NonNull
        @Override
        public CodeProviderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.code_provider_item, parent, false);
            return new CodeProviderHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CodeProviderHolder holder, int position) {
            holder.display(this.codeProviders.get(position));
        }

        @Override
        public int getItemCount() {
            return this.codeProviders.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<CodeProvider> providers = new ArrayList<>();
        providers.add(new CodeProvider("Github", "Couapy", "JBSWY3DPEHPK3PXP"));
        providers.add(new CodeProvider("Gitlab", "Couapy", "JBSWY3DPEHPK3PXP"));
        providers.add(new CodeProvider("Pypi", "Couapy", "JBSWY3DPEHPK3PXP"));

        RecyclerView recycler = findViewById(R.id.main_codes_recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        CodeProviderAdapter adapter = new CodeProviderAdapter(providers);
        recycler.setLayoutManager(layout);
        recycler.setAdapter(adapter);


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recycler.setAdapter(null);
                recycler.setLayoutManager(null);
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(layout);
                adapter.notifyDataSetChanged();

                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
    }
}