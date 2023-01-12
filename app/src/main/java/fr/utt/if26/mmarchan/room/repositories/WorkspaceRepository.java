package fr.utt.if26.mmarchan.room.repositories;

import android.app.Application;

import fr.utt.if26.mmarchan.room.WorkspaceDatabase;
import fr.utt.if26.mmarchan.room.daos.WorkspaceDAO;
import fr.utt.if26.mmarchan.room.entities.WorkspaceEntity;

public class WorkspaceRepository {

    private WorkspaceDAO mWorkspaceDAO;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public WorkspaceRepository(Application application) {
        WorkspaceDatabase db = WorkspaceDatabase.getInstance(application);
        mWorkspaceDAO = db.workspaceDAO();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertWorkspace(WorkspaceEntity workspace) {
        WorkspaceDatabase.databaseWriteExecutor.execute(() -> {
            mWorkspaceDAO.insert(workspace);
        });
    }

    public WorkspaceEntity getWorkspaceByPassword(String hash) {
        return mWorkspaceDAO.getByPassword(hash);
    }

    public WorkspaceEntity getWorkspaceById(int id) {
        return mWorkspaceDAO.getById(id);
    }

    public void deleteWorkspace(WorkspaceEntity workspace) {
        WorkspaceDatabase.databaseWriteExecutor.execute(() -> {
            mWorkspaceDAO.delete(workspace);
        });
    }

    public void update(WorkspaceEntity workspace) {
        WorkspaceDatabase.databaseWriteExecutor.execute(() -> {
            mWorkspaceDAO.update(workspace);
        });
    }


}