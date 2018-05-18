package me.nettee.financial.model.account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Asset;
import me.nettee.financial.model.asset.Liability;

public abstract class Account implements Comparable<Account>, Serializable {

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
        setUuid(UUID.randomUUID().toString());
    }

    public static Account fromJson(JSONObject jsonObject) throws JSONException {
        AccountType type = AccountType.valueOf(jsonObject.getString("type"));
        switch (type) {
            case CASH: return CashAccount.fromJson(jsonObject);
            case CREDIT_CARD: return CreditCardAccount.fromJson(jsonObject);
            case DEBIT_CARD: return DebitCardAccount.fromJson(jsonObject);
            case ALIPAY: return AlipayAccount.fromJson(jsonObject);
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

    public abstract Amount getDefaultAmount();

    public abstract String getDefaultAmountCaption();

    public final String getRemark() {
        return mRemark;
    }

    public final void setRemark(String remark) {
        mRemark = remark;
    }

    public boolean isBankCardAccount() {
        return getType().equals(AccountType.CREDIT_CARD)
                || getType().equals(AccountType.DEBIT_CARD);
    }

    public boolean isInvestmentAccount() {
        return getType().equals(AccountType.INVESTMENT);
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

    // Note: for test only
    public static CandidateAccount candidate(AccountType type) {
        return new CandidateAccount(type);
    }

    private static final class CandidateAccount extends Account implements Serializable {

        private static final long serialVersionUID = 1L;

        private AccountType mType;

        private CandidateAccount(AccountType type) {
            mType = type;
        }

        public static CandidateAccount fromJson(JSONObject jsonObject) throws JSONException {
            // TODO test whether this is correct
            String type = jsonObject.getString("type");
            return new CandidateAccount(AccountType.valueOf(type));
        }

        @Override
        public AccountType getType() {
            return mType;
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

    @Override
    public int compareTo(@Nonnull Account that) {
        if (this.getType().getPriority() < that.getType().getPriority()) {
            return -1;
        } else if (this.getType().getPriority() > that.getType().getPriority()) {
            return 1;
        } else {
            return this.getUuid().compareTo(that.getUuid());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Account) {
            Account that = (Account) o;
            return getUuid().equals(that.getUuid());
        } else {
            return false;
        }
    }
}
