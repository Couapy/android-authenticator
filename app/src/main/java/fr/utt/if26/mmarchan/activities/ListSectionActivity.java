package fr.utt.if26.mmarchan.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.net.URI;
import java.net.URISyntaxException;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.activities.adapters.SectionListAdapter;
import fr.utt.if26.mmarchan.databinding.ActivityListSectionBinding;
import fr.utt.if26.mmarchan.room.AppDatabase;
import fr.utt.if26.mmarchan.room.entities.WorkspaceEntity;
import fr.utt.if26.mmarchan.room.repositories.WorkspaceRepository;
import fr.utt.if26.mmarchan.room.viewmodels.SectionViewModel;

public class ListSectionActivity extends AppCompatActivity {

    private ActivityListSectionBinding binding;
    private WorkspaceRepository repository;
    private WorkspaceEntity workspace;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;
    private ScanOptions barcodeOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_section);
        repository = new WorkspaceRepository(getApplication());

        setupDatabaseAccess();
        setupQRCodeScanner();
        setTitle("Workspace: " + workspace.name);

        SectionViewModel viewModel = new ViewModelProvider(this).get(SectionViewModel.class);
        final SectionListAdapter adapter = new SectionListAdapter(new SectionListAdapter.SectionDiff(), this);
        viewModel.getAllSections().observe(this, sections -> {
            adapter.submitList(sections);
        });
        binding.sectionListRecyclerView.setAdapter(adapter);
    }

    private void setupDatabaseAccess() {
        int workspaceId = getIntent().getIntExtra("workspaceId", 0);
        workspace = repository.getWorkspaceById(workspaceId);
        String password = getIntent().getStringExtra("password");
        AppDatabase.useDatabase(this, workspace.database, password);
    }

    private void setupQRCodeScanner() {
        barcodeLauncher = registerForActivityResult(
                new ScanContract(),
                result -> {
                    if (result.getContents() != null) {

                        String decodedURI = result.getContents();
                        try {
                            URI uri = new URI(decodedURI);
                            if (!uri.getScheme().equals("otpauth") && !uri.getScheme().equals("totp")) {
                                throw new Exception("Invalid link");
                            }
                            Uri query = Uri.parse(decodedURI);
                            String name = query.getQueryParameter("issuer");
                            String user = null;
                            String token = query.getQueryParameter("secret");
                            String[] hostname = uri.getPath().split(":");
                            if (hostname.length >= 2) {
                                user = hostname[1];
                            }

                            Intent intent = new Intent(ListSectionActivity.this, AddCodeActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("user", user);
                            intent.putExtra("token", token);
                            startActivity(intent);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        barcodeOptions = new ScanOptions();
        barcodeOptions.setPrompt("Scan a QR Code");
        barcodeOptions.setBeepEnabled(false);
        barcodeOptions.setBarcodeImageEnabled(true);
        barcodeOptions.setOrientationLocked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_add_section:
                addSection();
                return true;
            case R.id.main_menu_add_code:
                addCode();
                return true;
            case R.id.main_menu_scan_qr_code:
                scanQRCode();
                return true;
            case R.id.main_menu_delete_workspace:
                deleteWorkspace();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addSection() {
        startActivity(new Intent(ListSectionActivity.this, AddSectionActivity.class));
    }

    private void addCode() {
        startActivity(new Intent(ListSectionActivity.this, AddCodeActivity.class));
    }

    private void scanQRCode() {
        barcodeLauncher.launch(barcodeOptions);
    }

    private void deleteWorkspace() {
        Intent intent = new Intent(ListSectionActivity.this, DeleteWorkspaceActivity.class);
        intent.putExtra("workspaceId", workspace.id);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            startActivity(new Intent(ListSectionActivity.this, SigningActivity.class));
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
