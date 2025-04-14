package com.csumb.gymlog.Database;

import android.app.Application;

import com.csumb.gymlog.Database.Entities.GymLog;
import com.csumb.gymlog.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;

    private ArrayList<GymLog> allLogs;

    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }

    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return null;
                    }
                }
        );
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allLogs;
    }
}
