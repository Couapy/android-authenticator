package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;
import fr.utt.if26.mmarchan.room.repositories.AuthIssuerRepository;

public class DeleteCodeActivity extends AppCompatActivity {

    AuthIssuerRepository authIssuerRepository = new AuthIssuerRepository(getApplication());
    AuthIssuerEntity authIssuer;

    TextView textView;
    CheckBox checkBox;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_code);

        int authIssuerId = getIntent().getIntExtra("authIssuerId", 0);
        authIssuer = authIssuerRepository.getAuthIssuerById(authIssuerId);

        textView = findViewById(R.id.code_delete_text_view);
        checkBox = findViewById(R.id.code_delete_checkbox);
        button = findViewById(R.id.code_delete_button);

        textView.setText(authIssuer.issuer);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                button.setEnabled(isChecked);
            }
        });
        button.setOnClickListener(v -> onDelete());
    }

    private void onDelete() {
        authIssuerRepository.deleteAuthIssuer(authIssuer);
        Toast.makeText(this, "The issuer has been successfully deleted.", Toast.LENGTH_SHORT).show();
        finish();
    }
}