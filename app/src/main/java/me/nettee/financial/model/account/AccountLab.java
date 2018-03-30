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
import okhttp3.MediaType;
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
        // TODO
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

        List<Account> accounts = new ArrayList<>();

        OkHttpClient client = Server.getOkHttpClient();

        Request request = new Request.Builder()
                .url(Server.accounts)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new AssertionError(response.code());
            }

            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                accounts.add(Account.fromJson(jsonObject));
            }

            return accounts;

        } catch (IOException e) {
            throw new BadNetworkException(e);
        } catch (JSONException e) {
            throw new BadJsonDataException(e);
        }
    }

    public List<Account> getCandidateAccounts() {
        return fetchCandidateAccounts();
    }

    private List<Account> fetchCandidateAccounts() {

        List<Account> candidateAccounts = new ArrayList<>();

        OkHttpClient client = Server.getOkHttpClient();

        Request request = new Request.Builder()
                .url(Server.candidate_accounts)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new AssertionError(response.code());
            }

            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                candidateAccounts.add(Account.candidate(jsonObject));
            }

            return candidateAccounts;

        } catch (IOException e) {
            throw new BadNetworkException(e);
        } catch (JSONException e) {
            throw new BadJsonDataException(e);
        }

    }
}
