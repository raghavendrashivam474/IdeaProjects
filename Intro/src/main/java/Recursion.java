import java.util.Scanner;
public class Recursion {
    static int sum_till_num(int n) {
        if (n == 0) {
            return 0;
        } else {
            return n + sum_till_num(n - 1);
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        System.out.println(sum_till_num(num));
    }
}
