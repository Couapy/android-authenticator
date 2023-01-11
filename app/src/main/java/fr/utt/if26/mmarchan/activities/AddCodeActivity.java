package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;
import fr.utt.if26.mmarchan.room.repositories.AuthIssuerRepository;
import fr.utt.if26.mmarchan.room.viewmodels.SectionViewModel;

public class AddCodeActivity extends AppCompatActivity {

    private EditText inputProvider;
    private EditText inputUsername;
    private EditText inputToken;
    private Spinner inputSection;
    private Button button;

    ArrayList<String> sectionList = new ArrayList<>();
    ArrayList<Integer> sectionIdList = new ArrayList<>();
    Integer selectedSectionId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_code);

        inputProvider = findViewById(R.id.code_add_input_provider);
        inputUsername = findViewById(R.id.code_add_input_username);
        inputToken = findViewById(R.id.code_add_input_token);
        inputSection = findViewById(R.id.code_add_input_section_spinner);
        button = findViewById(R.id.code_add_button);

        // Create my watchers
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                int lengthProvider = inputProvider.getText().length();
                int lengthToken = inputToken.getText().length();
                button.setEnabled(lengthProvider > 0 && lengthToken > 0);
            }
        };
        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSectionId = sectionIdList.get(position);
                Toast.makeText(AddCodeActivity.this, "Selected id: " + selectedSectionId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                inputSection.setSelection(0);
            }
        };

        // Bind live data to the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputSection.setAdapter(adapter);
        SectionViewModel viewModel = new ViewModelProvider(this).get(SectionViewModel.class);
        viewModel.getAllSections().observe(this, sections -> {
            for (SectionEntity section : sections) {
                sectionList.add(section.name);
                sectionIdList.add(section.id);
            }
            adapter.notifyDataSetChanged();
        });

        // Add listeners
        inputProvider.addTextChangedListener(textWatcher);
        inputToken.addTextChangedListener(textWatcher);
        inputSection.setOnItemSelectedListener(spinnerListener);
        button.setOnClickListener(v -> onSubmit());
    }

    private void onSubmit() {
        AuthIssuerEntity issuer = new AuthIssuerEntity();
        issuer.issuer = inputProvider.getText().toString();
        issuer.user = inputUsername.getText().toString();
        issuer.secret = inputToken.getText().toString();
        issuer.sectionId = selectedSectionId;

        AuthIssuerRepository authIssuerRepository = new AuthIssuerRepository(getApplication());
        authIssuerRepository.insertIssuer(issuer);

        Toast.makeText(AddCodeActivity.this, "Issuer successfully created!", Toast.LENGTH_SHORT).show();
        finish();
    }
}

