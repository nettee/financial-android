package me.nettee.financial.model;

import java.util.List;

public class Liability extends AssetAndLiability {

    public Liability(Amount amount) {
        setAmount(amount);
    }

    public static Amount sum(List<Liability> liabilityList) {
        Amount total = Amount.zero();
        for (Liability liability : liabilityList) {
            total = total.add(liability.getAmount());
        }
        return total;
    }
}
