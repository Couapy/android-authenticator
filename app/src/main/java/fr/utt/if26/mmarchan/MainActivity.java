package fr.utt.if26.mmarchan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.utt.if26.mmarchan.room.AppDatabase;
import fr.utt.if26.mmarchan.room.daos.AuthIssuerDAO;
import fr.utt.if26.mmarchan.room.daos.SectionDAO;
import fr.utt.if26.mmarchan.room.models.AuthIssuer;

public class MainActivity extends AppCompatActivity {

    AppDatabase database;

    private class AuthIssuerHolder extends RecyclerView.ViewHolder  {
        @NonNull
        private final View view;

        public AuthIssuerHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void display(@NonNull AuthIssuer issuer) {
            TextView providerTextView = view.findViewById(R.id.code_provider_text_view);
            TextView codeTextView = view.findViewById(R.id.code_code_text_view);
            ProgressBar progressBar = view.findViewById(R.id.code_progress_bar);

            if (issuer.user != null && issuer.user.length() > 0) {
                providerTextView.setText(issuer.issuer + " (" + issuer.user + ")");
            } else {
                providerTextView.setText(issuer.issuer);
            }
            codeTextView.setText(issuer.getTOTPCode());
            progressBar.setProgress(getValidSeconds());
        }

        private int getValidSeconds() {
            return 30 - (Calendar.getInstance().get(Calendar.SECOND) % 30);
        }
    }

    private class AuthIssuerAdapter extends RecyclerView.Adapter<AuthIssuerHolder> {
        private final List<AuthIssuer> issuers;

        AuthIssuerAdapter(List<AuthIssuer> issuers) {
            this.issuers = issuers;
        }

        @NonNull
        @Override
        public AuthIssuerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.code_provider_item, parent, false);
            return new AuthIssuerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AuthIssuerHolder holder, int position) {
            holder.display(this.issuers.get(position));
        }

        @Override
        public int getItemCount() {
            return this.issuers.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get items from database.
        database = AppDatabase.getInstance(getApplicationContext());
        SectionDAO sectionDAO = database.sectionDAO();
        AuthIssuerDAO authIssuerDAO = database.authIssuerDAO();

        List<AuthIssuer> issuers = authIssuerDAO.getAll();
        /* if (issuers.size() == 0) {
            authIssuerDAO.insert(new AuthIssuer("Github", "Couapy", "JBSWY3DPEHPK3PXP"));
            authIssuerDAO.insert(new AuthIssuer("Gitlab", "Couapy", "JBSWY3DPEHPK3PXP"));
            authIssuerDAO.insert(new AuthIssuer("Pypi", "Couapy", "JBSWY3DPEHPK3PXP"));
            issuers = authIssuerDAO.getAll();
        } */

        // Display items into the recycler.
        RecyclerView recycler = findViewById(R.id.main_codes_recycler_view);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        AuthIssuerAdapter adapter = new AuthIssuerAdapter(issuers);
        recycler.setLayoutManager(layout);
        recycler.setAdapter(adapter);

        // Force the recycler to be revalidated every second.
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