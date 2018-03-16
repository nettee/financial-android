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

    private Optional<List<Account>> mAccountsCache = Optional.empty();
    private Optional<List<Account>> mCandidateAccountsCache = Optional.empty();

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
        // TODO
        mAccountsCache.get().add(account);
//        Collections.sort(mAccounts, Comparator.comparingInt(one -> one.getType().getPriority()));
    }

    public void deleteAccount(Account account) {
        // TODO
        mAccountsCache.get().removeIf(acc -> acc.getId().equals(account.getId()));
    }

    public List<Account> getAccounts() {
        if (mAccountsCache.isPresent()) {
            return mAccountsCache.get();
        } else {
            List<Account> accounts = fetchAccounts();
            if (accounts != null) {
                mAccountsCache = Optional.of(accounts);
            }
            return accounts;
        }
    }

    public List<Account> getCandidateAccounts() {

        if (mCandidateAccountsCache.isPresent()) {
            return mCandidateAccountsCache.get();
        } else {
            List<Account> candidateAccounts = fetchCandidateAccounts();
            if (candidateAccounts != null) {
                mCandidateAccountsCache = Optional.of(candidateAccounts);
            }
            return candidateAccounts;
        }
    }

    private List<Account> fetchAccounts() {
//        List<Account> accounts = new ArrayList<>();
//        CashAccount cashAccount = new CashAccount();
//        cashAccount.setBalance(Amount.integer(976));
//        cashAccount.setRemark("钱包A");
//        accounts.add(cashAccount);
//        CreditCardAccount creditCardAccount = new CreditCardAccount();
//        Amount arrears = Amount.integer(1234);
//        arrears.setSign(Amount.NEGATIVE);
//        creditCardAccount.setArrears(arrears);
//        accounts.add(creditCardAccount);
//        DebitCardAccount debitCardAccount = new DebitCardAccount();
//        debitCardAccount.setBankCardNumber("669395");
//        debitCardAccount.setBalance(Amount.decimal(6134, 77));
//        accounts.add(debitCardAccount);
//        AlipayAccount alipayAccount = new AlipayAccount();
//        alipayAccount.setBalance(Amount.decimal(16431, 91));
//        alipayAccount.setHuabeiOpen(true);
//        HuabeiAccount huabeiAccount = new HuabeiAccount();
//        huabeiAccount.setCreditLimit(Amount.integer(2000));
//        huabeiAccount.setBillDate(CreditDate.day(1));
//        huabeiAccount.setPaymentDate(CreditDate.day(9));
//        huabeiAccount.setArrears(Amount.decimal(-413, 43));
//        alipayAccount.setHuabeiAccount(huabeiAccount);
//        accounts.add(alipayAccount);
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

        String url = "http://106.14.207.119:5000/accounts";

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        mRequestQueue.add(new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), future, future));

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
            Log.d("TAG", e.getMessage());
            Toast.makeText(mContext, "错误: 无法连接到服务器", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Log.d("TAG", e.getMessage());
            Toast.makeText(mContext, "错误: 解析数据失败", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private List<Account> fetchCandidateAccounts() {

        String url = "http://106.14.207.119:5000/candidate_accounts";

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        mRequestQueue.add(new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), future, future));

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
