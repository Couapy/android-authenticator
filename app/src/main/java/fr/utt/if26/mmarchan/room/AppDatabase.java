package fr.utt.if26.mmarchan.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.sqlcipher.database.SupportFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.utt.if26.mmarchan.room.daos.AuthIssuerDAO;
import fr.utt.if26.mmarchan.room.daos.SectionDAO;
import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;

@Database(entities = {SectionEntity.class, AuthIssuerEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract SectionDAO sectionDAO();

    public abstract AuthIssuerDAO authIssuerDAO();

    public static void useDatabase(Context context, String databaseName, String password) {
        INSTANCE = Room
                .databaseBuilder(context, AppDatabase.class, databaseName)
                .openHelperFactory(new SupportFactory(password.getBytes(StandardCharsets.UTF_8)))
                .allowMainThreadQueries()
                .build();
    }

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            // Should never happened because useDatabase is always called, in main activity, before this
            useDatabase(context, "default", "password");
        }
        return INSTANCE;
    }
}
