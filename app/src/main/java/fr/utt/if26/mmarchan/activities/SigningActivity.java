package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.databinding.ActivitySigningBinding;
import fr.utt.if26.mmarchan.room.entities.WorkspaceEntity;
import fr.utt.if26.mmarchan.room.repositories.WorkspaceRepository;
import fr.utt.if26.mmarchan.utils.PasswordUtil;

public class SigningActivity extends AppCompatActivity {

    private ActivitySigningBinding binding;
    private WorkspaceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signing);
        repository = new WorkspaceRepository(getApplication());

        binding.setHandler(this);
    }

    /**
     * Handle form submission, and open the app if the password if recognized.
     *
     * @return the success
     */
    public boolean onSubmit() {
        EditText input = findViewById(R.id.signin_input_password);
        String password = input.getText().toString();
        String hash = PasswordUtil.getHash(password);

        WorkspaceEntity workspace = repository.getWorkspaceByPassword(hash);
        if (workspace == null) {
            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
            return false;
        }

        Intent intent = new Intent(SigningActivity.this, ListSectionActivity.class);
        intent.putExtra("workspaceId", workspace.id);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();

        return true;
    }

    /**
     * Handle the signup onClick event.
     * It opens the SignupActivity to register a new workspace.
     *
     * @return true
     */
    public boolean onSignup() {
        startActivity(new Intent(SigningActivity.this, SignupActivity.class));
        return true;
    }
}