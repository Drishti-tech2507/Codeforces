import java.io.*;
import java.util.*;

public class Program4 {
    static DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));

    static int nextInt() throws IOException {
        int ret = 0, b = in.read();
        while (b < '0') b = in.read();
        while (b >= '0') {
            ret = ret * 10 + (b - '0');
            b = in.read();
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        final int MAX = 1_000_001;
        int[] spf = new int[MAX];
        for (int i = 2; i < MAX; i++) {
            if (spf[i] == 0) {
                for (int j = i; j < MAX; j += i) {
                    if (spf[j] == 0) spf[j] = i;
                }
            }
        }
        SplittableRandom rng = new SplittableRandom(System.nanoTime() ^ 0x9E3779B97F4A7C15L);
        long[] rnd = new long[MAX];
        for (int i = 0; i < MAX; i++) rnd[i] = rng.nextLong();

        int t = nextInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = nextInt();
            long[] h = new long[n];
            long[] pre = new long[n];
            long cur = 0;
            for (int i = 0; i < n; i++) {
                int x = nextInt();
                long hv = 0;
                while (x > 1) {
                    int p = spf[x];
                    int c = 0;
                    while (x % p == 0) {
                        x /= p;
                        c++;
                    }
                    if ((c & 1) == 1) hv ^= rnd[p];
                }
                h[i] = hv;
                cur ^= hv;
                pre[i] = cur;
            }
            long[] sorted = pre.clone();
            Arrays.sort(sorted);
            long ans = 0;
            for (int i = 0; i < n; i++) {
                ans += upper(sorted, h[i]) - lower(sorted, h[i]);
            }
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }

    static int lower(long[] a, long key) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    static int upper(long[] a, long key) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] <= key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}