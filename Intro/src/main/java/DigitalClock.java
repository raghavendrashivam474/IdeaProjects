import java.util.Scanner;

public class DigitalClock {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Hours: ");
        int hours = sc.nextInt();

        System.out.print("Enter Minutes: ");
        int minutes = sc.nextInt();

        System.out.print("Enter Seconds: ");
        int seconds = sc.nextInt();

        while (true) {

            System.out.printf("%02d:%02d:%02d\r",
                    hours, minutes, seconds);

            Thread.sleep(1000);

            seconds++;

            if (seconds == 60) {
                seconds = 0;
                minutes++;
            }

            if (minutes == 60) {
                minutes = 0;
                hours++;
            }

            if (hours == 24) {
                hours = 0;
            }
        }
    }
}
