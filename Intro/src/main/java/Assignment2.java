import java.util.Scanner;

public class Assignment2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int arr [] = new int[5];
        arr[0]=sc.nextInt();
        arr[1]=sc.nextInt();
        arr[2]=sc.nextInt();
        if(arr[0]==1&&arr[1]==4&&arr[2]==3) {
            System.out.println("Password is correct.");
        }else{
            System.out.println("password is incorrect.");
        }
    }
}
