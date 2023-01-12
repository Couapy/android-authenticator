package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.databinding.ActivitySignupBinding;
import fr.utt.if26.mmarchan.room.entities.WorkspaceEntity;
import fr.utt.if26.mmarchan.room.repositories.WorkspaceRepository;
import fr.utt.if26.mmarchan.utils.PasswordUtil;

public class SignupActivity extends AppCompatActivity {

    private WorkspaceRepository repository;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        repository = new WorkspaceRepository(getApplication());

        binding.setHandler(this);
    }

    public boolean onSubmit() {
        EditText inputName = findViewById(R.id.signup_input_name);
        EditText inputPassword = findViewById(R.id.signup_input_password);
        String name = inputName.getText().toString();
        String password = inputPassword.getText().toString();
        String hash = PasswordUtil.getHash(password);
        String database = String.valueOf(Calendar.getInstance().getTimeInMillis());

        WorkspaceEntity workspace = new WorkspaceEntity(name, database, hash);
        repository.insertWorkspace(workspace);

        Toast.makeText(this, "The workspace has been successfully created", Toast.LENGTH_SHORT).show();
        finish();

        return true;
    }
}