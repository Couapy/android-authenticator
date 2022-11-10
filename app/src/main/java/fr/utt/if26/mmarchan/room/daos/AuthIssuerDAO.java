package fr.utt.if26.mmarchan.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.mmarchan.room.models.AuthIssuer;
import fr.utt.if26.mmarchan.room.models.Section;

@Dao
public interface AuthIssuerDAO {
    @Insert
    public void insert(AuthIssuer issuer);

    @Insert
    public void insertAll(AuthIssuer... issuers);

    @Update
    public void update(AuthIssuer issuer);

    @Delete
    public void delete(AuthIssuer issuer);

    @Query("SELECT * FROM auth_issuers")
    public List<AuthIssuer> getAll();
}
