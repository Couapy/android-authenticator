package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;
import fr.utt.if26.mmarchan.room.repositories.SectionRepository;

public class DeleteSectionActivity extends AppCompatActivity {

    SectionRepository sectionRepository = new SectionRepository(getApplication());
    SectionEntity section;

    TextView textView;
    CheckBox checkBox;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_section);

        int sectionId = getIntent().getIntExtra("sectionId", 0);
        section = sectionRepository.getSectionById(sectionId);

        textView = findViewById(R.id.section_delete_text_view);
        checkBox = findViewById(R.id.section_delete_checkbox);
        button = findViewById(R.id.section_delete_button);

        textView.setText(section.name);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                button.setEnabled(isChecked);
            }
        });
        button.setOnClickListener(v -> onDelete());
    }

    private void onDelete() {
        sectionRepository.deleteSection(section);
        Toast.makeText(this, "The section has been successfully deleted.", Toast.LENGTH_SHORT).show();
        finish();
    }
}