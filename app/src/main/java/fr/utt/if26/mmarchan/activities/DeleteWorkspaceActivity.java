package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.room.entities.WorkspaceEntity;
import fr.utt.if26.mmarchan.room.repositories.WorkspaceRepository;

public class DeleteWorkspaceActivity extends AppCompatActivity {

    WorkspaceRepository workspaceRepository = new WorkspaceRepository(getApplication());
    WorkspaceEntity workspace;

    TextView textView;
    CheckBox checkBox;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_workspace);

        int workspaceId = getIntent().getIntExtra("workspaceId", 0);
        workspace = workspaceRepository.getWorkspaceById(workspaceId);

        textView = findViewById(R.id.workspace_delete_text_view);
        checkBox = findViewById(R.id.workspace_delete_checkbox);
        button = findViewById(R.id.workspace_delete_button);

        textView.setText(workspace.name);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                button.setEnabled(isChecked);
            }
        });
        button.setOnClickListener(v -> onDelete());
    }

    /**
     * Handle the deletion confirmation: delete the entity from the database.
     */
    private void onDelete() {
        workspaceRepository.deleteWorkspace(workspace);
        Toast.makeText(this, "The workspace has been successfully deleted.", Toast.LENGTH_SHORT).show();
        setResult(0);
        finish();
    }
}
