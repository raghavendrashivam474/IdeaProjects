class animal{
    void sound(){
        System.out.println("sound!");
    }
}
class dog extends animal{
    void sound(){
        System.out.println("Barks");
    }
}
public class HybridInheritance extends dog {
    void sound(){
        System.out.println("Random");
    }
    public static void main(String[] args){
        HybridInheritance h = new HybridInheritance();
        h.sound();
        dog d = new dog();
        d.sound();
    }
}
