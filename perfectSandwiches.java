import java.util.*;

public class perfectSandwiches {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int a = sc.nextInt(); // To read cheese slices
            int b = sc.nextInt(); // To read sausage slices
            if (a <= b) {
                System.out.println(-1);
            } else {
                System.out.println(Math.min(b, a - b)); // Printing Minimum value

            }

        }

        sc.close();

    }

}