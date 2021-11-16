package 学习java;

public class student extends teacher {
    int a =4;
    public student(){


    }
    public student(int a){
        this();
        System.out.println();
    }

    public static void main(String[] args) {
        Object o = new student();
        teacher t = (teacher) o;
        System.out.println(t);
    }
}
