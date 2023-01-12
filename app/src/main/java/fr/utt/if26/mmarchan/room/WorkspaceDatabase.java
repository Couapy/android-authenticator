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
import fr.utt.if26.mmarchan.room.daos.WorkspaceDAO;
import fr.utt.if26.mmarchan.room.entities.AuthIssuerEntity;
import fr.utt.if26.mmarchan.room.entities.SectionEntity;
import fr.utt.if26.mmarchan.room.entities.WorkspaceEntity;

@Database(entities = {WorkspaceEntity.class}, version = 1)
public abstract class WorkspaceDatabase extends RoomDatabase {
    private static WorkspaceDatabase INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract WorkspaceDAO workspaceDAO();

    public static WorkspaceDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                .databaseBuilder(context, WorkspaceDatabase.class, "workspaces")
                .allowMainThreadQueries()
                .build();
        }
        return INSTANCE;
    }
}
