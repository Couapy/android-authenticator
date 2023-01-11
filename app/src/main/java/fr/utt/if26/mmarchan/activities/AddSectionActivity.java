package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;
import fr.utt.if26.mmarchan.room.repositories.SectionRepository;

public class AddSectionActivity extends AppCompatActivity {

    private EditText input;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_section);

        input = findViewById(R.id.section_add_edit_text);
        button = findViewById(R.id.section_add_button);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = input.getText().length();
                button.setEnabled(length > 0);
            }
        });
        button.setOnClickListener(v -> onSubmit());
    }

    private void onSubmit() {
        String value = input.getText().toString();
        SectionEntity section = new SectionEntity(value);
        SectionRepository sectionRepository = new SectionRepository(getApplication());
        sectionRepository.insertSection(section);

        Toast.makeText(AddSectionActivity.this, "Issuer successfully created!", Toast.LENGTH_SHORT).show();
        finish();
    }
}