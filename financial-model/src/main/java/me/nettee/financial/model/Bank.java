package me.nettee.financial.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Bank {

    public static final int ICBC = 1;

    private final int mType;
    private final String mName;

    private Bank(int type, String name) {
        mType = type;
        mName = name;
    }

    public static Bank icbc() {
        return new Bank(ICBC, "工商银行");
    }

    public int getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public int getImageResource() {
        try {
            Class<?> class_ = Class.forName("me.nettee.financial.model.ImageResource");
            Method method = class_.getMethod("getBankImageResource", Bank.class);
            Object o = method.invoke(null, this);
            return (int) (Integer) o;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return 0;
        }
    }
}
