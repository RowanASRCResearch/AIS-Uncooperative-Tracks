package io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import prediction.Controller;

import java.io.*;

/**
 * Created by Roberto Gonzalez on 3/23/2016.
 */
public class CSVParser {

    //global variables
    private Iterable<CSVRecord> csvRecordIterable;
    private File csvFile;

    /**
     * Instantiates a new Csv parser.
     *
     * @param csvFile the csv file to be parsed
     */
    public CSVParser(File csvFile) throws CSVParserException {
        this.csvFile = csvFile;
        //Worst case exception handling
        if(csvFile == null || Controller.database == null || !readFile()){
            throw new CSVParserException();
        }
    }

    /**
     * Instantiates the file reader and passes it to the parser initializer
     *
     * @return boolean false when file not found
     */
    private boolean readFile() {
        try {
            Reader csvReader = new FileReader(csvFile);
            if (!initParser(csvReader)) {
                return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            return false;
        }
        return true;
    }

    /**
     * Initializes the csv parser with predefined headers
     *
     * @param csvReader the csv reader object for the csvFile
     * @return false if ioException
     */
    private boolean initParser(Reader csvReader) {
        //defines the csv column headers to be referenced by name
        try {
            csvRecordIterable = CSVFormat.EXCEL.withHeader(Controller.database.getColumnNames()).parse(csvReader);
        } catch (IOException e) {
            System.out.println("io exception");
            return false;
        }
        return true;
    }

    /**
     * Iterates over all csv records found in the file.
     *
     * @return false if an an SQLException is thrown
     */
    public boolean iterateCsv() {
        int i = 0;
            /**
             * Iterates over each csv record to build an SQL query containing a given csv record's data
             */
            for (CSVRecord record : csvRecordIterable) {
                //this skips the first record, as it contains header data
                if (i > 0) {
                    Controller.database.insertCsvEntry(record);
                }
                i++;
            }
        return true;
    }


}