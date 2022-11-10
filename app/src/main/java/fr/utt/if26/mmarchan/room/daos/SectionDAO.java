package fr.utt.if26.mmarchan.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.utt.if26.mmarchan.room.models.Section;

@Dao
public interface SectionDAO {
    @Insert
    public void insertAll(Section... sections);

    @Update
    public void update(Section section);

    @Delete
    public void delete(Section section);

    @Query("SELECT * FROM sections")
    public List<Section> getAll();
}
