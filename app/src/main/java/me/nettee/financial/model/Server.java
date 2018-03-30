package me.nettee.financial.model;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class Server {

    private static String base = "http://106.14.207.119:5000";

    public static String accounts = base + "/accounts";
    public static String candidate_accounts = base + "/candidate_accounts";

    private static OkHttpClient sOkHttpClient;

    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            sOkHttpClient = new OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();

        }
        return sOkHttpClient;
    }
}
