public class Methods {
//    static void Btech_cse(){
//        System.out.println("My first method!");
//    }
//    static void Btech_string(String fname , int age){
//        System.out.println(fname+" Hello!"+" age is:"+age);
//    }
    static int sum(int a,int b){
        return a+b;
    }
    static double sum(double a,double b,double c){
        return a+b+c;
    }
    public static void main(String[] args){
        System.out.println("This is my method!");
//        Methods m = new Methods();
//        Btech_cse();
//        Btech_cse();
//        Btech_cse();
//          Btech_string("Mohan",45);
//          Btech_string("Shreya",19);
//          Btech_string("Raghav",22);
        System.out.println(sum(5,6));
        System.out.println(sum(5.1,4.1,7.4));
    }
}
