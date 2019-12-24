package Bank;

import java.util.Scanner;

public class Bank {
    //分配资源的方法：将进程的资源请求进行处理
    public void BankAlgorithm(){
        //初始化系统已有的资源+进程的当前信息
        //五个进程对象（输入每个进程对象的名称\所需的最大资源\已分配的资源\还需要的资源）
        Processor processor[] = new Processor[5];

        System.out.println("请输入进程信息：");
        Scanner scanner = new Scanner(System.in);
        for (int i = 0;i < 5;i++){

        }
//        //第一组测试
//        processor[0] = new Processor("P0",5,7,3,1,0,0,4,7,3);
//        processor[1] = new Processor("P1",2,3,2,0,2,0,2,1,2);
//        processor[2] = new Processor("P2",0,9,2,0,3,2,0,6,0);
//        processor[3] = new Processor("P3",2,2,2,1,2,1,1,0,1);
//        processor[4] = new Processor("P4",3,4,3,0,0,2,3,4,1);

        /** 第二组测试数据:
         processor[0] = new Processor("P0", 5, 7, 3, 1, 0, 0, 4, 7, 3);
         processor[1] = new Processor("P1", 2, 3, 2, 0, 2, 0, 2, 1, 2);
         processor[2] = new Processor("P2", 0, 9, 2, 0, 3, 2, 0, 6, 0);
         processor[3] = new Processor("P3", 2, 2, 2, 1, 2, 1, 1, 0, 1);
         processor[4] = new Processor("P4", 3, 4, 3, 3, 3, 3, 0, 1, 0);*/


        // 一个资源对象(表示当系统拥有的资源)
        Sources sources = new Sources(scanner.nextInt(),scanner.nextInt(),scanner.nextInt(),scanner.nextInt());

        System.out.println("当前系统可分配资源为:");
        System.out.println("A: " + sources.A + "\tB: " + sources.B + "\tC: " + sources.C + "\tD: "+sources.D);
        System.out.println("----------------------------进程状态" + "------------------------------");
        System.out.println("P(进程号)" + "\t" + "Max(所需最大资源)" + "\t" + "Allocation(已分配资源)" + "\t" + "Need(还需要的资源数量)");
        System.out.println("   " + "\t" + "A B C D" + "\t\t" + "A B C D" + "\t\t\t" + "A B C D");
        for (int i = 0; i < 5; i++)
        {
            processor[i].ShowProcessor();
        }
        System.out.println("-----------------------------------------------------------------\n");

        while (true)
        {
            System.out.println("开始进程资源分配:");

            // 输入要请求的进程号及其要请求的资源数目
            Scanner scan = new Scanner(System.in);
            System.out.println("请输入要请求的进程号(0-4): ");
            int RequestProcessorNum = scan.nextInt();
            System.out.println("请输入要为该进程请求的资源数目(A、B、C、D): ");
            int a, b, c,d;
            a = scan.nextInt();
            b = scan.nextInt();
            c = scan.nextInt();
            d = scan.nextInt();
            Sources RequestSources = new Sources(a, b, c,d);

            // 当初始时第一个进程请求完毕后，需要做判断和改值
            // 1.判断是不是保证请求的每个资源数量是小于等于该进程还需要的资源数量的
            if (RequestSources.A > processor[RequestProcessorNum].Need.A
                    || RequestSources.B > processor[RequestProcessorNum].Need.B
                    || RequestSources.C > processor[RequestProcessorNum].Need.C
                    || RequestSources.D > processor[RequestProcessorNum].Need.D) {
                System.out.println("出现错误: 请求的资源超过了该进程所需要的资源");
                System.out.println("退出系统");
                System.exit(-1);
            }
            // 2.判断请求的资源是不是小于等于当前所拥有的资源
            if (RequestSources.A > sources.A || RequestSources.B > sources.B || RequestSources.C > sources.C || RequestSources.D > sources.D) {
                System.out.println("出现错误: 请求的资源超过了系统此时拥有的资源");
                System.out.println("退出系统(等待其他进程释放资源)");
                System.exit(-1);
            }

            // 工作资源(实时记录当前系统资源数量)
            Sources Work = sources;
            //因为已经有进程请求了分配资源，假设可以进行这次的资源请求，所以需要改值
            processor[RequestProcessorNum].Allocation.A += RequestSources.A;
            processor[RequestProcessorNum].Allocation.B += RequestSources.B;
            processor[RequestProcessorNum].Allocation.C += RequestSources.C;
            processor[RequestProcessorNum].Allocation.D += RequestSources.D;
            processor[RequestProcessorNum].Need.A -= RequestSources.A;
            processor[RequestProcessorNum].Need.B -= RequestSources.B;
            processor[RequestProcessorNum].Need.C -= RequestSources.C;
            processor[RequestProcessorNum].Need.D -= RequestSources.D;
            Work.A -= RequestSources.A;
            Work.B -= RequestSources.B;
            Work.C -= RequestSources.C;
            Work.D -= RequestSources.D;

            //已经做出了资源请求并且改了值，判断是不是存在安全序列
            boolean isExistSafeQueue = true;
            String[] safeQueue = new String[5];
            int index = 0;
            while (true)
            {
                boolean find = false;
                for (int i = 0; i < 5; i++)
                {
                    if (processor[i].Finish == false)// 该进程没有分配资源
                    {
                        boolean flag = true;// 看系统当前资源是不是>=该进程需要的资源
                        if (Work.A < processor[i].Need.A)
                        {
                            flag = false;
                        }
                        if (Work.B < processor[i].Need.B)
                        {
                            flag = false;
                        }
                        if (Work.C < processor[i].Need.C)
                        {
                            flag = false;
                        }
                        if (Work.D < processor[i].Need.D){
                            flag = false;
                        }
                        if (flag == true)
                        {
                            //System.out.println("此时进程"+i+"可以被分配资源");

                            // 满足三个资源的条件，可以进行资源的分配，当前系统的资源要+上该进程释放的资源
                            Work.A += processor[i].Allocation.A;
                            Work.B += processor[i].Allocation.B;
                            Work.C += processor[i].Allocation.C;
                            Work.D += processor[i].Allocation.D;

                            //System.out.println("分配资源后work数组等于="+Work.A+","+Work.B+","+Work.C);

                            processor[i].Finish = true;

                            // 安全序列中加入这个进程
                            safeQueue[index++] = "P" + i;

                            // 标记find
                            find = true;
                        }
                    }
                }
                if (find == false)
                {
                    for (int i = 0; i < 5; i++)
                    {
                        if (processor[i].Finish == false)
                        {
                            isExistSafeQueue = false;
                            System.out.println("出现进程之间的死锁，也就是找不到安全序列");
                            break;
                        }
                    }
                    break;
                }
            }
            // 如果存在安全序列
            if (isExistSafeQueue == true)
            {
                System.out.println("能够对进程安全分配资源");
                System.out.println("安全序列如下:");
                for (int i = 0; i < 5; i++)
                {
                    System.out.print(safeQueue[i] + " ");
                }
                System.out.println();

                //已经判断能够形成安全序列，表示该进程的资源请求成功，此时不需要改变进程的值只要改变sources的值解
                sources.A -= RequestSources.A;
                sources.B -= RequestSources.B;
                sources.C -= RequestSources.C;
                sources.D -= RequestSources.D;

                System.out.println("\n系统分配资源后:");
                System.out.println("当前系统可分配资源为:");
                System.out.println("A: " + sources.A + "\tB: " + sources.B + "\tC: " + sources.C+"\tD: "+sources.D);
                System.out.println("----------------------------进程状态" + "------------------------------");
                System.out.println(
                        "P(进程号)" + "\t" + "Max(所需最大资源)" + "\t" + "Allocation(已分配资源)" + "\t" + "Need(还需要的资源数量)");
                System.out.println("   " + "\t" + "A B C D" + "\t\t" + "A B C D" + "\t\t\t" + "A B C D");
                for (int i = 0; i < 5; i++)
                {
                    processor[i].ShowProcessor();
                }
                System.out.println("-----------------------------------------------------------------\n");

            }
            else
            {
                System.out.println("不能够对进程进行安全的资源分配");

                //已经判断不能够形成安全序列，表示该进程的资源请求失败，因为之前已经修改了值，所以要改回来
                processor[RequestProcessorNum].Allocation.A -= RequestSources.A;
                processor[RequestProcessorNum].Allocation.B -= RequestSources.B;
                processor[RequestProcessorNum].Allocation.C -= RequestSources.C;
                processor[RequestProcessorNum].Allocation.D -= RequestSources.D;
                processor[RequestProcessorNum].Need.A += RequestSources.A;
                processor[RequestProcessorNum].Need.B += RequestSources.B;
                processor[RequestProcessorNum].Need.C += RequestSources.C;
                processor[RequestProcessorNum].Need.D += RequestSources.D;

                System.out.println("当前系统可分配资源为:");
                System.out.println("A: " + sources.A + "\tB: " + sources.B + "\tC: " + sources.C + "\tD: "+sources.D);
                System.out.println("----------------------------进程状态" + "------------------------------");
                System.out.println(
                        "P(进程号)" + "\t" + "Max(所需最大资源)" + "\t" + "Allocation(已分配资源)" + "\t" + "Need(还需要的资源数量)");
                System.out.println("   " + "\t" + "A B C D" + "\t\t" + "A B C D" + "\t\t\t" + "A B C D");
                for (int i = 0; i < 5; i++)
                {
                    processor[i].ShowProcessor();
                }
                System.out.println("-----------------------------------------------------------------\n");

            }

            System.out.println("是否还需要测试该算法(yes/no):");
            String ask = scan.next();
            if (ask.equals("no"))
            {
                break;
            }
        }
    }
}
