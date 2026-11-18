class Animal{
    void sound(){
        System.out.println("Animal creates sound!");
    }
}
class Bandar extends Animal{
    void sound(){
        System.out.println("Khow khow!");
    }
}
class Dog extends Animal{
    void sound(){
        System.out.println("Bhow Bhow!");
    }
}

public class Polymorphism {
    public static void main(String [] args){
        Animal a = new Animal();
        Animal m = new Bandar();
        Animal d = new Dog();
        a.sound();
        m.sound();
        d.sound();
    }
}
