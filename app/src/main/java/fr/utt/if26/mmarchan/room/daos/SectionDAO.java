package fr.utt.if26.mmarchan.room.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.mmarchan.room.entities.SectionEntity;

@Dao
public interface SectionDAO {
    @Insert
    public void insert(SectionEntity section);

    @Insert
    public void insertAll(SectionEntity... sections);

    @Update
    public void update(SectionEntity section);

    @Delete
    public void delete(SectionEntity section);

    @Query("DELETE FROM sections")
    public void deleteAll();

    @Query("SELECT * FROM sections WHERE id = :id")
    public SectionEntity getById(int id);

    @Query("SELECT * FROM sections")
    public LiveData<List<SectionEntity>> getAll();
}
