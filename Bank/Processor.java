package Bank;

//进程类
public class Processor {
    //四个字段
    private String name;//进程名
    Sources Max;//进程所需要的最大资源
    Sources Allocation;//当前进程已分配的资源数
    Sources Need;//当前进程还需要多少资源
    public boolean Finish = false;//判断进程是否结束

    public Processor(String name,int Max_a,int Max_b,int Max_c,int Max_d,int All_a,int All_b,int All_c,int All_d,int Need_a,int Need_b,int Need_c,int Need_d){
        this.name = name;
        Max = new Sources(Max_a,Max_b,Max_c,Max_d);
        Allocation = new Sources(All_a,All_b,All_c,All_d);
        Need = new Sources(Need_a,Need_b,Need_c,Need_d);
    }
    public void ShowProcessor(){
        //打印出进程的信息
        System.out.println(name+"\t"+Max.toString()+"\t\t"+Allocation.toString()+"\t\t\t"+Need.toString());
    }
}
