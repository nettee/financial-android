package me.nettee.financial.model;

import java.io.Serializable;

public class CandidateAccount implements Serializable {

    private int mType;
    private String mCandidateName;
    private int mCandidateImageResource;

    public CandidateAccount(int type, String candidateName, int candidateImageResource) {
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
}
