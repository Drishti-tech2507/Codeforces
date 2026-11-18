import java.util.*;

class Main {
public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int t = sc.nextInt();
    while (t-- > 0) {
        int x0 = sc.nextInt();
        int y0 = sc.nextInt();
        int r = sc.nextInt();
        boolean f = false;
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                if (dx * dx + dy * dy == r * r) {
                    System.out.println((x0 + dx) + " " + (y0 + dy));
                    f = true;
                    break;
                }
            }
            if (f) {
                break;
            }
        }
    }
    sc.close();
}

}