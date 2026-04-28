package com.practice.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    private static String requestId = "8007";

    public static void main(String[] args) {

        //  Prepare request params
        Map<String, String> procParams = getProcParams(requestId);
        System.out.println("001 Request Payload -> " + new JSONObject(procParams));

        // Dummy Response (Simulating API/DB response)
        String dummyResponse = "{\n" +
                "  \"records\": [\n" +
                "    {\n" +
                "      \"initiatorTransactId\": \"8800000972\",\n" +
                "      \"olbApproverTransactId\": \"8800005768\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"initiatorTransactId\": \"1111111111\",\n" +
                "      \"olbApproverTransactId\": \"2222222222\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"opstatus\": 0,\n" +
                "  \"httpStatusCode\": 0\n" +
                "}";

        System.out.println("002 Dummy Response -> " + dummyResponse);

        //  Parse JSON
        try {
            JSONObject json = new JSONObject(dummyResponse);
            int opstatus = json.optInt("opstatus");
            if (opstatus != 0) {
                System.out.println("003 API failed with opstatus = " + opstatus);
                return;
            }

            //Extract records fron records json arry
            JSONArray records = json.optJSONArray("records");

            if (records != null && records.length() > 0) {

                System.out.println("004 Extracted Data");

                // fetch  all records using loop
                for (int i = 0; i < records.length(); i++) {

                    JSONObject record = records.getJSONObject(i);

                    String initiatorTransactId = record.optString("initiatorTransactId", "N/A");
                    String olbApproverTransactId = record.optString("olbApproverTransactId", "N/A");

                    // 🖨 Print output
                    System.out.println("------------------------");
                    System.out.println("Record #" + (i + 1));
                    System.out.println("Initiator ID: " + initiatorTransactId);
                    System.out.println("OLB Approver ID: " + olbApproverTransactId);
                }

            } else {
                System.out.println("No records found");
            }

        } catch (Exception e) {
            System.out.println("Actual Exception ERROR: " + e.getMessage());
        }
    }

    // Helper method
    private static Map<String, String> getProcParams(String requestId) {
        Map<String, String> procParams = new HashMap<>();
        procParams.put("p_requestId", requestId);
        return procParams;
    }
}
