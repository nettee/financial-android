package me.nettee.financial.model.bill;

import org.joda.time.DateTime;

import java.io.Serializable;

import me.nettee.financial.model.Amount;

public abstract class BookEntry implements Serializable {

    public enum Type {
        INCOME,
        OUTCOME,
        TRANSFER,
    }

    private Amount amount;
    private DateTime createdTime;
    private DateTime lastModifiedTime;
    private String remark;
    private BookCategory category;

    public abstract Type getType();

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public DateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

    public DateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(DateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
