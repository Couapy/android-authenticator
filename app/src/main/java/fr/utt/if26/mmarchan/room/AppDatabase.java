package fr.utt.if26.mmarchan.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                .databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                // recreate the database if necessary
                .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            databaseWriteExecutor.execute(() -> {
                                SectionDAO sectionDAO = INSTANCE.sectionDAO();
                                sectionDAO.insert(new SectionEntity("Code repositories"));
                                sectionDAO.insert(new SectionEntity("Package repositories"));

                                AuthIssuerDAO issuerDAO = INSTANCE.authIssuerDAO();
                                issuerDAO.deleteAll();
                                issuerDAO.insert(new AuthIssuerEntity("Github", "Couapy", "JBSWY3DPEHPK3PXP", 1));
                                issuerDAO.insert(new AuthIssuerEntity("Gitlab", "Couapy", "JBSWY3DPEHPK3PXP", 1));
                                issuerDAO.insert(new AuthIssuerEntity("Pypi", "Couapy", "JBSWY3DPEHPK3PXP", 2));
                            });
                        }
                    })
                .build();
        }
        return INSTANCE;
    }
}
