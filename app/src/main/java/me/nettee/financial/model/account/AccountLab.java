package me.nettee.financial.model.account;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.investment.InvestmentPlatform;

public class AccountLab {

    private static AccountLab sAccountLab;

    private final Context mContext;
    private final RequestQueue mRequestQueue;

    private AccountLab(Context context) {
        mContext = context;
        mRequestQueue =  Volley.newRequestQueue(mContext);
    }

    public static AccountLab getInstance(Context context) {
        if (sAccountLab == null) {
            sAccountLab = new AccountLab(context);
        }
        return sAccountLab;
    }

    public void addAccount(Account account) {
        pushAccount(account);
        // TODO
    }

    public void deleteAccount(Account account) {
        // TODO
    }

    public List<Account> getAccounts() {
        return fetchAccounts();
    }

    public List<Account> getCandidateAccounts() {
        return fetchCandidateAccounts();
    }

    private void pushAccount(Account account) {
        try {
            account.toJson();
        } catch (JSONException e) {
            Log.e("TAG", e.getMessage());
            // TODO Toast
        }
    }

    private List<Account> fetchAccounts() {
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

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        mRequestQueue.add(new JsonArrayRequest(Request.Method.GET, Server.accounts, new JSONArray(), future, future));

        try {
            JSONArray jsonArray = future.get();
            Log.d("TAG", jsonArray.toString());
            List<Account> accounts = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                accounts.add(Account.fromJson(jsonObject));
            }
            return accounts;
        } catch (InterruptedException | ExecutionException e) {
            Log.e("TAG", e.getMessage());
            // TODO error number
            Toast.makeText(mContext, "错误: 无法连接到服务器", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.e("TAG", e.getMessage());
            Toast.makeText(mContext, "错误: 解析数据失败", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private List<Account> fetchCandidateAccounts() {

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        mRequestQueue.add(new JsonArrayRequest(Request.Method.GET, Server.candidate_accounts, new JSONArray(), future, future));

        try {
            JSONArray jsonArray = future.get();
            Log.d("TAG", jsonArray.toString());
            List<Account> candidateAccounts = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                candidateAccounts.add(Account.candidate(jsonObject));
            }
            return candidateAccounts;
        } catch (InterruptedException | ExecutionException e) {
            Log.d("TAG", e.getMessage());
            Toast.makeText(mContext, "错误: 无法连接到服务器", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.d("TAG", e.getMessage());
            Toast.makeText(mContext, "错误: 解析数据失败", Toast.LENGTH_SHORT).show();
        }

        return null;
    }
}
