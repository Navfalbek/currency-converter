package com.project;

import java.io.*;
import java.util.Arrays;


/**
 *
 *
 *  @author navfalbek.makhfuzullaev
 *
 */

public class CsvDecoding {
    static String line = "";
    static String splitBy = ",";
    static int i = 0, j = 0;
    private final static String csvPath = "src/resources/data/data_new.csv";
    private final String[] currency = new String[169];
    private final String[] currencyCode = new String[169];
    CsvDecoding() {
        try {

            BufferedReader br = new BufferedReader(new FileReader(csvPath));

            while ((line = br.readLine()) != null) {

                String[] fileLine = line.split(splitBy);

                setCurrency(fileLine[0]);
                setCurrencyCode(fileLine[1]);
            }

        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void setCurrency (String currency) {
        this.currency[i] = currency;
        i++;
    }

    private void setCurrencyCode (String currencyCode) {
        this.currencyCode[j] = currencyCode;
        j++;
    }

    public String[] getCurrency() {
        return currency;
    }

    public String getCurrencyCode(String currencyCode) {
        int indexCode = Arrays.binarySearch(currency, currencyCode);    // may return negative value (Did not think about that)
        indexCode = Math.abs(indexCode);
        System.out.println("indexCode: " + indexCode);
        return this.currencyCode[indexCode];
    }
}
