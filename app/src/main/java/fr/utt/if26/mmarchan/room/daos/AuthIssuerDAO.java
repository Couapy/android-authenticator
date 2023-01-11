package fr.utt.if26.mmarchan.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;

@Dao
public interface AuthIssuerDAO {
    @Insert
    public void insert(AuthIssuerEntity issuer);

    @Insert
    public void insertAll(AuthIssuerEntity... issuers);

    @Update
    public void update(AuthIssuerEntity issuer);

    @Delete
    public void delete(AuthIssuerEntity issuer);

    @Query("DELETE FROM auth_issuers")
    public void deleteAll();

    @Query("SELECT * FROM auth_issuers")
    public LiveData<List<AuthIssuerEntity>> getAll();

    @Query("SELECT * FROM auth_issuers WHERE id = :id")
    public AuthIssuerEntity getById(int id);
}
