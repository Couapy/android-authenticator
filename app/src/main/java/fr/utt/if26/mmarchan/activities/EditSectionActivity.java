package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;
import fr.utt.if26.mmarchan.room.repositories.SectionRepository;

public class EditSectionActivity extends AppCompatActivity {

    private SectionRepository sectionRepository;
    private SectionEntity section;

    EditText input;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);

        int sectionId = getIntent().getIntExtra("sectionId", 0);
        sectionRepository = new SectionRepository(getApplication());
        section = sectionRepository.getSectionById(sectionId);

        input = findViewById(R.id.section_edit_edit_text);
        button = findViewById(R.id.section_edit_button);

        input.setText(section.name);
        button.setOnClickListener(v -> onSave());
    }

    private void onSave() {
        section.name = input.getText().toString();
        sectionRepository.update(section);

        Toast.makeText(this, "Section name has been successfully updated!", Toast.LENGTH_SHORT).show();
        finish();
    }
}