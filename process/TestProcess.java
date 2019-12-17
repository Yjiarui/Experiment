package process;


import java.util.Scanner;

public class TestProcess {
    public static void main(String[] args) {
        processMenu pm = new processMenu();
        Scanner scan  = new Scanner(System.in);
        int count = 1;
        while (count == 1){
            System.out.println("请选择调度算法：");
            System.out.println("1.先来先服务");
            System.out.println("2.短进程服务");
            System.out.println("3.时间片轮转");
            System.out.println("4.高优先级优先");
            pm.init();
            int n = scan.nextInt();
            switch (n){
                case 1:
                    pm.FCFS();pm.printProcess();
                    break;
                case 2:
                    pm.SJF();pm.printProcess();
                    break;
                case 3:
                    pm.RR();pm.printProcess();
                    break;
                case 4:
                    pm.PSA();pm.printProcess();
                    break;
                case 0:count = 0;
                break;
                default:
                    System.out.println("输入错误请重新输入：");
            }
        }
    }
}
