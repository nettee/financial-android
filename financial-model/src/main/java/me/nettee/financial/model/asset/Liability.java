package me.nettee.financial.model.asset;

import java.util.List;

import me.nettee.financial.model.Amount;

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
