package me.nettee.financial.model.account;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.error.BadJsonDataException;
import me.nettee.financial.error.BadNetworkException;
import me.nettee.financial.model.Server;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountLab {

    private static AccountLab sAccountLab;

    private AccountLab() {
    }

    public static AccountLab getInstance() {
        if (sAccountLab == null) {
            sAccountLab = new AccountLab();
        }
        return sAccountLab;
    }

    @Deprecated
    public static AccountLab getInstance(Context context) {
        return getInstance();
    }

    public void addAccount(Account account) {
        try {
            JSONObject jsonData = account.toJson();

            OkHttpClient client = Server.getOkHttpClient();

            RequestBody body = RequestBody.create(Server.JSON, jsonData.toString());
            Request request = new Request.Builder()
                    .url(Server.accounts)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new AssertionError(response.code());
            }

        } catch (JSONException e) {
            Log.e("TAG", e.getMessage());
            throw new BadJsonDataException(e);
        } catch (IOException e) {
            throw new BadNetworkException(e);
        }
    }

    public void deleteAccount(Account account) {
//        String uuid = account.getUuid();
//        if (uuid == null) {
//            throw new AssertionError();
//        }
//
//        try {
//            OkHttpClient client = Server.getOkHttpClient();
//
//            Request request = new Request.Builder()
//                    .url(Server.account(uuid))
//                    .delete()
//                    .build();
//            Response response = client.newCall(request).execute();
//
//            if (!response.isSuccessful()) {
//                throw new AssertionError(response.code());
//            }
//        } catch (IOException e) {
//            throw new BadNetworkException(e);
//        }
    }

    public List<Account> getAccounts() {
        //        WeixinAccount weixinAccount = new WeixinAccount();
//        weixinAccount.setBalance(Amount.decimal(92, 60));
//        accounts.add(weixinAccount);
//        CampusCardAccount campusCardAccount = new CampusCardAccount();
//        campusCardAccount.setBalance(Amount.decimal(49, 20));
//        accounts.add(campusCardAccount);
//        BusCardAccount busCardAccount = new BusCardAccount();
//        busCardAccount.setBalance(Amount.decimal(67, 3));
//        accounts.add(busCardAccount);
//        InvestmentAccount investmentAccount1 = new InvestmentAccount();
//        investmentAccount1.setPlatform(InvestmentPlatform.getPlatformByName("蚂蚁财富"));
//        accounts.add(investmentAccount1);
//        InvestmentAccount investmentAccount2 = new InvestmentAccount();
//        investmentAccount2.setPlatform(InvestmentPlatform.getPlatformByName("陆金所"));
//        accounts.add(investmentAccount2);
//        InvestmentAccount investmentAccount3 = new InvestmentAccount();
//        investmentAccount3.setPlatform(InvestmentPlatform.getPlatformByName("天天基金"));
//        accounts.add(investmentAccount3);
//        return accounts;

        String jsonData = "[{\"bankCardNumber\": \"669395\", \"type\": \"DEBIT_CARD\", \"balance\": \"6134.77\", \"remark\": \"\", \"uuid\": \"8ce0c4de-5106-11e8-9f40-68071509b9bf\"}, {\"paymentDate\": 10, \"remark\": \"\", \"uuid\": \"8ce0c4dd-5106-11e8-9f40-68071509b9bf\", \"bankCardNumber\": \"4237\", \"creditLimit\": \"2000\", \"type\": \"CREDIT_CARD\", \"arrears\": \"1234\", \"billDate\": 1}, {\"type\": \"CASH\", \"balance\": \"976.00\", \"remark\": \"\\u94b1\\u5305A\", \"uuid\": \"8ce0c4dc-5106-11e8-9f40-68071509b9bf\"}, {\"type\": \"ALIPAY\", \"yuebao\": null, \"remark\": \"\", \"uuid\": \"8ce0c4e0-5106-11e8-9f40-68071509b9bf\", \"balance\": \"16431.91\", \"huabei\": {\"creditLimit\": \"2000\", \"type\": \"HUABEI\", \"paymentDate\": 9, \"billDate\": 1, \"arrears\": \"413.43\"}}]";

        List<Account> accounts = new ArrayList<>();

//        OkHttpClient client = Server.getOkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(Server.accounts)
//                .build();

        try {
//            Response response = client.newCall(request).execute();
//
//            if (!response.isSuccessful()) {
//                throw new AssertionError(response.code());
//            }
//
//            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                accounts.add(Account.fromJson(jsonObject));
            }

            return accounts;

//        } catch (IOException e) {
//            throw new BadNetworkException(e);
        } catch (JSONException e) {
            throw new BadJsonDataException(e);
        }
    }

    public List<Account> getCandidateAccounts() {
        return fetchCandidateAccounts();
    }

    private List<Account> fetchCandidateAccounts() {

        String jsonData = "[{\"type\": \"CASH\", \"name\": \"\u73b0\u91d1\u94b1\u5305\"}, {\"type\": \"CREDIT_CARD\", \"name\": \"\u4fe1\u7528\u5361\"}, {\"type\": \"DEBIT_CARD\", \"name\": \"\u501f\u8bb0\u5361\"}, {\"type\": \"ALIPAY\", \"name\": \"\u652f\u4ed8\u5b9d\"}, {\"type\": \"WEIXIN\", \"name\": \"\u5fae\u4fe1\u94b1\u5305\"}, {\"type\": \"CAMPUS_CARD\", \"name\": \"\u6821\u56ed\u5361\"}, {\"type\": \"BUS_CARD\", \"name\": \"\u516c\u4ea4\u5361\"}, {\"type\": \"INVESTMENT\", \"name\": \"\u6295\u8d44\u8d26\u6237\"}]";

        List<Account> candidateAccounts = new ArrayList<>();
//
//        OkHttpClient client = Server.getOkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(Server.candidate_accounts)
//                .build();

        try {
//            Response response = client.newCall(request).execute();
//
//            if (!response.isSuccessful()) {
//                throw new AssertionError(response.code());
//            }
//
//            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                candidateAccounts.add(Account.candidate(jsonObject));
            }

            return candidateAccounts;

//        } catch (IOException e) {
//            throw new BadNetworkException(e);
        } catch (JSONException e) {
            throw new BadJsonDataException(e);
        }

    }
}
