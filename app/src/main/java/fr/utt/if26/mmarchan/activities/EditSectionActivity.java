package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);

        // Fetch data from database
        int sectionId = getIntent().getIntExtra("sectionId", 0);
        sectionRepository = new SectionRepository(getApplication());
        section = sectionRepository.getSectionById(sectionId);

        // Get the view elements
        input = findViewById(R.id.section_edit_edit_text);
        button = findViewById(R.id.section_edit_button);
        buttonDelete = findViewById(R.id.section_edit_button_delete);

        // Bind listeners
        input.setText(section.name);
        button.setOnClickListener(v -> onSave());
        buttonDelete.setOnClickListener(v -> onDelete());
    }

    /**
     * Save the section when the confirmation button click is triggered.
     */
    private void onSave() {
        section.name = input.getText().toString();
        sectionRepository.update(section);

        Toast.makeText(this, "Section name has been successfully updated!", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Handle the deletion button: open the deletion activity.
     */
    private void onDelete() {
        Intent intent = new Intent(EditSectionActivity.this, DeleteSectionActivity.class);
        intent.putExtra("sectionId", section.id);
        startActivity(intent);
        finish();
    }
}