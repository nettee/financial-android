package me.nettee.financial.model.account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Asset;
import me.nettee.financial.model.asset.Liability;

public abstract class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum AccountType {
        GENERAL,
        CASH,
        CREDIT_CARD,
        HUABEI,
        DEBIT_CARD,
        ALIPAY,
        WEIXIN,
        CAMPUS_CARD,
        BUS_CARD,
        INVESTMENT,
        ;

        public int getPriority() {
            switch (this) {
                case CASH: return 0;
                case CREDIT_CARD: return 10;
                case DEBIT_CARD: return 20;
                case ALIPAY: return 30;
                case HUABEI: return 40;
                case WEIXIN: return 50;
                case CAMPUS_CARD: return 60;
                case BUS_CARD: return 70;
                case INVESTMENT: return 80;
                case GENERAL: return 90;
                default: return 99;
            }
        }
    }

    private String mUuid;
    private String mRemark;

    public Account() {
    }

    public static Account fromJson(JSONObject jsonObject) throws JSONException {
        AccountType type = AccountType.valueOf(jsonObject.getString("type"));
        switch (type) {
            case CASH: return CashAccount.fromJson(jsonObject);
//            case CREDIT_CARD: return CreditCardAccount.fromJson(jsonObject);
            case DEBIT_CARD: return DebitCardAccount.fromJson(jsonObject);
//            case ALIPAY: return AlipayAccount.fromJson(jsonObject);
            default: throw new AssertionError();
        }
    }

    public JSONObject toJson() throws JSONException {
        throw new UnsupportedOperationException();
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

    public abstract AccountType getType();

    public abstract String getCandidateName();

    public final int getCandidateImageResource() {
        try {
            Class<?> class_ = Class.forName("me.nettee.financial.model.ImageResource");
            Method method = class_.getMethod("getAccountCandidateImageResource", Account.class);
            return (int) (Integer) method.invoke(null, this);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return 0;
        }
    }

    public abstract Amount getDefaultAmount();

    public abstract String getDefaultAmountCaption();

    public final String getRemark() {
        return mRemark;
    }

    public final void setRemark(String remark) {
        mRemark = remark;
    }

    /**
     * Displayed in account list, and account detail card.
     * Sub-classes can override this default implementation.
     * @return account name
     */
    public String getDisplayName() {
        return getCandidateName();
    }

    /**
     * Displayed in account list, and account detail card.
     * Sub-classes can override this default implementation.
     * @return account image (icon)
     */
    public int getDisplayImageResource() {
        return getCandidateImageResource();
    }

    /** Displayed in account list, and account detail card.
     * Sub-classes can override this default implementation.
     * @return remark
     */
    public String getDisplayRemark() {
        return getRemark();
    }

    /**
     * Asset correspond to this account.
     * Sub-classes can override this default implementation.
     */
    public Optional<Asset> getAsset() {
        return Optional.empty();
    }

    /**
     * Liability correspond to this account.
     * Sub-classes can override this default implementation.
     */
    public Optional<Liability> getLiability() {
        return Optional.empty();
    }

    public static CandidateAccount candidate(JSONObject jsonObject) throws JSONException {
        return CandidateAccount.fromJson(jsonObject);
    }

    private static final class CandidateAccount extends Account implements Serializable {

        private static final long serialVersionUID = 1L;

        private AccountType mType;
        private String mCandidateName;

        private CandidateAccount(AccountType type, String candidateName) {
            mType = type;
            mCandidateName = candidateName;
        }

        public static CandidateAccount fromJson(JSONObject jsonObject) throws JSONException {
            String type = jsonObject.getString("type");
            String name = jsonObject.getString("name");
            return new CandidateAccount(AccountType.valueOf(type), name);
        }

        @Override
        public AccountType getType() {
            return mType;
        }

        @Override
        public String getCandidateName() {
            return mCandidateName;
        }

        @Override
        public Amount getDefaultAmount() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getDefaultAmountCaption() {
            throw new UnsupportedOperationException();
        }
    }

}
