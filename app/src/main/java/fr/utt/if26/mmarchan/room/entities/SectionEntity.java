package fr.utt.if26.mmarchan.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Section that contains AutIssuerEntities.
 */
@Entity(tableName = "sections")
public class SectionEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    public SectionEntity() {
        this.name = "Section name";
    }

    public SectionEntity(String name) {
        this.name = name;
    }
}
