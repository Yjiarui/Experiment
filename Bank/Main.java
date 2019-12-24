package Bank;

public class Main {
    public static void main(String[] args) {
        //开始调用银行家算法进行资源请求（判断是不是形成一个安全序列）
        Bank banker = new Bank();
        banker.BankAlgorithm();
    }
}
