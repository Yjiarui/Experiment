package Bank;

//资源类
public class Sources {
    public int A;
    public int B;
    public int C;
    public int D;

    public Sources(int A,int B,int C,int D){
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
    }

    public String toString(){
        return A+" "+B+" "+C+" "+D;
    }
}
