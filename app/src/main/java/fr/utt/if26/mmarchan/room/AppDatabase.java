package fr.utt.if26.mmarchan.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.utt.if26.mmarchan.room.daos.AuthIssuerDAO;
import fr.utt.if26.mmarchan.room.daos.SectionDAO;
import fr.utt.if26.mmarchan.room.models.AuthIssuer;
import fr.utt.if26.mmarchan.room.models.Section;

@Database(entities = {Section.class, AuthIssuer.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public abstract SectionDAO sectionDAO();

    public abstract AuthIssuerDAO authIssuerDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                .databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                // recreate the database if necessary
                .fallbackToDestructiveMigration()
                .build();
        }
        return instance;
    }
}
