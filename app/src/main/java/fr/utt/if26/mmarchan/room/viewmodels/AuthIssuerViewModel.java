package fr.utt.if26.mmarchan.room.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;
import fr.utt.if26.mmarchan.room.repositories.AuthIssuerRepository;

public class AuthIssuerViewModel extends AndroidViewModel {

    private AuthIssuerRepository mRepository;

    private final LiveData<List<AuthIssuerEntity>> issuers;

    public AuthIssuerViewModel (Application application) {
        super(application);
        mRepository = new AuthIssuerRepository(application);
        issuers = mRepository.getAllAuthIssuers();
    }

    public void insert(AuthIssuerEntity issuer) { mRepository.insertIssuer(issuer); }

    public LiveData<List<AuthIssuerEntity>> getIssuersBySection(int sectionId) {
        return mRepository.getIssuersBySection(sectionId);
    }
}
