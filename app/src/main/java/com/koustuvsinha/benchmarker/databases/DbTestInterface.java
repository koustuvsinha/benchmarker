package com.koustuvsinha.benchmarker.databases;

import com.koustuvsinha.benchmarker.models.DbTestRecordModel;

import java.util.List;

/**
 * Created by koustuvsinha on 27/5/15.
 * DbTestInterface interface to define the API endpoints which should be adhered by
 * every database test runners
 */
public interface DbTestInterface {

    /**
     * Method to insert data into db
     * @param modelList List of DbTestRecordModel objects
     */
    public void insertData(List<DbTestRecordModel> modelList);

    /**
     * Method to retrieve the data from db
     * The method does not have a return type which is intentional, as we do not need to
     * verify the retrieved data, we just calculate the time taken to retrieve it
     */
    public void getData();

    /**
     * Method to update data in db
     * This method does not have a param as we update all the records to "na" by default
     * in all cases
     */
    public void updateData();

    /**
     * Method to delete all data from the concerned database
     */
    public void deleteAllData();

    /**
     * Method to close the database instance
     */
    public void closeDb();

    /**
     * Method to remove the database file
     * Even if we remove all the data, we also remove the actual file location to ensure
     * that the testing is done in the same situations
     */
    public void removeDbFile();
}
