package com.csumb.gymlog.Database;

import android.app.Application;

import com.csumb.gymlog.Database.Entities.GymLog;
import com.csumb.gymlog.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;

    private static GymLogRepository repository;

    private GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
    }

    public static GymLogRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }

        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);
                    }
                }
        );

        try {
            repository = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return repository;
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
