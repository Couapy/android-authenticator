package fr.utt.if26.mmarchan.room.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.mmarchan.room.entities.SectionEntity;
import fr.utt.if26.mmarchan.room.repositories.SectionRepository;

public class SectionViewModel extends AndroidViewModel {

    private SectionRepository mRepository;

    private final LiveData<List<SectionEntity>> sections;

    public SectionViewModel (Application application) {
        super(application);
        mRepository = new SectionRepository(application);
        sections = mRepository.getAllSections();
    }

    public LiveData<List<SectionEntity>> getAllSections() { return sections; }

    public void insert(SectionEntity section) { mRepository.insertSection(section); }
}
