class A {
    int a = 10;
}

class B extends A {
    void disply() {
        System.out.println(a);
    }
}

public class abes1 extends B {
    public static void main(String[] args) {
        abes1 i = new abes1();
        i.disply(); // Output: 10
    }
}
