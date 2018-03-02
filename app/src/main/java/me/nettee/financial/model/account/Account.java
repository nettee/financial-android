package me.nettee.financial.model.account;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Asset;
import me.nettee.financial.model.Liability;

public abstract class Account implements Serializable {

    public static final int GENERAL = 0;
    public static final int CASH = 100;
    public static final int CREDIT_CARD = 200;
    public static final int DEBIT_CARD = 300;
    public static final int ALIPAY = 400;
    public static final int WEIXIN = 401;
    public static final int CAMPUS_CARD = 500;
    public static final int BUS_CARD = 501;
    public static final int INVESTMENT = 600;

    private final UUID mId;
    private String mRemark;

    public Account() {
        mId = UUID.randomUUID();
    }

    public final UUID getId() {
        return mId;
    }

    public abstract int getType();

    public abstract String getCandidateName();

    public abstract int getCandidateImageResource();

    public abstract Amount getDefaultAmount();

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

    public static CandidateAccount candidate(int type, String candidateName, int candidateImageResource) {
        return new CandidateAccount(type, candidateName, candidateImageResource);
    }

    private static final class CandidateAccount extends Account implements Serializable {

        private int mType;
        private String mCandidateName;
        private int mCandidateImageResource;

        private CandidateAccount(int type, String candidateName, int candidateImageResource) {
            mType = type;
            mCandidateName = candidateName;
            mCandidateImageResource = candidateImageResource;
        }

        public int getType() {
            return mType;
        }

        public String getCandidateName() {
            return mCandidateName;
        }

        public int getCandidateImageResource() {
            return mCandidateImageResource;
        }

        @Override
        public Amount getDefaultAmount() {
            throw new UnsupportedOperationException();
        }
    }

}
