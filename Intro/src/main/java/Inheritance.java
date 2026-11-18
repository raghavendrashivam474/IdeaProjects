class inherit{
    int a = 10;
    void display(){
        System.out.println(a);
    }
}
class inhertit extends inherit{
    int b=5;
    void disp(){
        System.out.println(b);
    }
}
public class Inheritance extends inhertit  {

        public static void main(String[] args){
            Inheritance i = new Inheritance();
            System.out.println(i.a);
            i.display();
            i.disp();
        }
}
