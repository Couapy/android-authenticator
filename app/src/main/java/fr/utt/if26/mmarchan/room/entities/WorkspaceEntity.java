package fr.utt.if26.mmarchan.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workspaces")
public class WorkspaceEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "database")
    public String database;

    /* Password hash */
    @ColumnInfo(name = "password")
    public String password;

    public WorkspaceEntity() { this.name = "Workspace name"; }

    public WorkspaceEntity(String name, String database, String password) {
        this.name = name;
        this.database = database;
        this.password = password;
    }
}
