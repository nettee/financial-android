package me.nettee.financial.error;

public class Errors {

    public static String getErrorMessage(Throwable e) {
        if (e instanceof BadNetworkException) {
            return "错误: 无法连接到服务器 (E:1000)";
        } else if (e instanceof  BadJsonDataException) {
            return "错误: 解析数据失败 (E:1020)";
        } else {
            throw new IllegalArgumentException();
        }
    }
}
