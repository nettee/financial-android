package me.nettee.financial.model.account;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.investment.InvestmentPlatform;

public class AccountLab {

    private static AccountLab sAccountLab;

    private final Context mContext;

    private final List<Account> mAccounts;
    private final RequestQueue mRequestQueue;

    private Comparator<Account> mAccountComparator = (one, another) -> {
        if (one.getType() < another.getType()) {
            return -1;
        } else if (one.getType() > another.getType()) {
            return 1;
        } else {
            return 0;
        }
    };

    private static List<Account> sCandidateAccounts = new ArrayList<Account>() {
        private static final long serialVersionUID = 1L;
        {
            add(Account.candidate(Account.CASH, "现金钱包", R.drawable.ic_wallet));
            add(Account.candidate(Account.CREDIT_CARD, "信用卡", R.drawable.ic_bank_card));
            add(Account.candidate(Account.DEBIT_CARD, "借记卡", R.drawable.ic_bank_card));
            add(Account.candidate(Account.ALIPAY, "支付宝", R.drawable.ic_alipay));
            add(Account.candidate(Account.WEIXIN, "微信钱包", R.drawable.ic_wxpay));
            add(Account.candidate(Account.CAMPUS_CARD, "校园卡", R.drawable.ic_campus_card));
            add(Account.candidate(Account.BUS_CARD, "公交卡", R.drawable.ic_bus));
            add(Account.candidate(Account.INVESTMENT, "投资账户", R.drawable.ic_account_investment));
//            add(Account.candidate(Account.CASH, "其他账户", R.drawable.ic_account));
        }
    };

    private AccountLab(Context context) {

        mContext = context;
        mRequestQueue =  Volley.newRequestQueue(mContext);

        mAccounts = new ArrayList<>();
        CashAccount cashAccount = new CashAccount();
        cashAccount.setBalance(Amount.integer(976));
        cashAccount.setRemark("钱包A");
        mAccounts.add(cashAccount);
        CreditCardAccount creditCardAccount = new CreditCardAccount();
        Amount arrears = Amount.integer(1234);
        arrears.setSign(Amount.NEGATIVE);
        creditCardAccount.setArrears(arrears);
        mAccounts.add(creditCardAccount);
        DebitCardAccount debitCardAccount = new DebitCardAccount();
        debitCardAccount.setBankCardNumber("669395");
        debitCardAccount.setBalance(Amount.decimal(6134, 77));
        mAccounts.add(debitCardAccount);
        AlipayAccount alipayAccount = new AlipayAccount();
        alipayAccount.setBalance(Amount.decimal(16431, 91));
        alipayAccount.setHuabeiOpen(true);
        HuabeiAccount huabeiAccount = new HuabeiAccount();
        huabeiAccount.setCreditLimit(Amount.integer(2000));
        huabeiAccount.setBillDate(CreditDate.day(1));
        huabeiAccount.setPaymentDate(CreditDate.day(9));
        huabeiAccount.setArrears(Amount.decimal(-413, 43));
        alipayAccount.setHuabeiAccount(huabeiAccount);
        mAccounts.add(alipayAccount);
        WeixinAccount weixinAccount = new WeixinAccount();
        weixinAccount.setBalance(Amount.decimal(92, 60));
        mAccounts.add(weixinAccount);
        CampusCardAccount campusCardAccount = new CampusCardAccount();
        campusCardAccount.setBalance(Amount.decimal(49, 20));
        mAccounts.add(campusCardAccount);
        BusCardAccount busCardAccount = new BusCardAccount();
        busCardAccount.setBalance(Amount.decimal(67, 3));
        mAccounts.add(busCardAccount);
//        mAccounts.add(new Account("花呗", 15043, Account.HUABEI, R.drawable.ic_huabei));
        InvestmentAccount investmentAccount1 = new InvestmentAccount();
        investmentAccount1.setPlatform(InvestmentPlatform.getPlatformByName("蚂蚁财富"));
        mAccounts.add(investmentAccount1);
        InvestmentAccount investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setPlatform(InvestmentPlatform.getPlatformByName("陆金所"));
        mAccounts.add(investmentAccount2);
        InvestmentAccount investmentAccount3 = new InvestmentAccount();
        investmentAccount3.setPlatform(InvestmentPlatform.getPlatformByName("天天基金"));
        mAccounts.add(investmentAccount3);
    }

    public static AccountLab getInstance(Context context) {
        if (sAccountLab == null) {
            sAccountLab = new AccountLab(context);
        }
        return sAccountLab;
    }

    public void addAccount(Account account) {
        mAccounts.add(account);
        Collections.sort(mAccounts, mAccountComparator);
    }

    public void deleteAccount(Account account) {
        mAccounts.removeIf(acc -> acc.getId().equals(account.getId()));
    }

    public List<Account> getAccounts() {
        return mAccounts;
    }

    public List<Account> getCandidateAccounts() {

        return fetchCandidateAccounts();

//        return sCandidateAccounts;
    }

    public List<Account> fetchCandidateAccounts() {

        String url = "http://106.14.207.119:5000/candidate_accounts";

//        Response.Listener<JSONArray> onResponse = response -> {
//            try {
//                Log.d("TAG", response.toString());
//                for (int i = 0; i < response.length(); i++) {
//                    JSONObject jsonObject = response.getJSONObject(i);
//                    String type = jsonObject.getString("type");
//                    String name = jsonObject.getString("name");
//                }
//            } catch (JSONException e) {
//                Toast.makeText(mContext, "错误: 解析数据失败", Toast.LENGTH_SHORT).show();
//            }
//        };
//        Response.ErrorListener onError = error -> {
//            Log.d("TAG", error.toString());
//            Toast.makeText(mContext, "错误: 无法连接到服务器", Toast.LENGTH_SHORT).show();
//        };

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        mRequestQueue.add(new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), future, future));

//        mRequestQueue.add(new JsonArrayRequest(Request.Method.GET, url, null, onResponse, onError));

        try {
            JSONArray jsonArray = future.get();
            Log.d("TAG", jsonArray.toString());
            List<Account> candidateAccounts = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String type = jsonObject.getString("type");
                String name = jsonObject.getString("name");
                candidateAccounts.add(Account.candidate(Account.CASH, name, R.drawable.ic_wallet));
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
