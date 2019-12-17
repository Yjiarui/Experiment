package process;

import java.util.*;

public class processMenu{
    ArrayList<JCB> jcb;//存放所有进程
    LinkedList<JCB> link;//存放已经进入队列的进程
    ArrayList<JCB> new_jcb;//存放按指定调度算法排序后的进程
    JCB nowProcess;//当前应执行进程

    public void init() {
        jcb = new ArrayList<JCB>();
        link = new LinkedList<JCB>();
        new_jcb = new ArrayList<JCB>();

        JCB p1 = new JCB("1",0,4,3);
        JCB p2 = new JCB("2",1,3,2);
        JCB p3 = new JCB("3",2,5,3);
        JCB p4 = new JCB("4",3,2,1);
        JCB p5 = new JCB("5",4,4,5);
        jcb.add(p1);jcb.add(p2);jcb.add(p3);jcb.add(p4);jcb.add(p5);

        Collections.sort(jcb,new compareAt_St());
    }
    public void FCFS(){//先来先服务算法
        ProcessQueue pq = new ProcessQueue();//调用内部类
        pq.Enqueue();//让最先到达的进程先入队
        System.out.println("********************************************");
        while (!link.isEmpty()){
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.Enqueue();//已到达的进程入队
        }
    }

    public void SJF(){//短进程优先算法
        ProcessQueue pq = new ProcessQueue();
        pq.Enqueue();
        System.out.println("********************************************");
        while (!link.isEmpty()){
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.Enqueue();//已到达的进程入队
            Collections.sort(link,new compareSt());//队列中的进程还需按服务时间长度进行排序
        }
    }

    public void RR(){//时间片轮转调度算法
        ProcessQueue pq = new ProcessQueue();
        pq.Enqueue();
        System.out.println("********************************************");
        while (!link.isEmpty()){
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue(1);//出队，一次一个
        }
    }
    public void PSA(){//优先级优先调度算法
        ProcessQueue pq = new ProcessQueue();
        pq.Enqueue();
        System.out.println("********************************************");
        while (!link.isEmpty()){
            pq.DisplayQueue();//打印当前队列中的进程
            pq.Dequeue();//出队，一次一个
            pq.Enqueue();//已到达的进程入队
            Collections.sort(link,new comparePriority());
        }
    }

    public void printProcess() {
        System.out.println("进程名  到达时间   服务时间    优先级   开始时间   完成时间   周转时间  带权周转时间");
        for (int i = 0; i < new_jcb.size();i++){
            System.out.println(" P"+new_jcb.get(i).name+"      "+new_jcb.get(i).arriveTime+"           "+
                    new_jcb.get(i).serveTime+"            "+new_jcb.get(i).priority+"        "+new_jcb.get(i).beginTime+"        "+new_jcb.get(i).finishTime+
                    "            "+new_jcb.get(i).roundTime+"           "+new_jcb.get(i).aveRoundTime);
        }
        new_jcb.clear();
    }
    public void choose(int n){

    }

    class ProcessQueue{
        int k = 0;//jcb中的进程便利时的下标
        int nowTime = 0;//当前时间
        double sliceTime;//轮转调度时间片
        int i = 0;//记录当前出入队列的次数
        public void Enqueue(){
            while (k < jcb.size()){
                if (jcb.get(k).arriveTime <= nowTime){
                    link.add(jcb.get(k));
                    k++;
                }else {
                    break;
                }
            }
        }
        public void Dequeue(){
            nowProcess  = link.removeFirst();
            nowProcess.beginTime = nowTime;
            nowProcess.finishTime = nowProcess.beginTime + nowProcess.serveTime;
            nowProcess.roundTime = nowProcess.finishTime - nowProcess.arriveTime;
            nowProcess.aveRoundTime = (double) nowProcess.roundTime / nowProcess.serveTime;
            nowTime = nowProcess.finishTime;
            new_jcb.add(nowProcess);
            for (int i = 0; i < link.size();++i){
                link.get(i).waitTime++;
            }
        }
        public void Dequeue(double sliceTime){
            nowProcess  = link.removeFirst();
            nowTime += sliceTime;
            nowProcess.clock += sliceTime;
            if (nowProcess.clock >= nowProcess.serveTime) {
                nowProcess.finishTime = nowTime;
                nowProcess.roundTime = nowProcess.finishTime - nowProcess.arriveTime;
                nowProcess.aveRoundTime = (double) nowProcess.roundTime / nowProcess.serveTime;
                new_jcb.add(nowProcess);
                Enqueue();
            }else {
                Enqueue();
                link.addLast(nowProcess);
            }
        }
        public void DisplayQueue(){
           i++;
            System.out.println("第"+ i +"次队列中排队的进程"+link);
    }
    }
}
class compareSt implements Comparator<JCB>{
    public int compare(JCB arg0,JCB arg1){
        return arg0.serveTime - arg1.serveTime;
    }
}
class compareAt_St implements Comparator<JCB>{
    public int compare(JCB o1,JCB o2){
        int a = o1.arriveTime - o2.arriveTime;
        if (a > 0)
            return 1;
        else if (a == 0){
            return o1.serveTime > o2.serveTime ? 1 : -1;
        }else
            return -1;
    }
}
class comparePriority implements Comparator<JCB>{
    public int compare(JCB arg0,JCB arg1){
        return arg1.priority - arg0.priority;
    }
