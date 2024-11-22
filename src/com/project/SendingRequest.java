package com.project;


import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


/**
 *
 *
 *  @author navfalbek.makhfuzullaev
 *
 */


public class SendingRequest {
    static HttpClient client;
    static HttpRequest request;
    static HttpResponse<String> response;
    static Object fileJson;
    static Double[] rateResult = new Double[2];
    private static final String api = ""; // API token can be takan from apilayer.com website

    public static Double[] rateReturner (String from, String to, double amount) {
        try {
            client = HttpClient.newHttpClient();

            String url = "https://api.apilayer.com/exchangerates_data/convert?to=" + from + "&from=" + to + "&amount=" + amount;

            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("apikey", api)
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(url);
            String jsonString = response.body();
            System.out.println(response.body());

            fileJson = JSONValue.parse(jsonString);

            JSONObject jsonDecoding = (JSONObject) fileJson;

            rateResult[0] = (Double) jsonDecoding.get("result");
            System.out.println(rateResult[0]);

            rateResult[1] = rateResult[0] / amount;
            // rateResult[1] = Double.parseDouble(String.format("%.6f", rateResult[1]));
            Math.round(rateResult[1]);
            System.out.println(rateResult[1]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return rateResult;
    }
}