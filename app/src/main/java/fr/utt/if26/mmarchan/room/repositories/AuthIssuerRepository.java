package fr.utt.if26.mmarchan.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.mmarchan.room.AppDatabase;
import fr.utt.if26.mmarchan.room.daos.AuthIssuerDAO;
import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;

public class AuthIssuerRepository {

    private AuthIssuerDAO mAuthIssuerDAO;
    private LiveData<List<AuthIssuerEntity>> mAllAuthIssuers;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public AuthIssuerRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mAuthIssuerDAO = db.authIssuerDAO();
        mAllAuthIssuers = mAuthIssuerDAO.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<AuthIssuerEntity>> getAllAuthIssuers() {
        return mAllAuthIssuers;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertIssuer(AuthIssuerEntity section) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAuthIssuerDAO.insert(section);
        });
    }

    public AuthIssuerEntity getAuthIssuerById(int authIssuerId) {
        return mAuthIssuerDAO.getById(authIssuerId);
    }

    public void deleteAuthIssuer(AuthIssuerEntity issuer) {
        mAuthIssuerDAO.delete(issuer);
    }

    public LiveData<List<AuthIssuerEntity>> getIssuersBySection(int sectionId) {
        return mAuthIssuerDAO.getBySection(sectionId);
    }
}