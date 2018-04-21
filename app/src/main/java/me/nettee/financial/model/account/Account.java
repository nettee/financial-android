package me.nettee.financial.model.account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import me.nettee.financial.R;
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

    public abstract String getCandidateName();

    public final int getCandidateImageResource() {
        switch (getType()) {
            case CASH: return R.drawable.ic_wallet;
            case CREDIT_CARD: return R.drawable.ic_bank_card;
            case DEBIT_CARD: return R.drawable.ic_bank_card;
            case ALIPAY: return R.drawable.ic_alipay;
            case HUABEI: return R.drawable.ic_huabei;
            case WEIXIN: return R.drawable.ic_weixin;
            case CAMPUS_CARD: return R.drawable.ic_campus_card;
            case BUS_CARD: return R.drawable.ic_bus;
            case INVESTMENT: return R.drawable.ic_investment;
            case GENERAL: return R.drawable.ic_account;
            default: return R.drawable.ic_account;
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
