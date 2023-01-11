package fr.utt.if26.mmarchan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import fr.utt.if26.mmarchan.R;
import fr.utt.if26.mmarchan.activities.adapters.SectionListAdapter;
import fr.utt.if26.mmarchan.databinding.ActivityListSectionBinding;
import fr.utt.if26.mmarchan.room.viewmodels.SectionViewModel;

public class ListSectionActivity extends AppCompatActivity {

    private ActivityListSectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_section);

        SectionViewModel viewModel = new ViewModelProvider(this).get(SectionViewModel.class);
        final SectionListAdapter adapter = new SectionListAdapter(new SectionListAdapter.SectionDiff(), this);
        viewModel.getAllSections().observe(this, sections -> {
            adapter.submitList(sections);
        });
        binding.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
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
        //Intent intent = new Intent(MainActivity.this, DeleteSectionActivity.class);
        //intent.putExtra("sectionId", 3);
        //startActivity(intent);

        // Intent intent = new Intent(MainActivity.this, DeleteCodeActivity.class);
        // intent.putExtra("authIssuerId", 5);
        // startActivity(intent);

        Intent intent = new Intent(ListSectionActivity.this, EditSectionActivity.class);
        intent.putExtra("sectionId", 4);
        startActivity(intent);
    }
}
