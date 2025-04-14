package com.csumb.gymlog.Database;

import android.app.Application;

import com.csumb.gymlog.Database.Entities.GymLog;
import com.csumb.gymlog.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;

    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
    }

    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertGymLog(GymLog gymLog) {
        Future<Object> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        gymLogDAO.insert(gymLog);
                        return null;
                    }
                }
        );
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
