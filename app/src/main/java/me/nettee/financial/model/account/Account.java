package me.nettee.financial.model.account;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

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

    private final UUID mId;
    private String mRemark;

    public Account() {
        mId = UUID.randomUUID();
    }

    public final UUID getId() {
        return mId;
    }

    public abstract AccountType getType();

    public abstract String getCandidateName();

    public abstract int getCandidateImageResource();

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

    public static CandidateAccount candidate(AccountType type, String candidateName, int candidateImageResource) {
        return new CandidateAccount(type, candidateName, candidateImageResource);
    }

    private static final class CandidateAccount extends Account implements Serializable {

        private static final long serialVersionUID = 1L;

        private AccountType mType;
        private String mCandidateName;
        private int mCandidateImageResource;

        private CandidateAccount(AccountType type, String candidateName, int candidateImageResource) {
            mType = type;
            mCandidateName = candidateName;
            mCandidateImageResource = candidateImageResource;
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
        public int getCandidateImageResource() {
            return mCandidateImageResource;
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
