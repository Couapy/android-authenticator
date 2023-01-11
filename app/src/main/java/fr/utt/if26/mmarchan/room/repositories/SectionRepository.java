package fr.utt.if26.mmarchan.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.mmarchan.room.AppDatabase;
import fr.utt.if26.mmarchan.room.daos.SectionDAO;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;

public class SectionRepository {

    private SectionDAO mSectionDAO;
    private LiveData<List<SectionEntity>> mAllSections;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public SectionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mSectionDAO = db.sectionDAO();
        mAllSections = mSectionDAO.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<SectionEntity>> getAllSections() {
        return mAllSections;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertSection(SectionEntity section) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mSectionDAO.insert(section);
        });
    }

    public SectionEntity getSectionById(int id) {
        return mSectionDAO.getById(id);
    }

    public void deleteSection(SectionEntity section) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mSectionDAO.delete(section);
        });
    }

    public void update(SectionEntity section) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mSectionDAO.update(section);
        });
    }

}