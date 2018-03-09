package me.nettee.financial.model.asset;

import java.util.List;

import me.nettee.financial.model.Amount;

public class Asset extends AssetAndLiability {

    public Asset(Amount amount) {
        setAmount(amount);
    }

    public static Amount sum(List<Asset> assetList) {
        Amount total = Amount.zero();
        for (Asset asset : assetList) {
            total = total.add(asset.getAmount());
        }
        return total;
    }

}
