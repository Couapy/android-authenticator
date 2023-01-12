package fr.utt.if26.mmarchan.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import fr.utt.if26.mmarchan.room.entities.WorkspaceEntity;

@Dao
public interface WorkspaceDAO {
    @Insert
    public void insert(WorkspaceEntity workspace);

    @Query("SELECT * FROM workspaces WHERE id = :id")
    public WorkspaceEntity getById(int id);

    @Query("SELECT * FROM workspaces WHERE password = :password")
    public WorkspaceEntity getByPassword(String password);

    @Update
    public void update(WorkspaceEntity workspace);

    @Delete
    public void delete(WorkspaceEntity workspace);
}
