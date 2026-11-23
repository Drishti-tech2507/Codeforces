import java.io.BufferedInputStream;
import java.io.IOException;

public class PrecisionAlignment {
    record Lab(long sum, long extraOps, boolean immutable) {
        static Lab of(long a, long b, long c) {
            long sum = a + b + c;
            if (a == b && b == c) {
                return new Lab(sum, 0, true);
            }
            if (a > b || a > c || b > c) {
                return new Lab(sum, 0, false);
            }
            long w = (b - a) + 1;
            if (a < b) {
                w = Math.min(w, (c - b) + 1);
            }
            return new Lab(sum, 2 * w, false);
        }
    }

    static final class FastScanner implements AutoCloseable {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        private int readByte() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c;
            while ((c = readByte()) != -1 && c <= ' ') ;
            if (c == -1) return Long.MIN_VALUE;
            long sign = 1;
            if (c == '-') {
                sign = -1;
                c = readByte();
            }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = readByte();
            }
            return val * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }

        @Override
        public void close() throws IOException {
            in.close();
        }
    }

    public static void main(String[] args) throws IOException {
        try (var sc = new FastScanner()) {
            int t = sc.nextInt();
            var out = new StringBuilder();

            while (t-- > 0) {
                int n = sc.nextInt();
                long k = sc.nextLong();

                var labs = new Lab[n];
                long minSum = Long.MAX_VALUE;
                long maxSum = Long.MIN_VALUE;

                for (int i = 0; i < n; i++) {
                    long a = sc.nextLong();
                    long b = sc.nextLong();
                    long c = sc.nextLong();

                    var lab = Lab.of(a, b, c);
                    labs[i] = lab;
                    minSum = Math.min(minSum, lab.sum());
                    maxSum = Math.max(maxSum, lab.sum());
                }

                long low = minSum;
                long high = maxSum + k;
                long ans = low;

                while (low <= high) {
                    long mid = low + ((high - low) >>> 1);
                    if (canAchieve(mid, k, labs)) {
                        ans = mid;
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }

                out.append(ans).append('\n');
            }

            System.out.print(out);
        }
    }

    private static boolean canAchieve(long target, long k, Lab[] labs) {
        long totalOps = 0;
        for (var lab : labs) {
            if (lab.sum() >= target) continue;
            if (lab.immutable()) return false;

            long needed = (target - lab.sum()) + lab.extraOps();
            if (needed > k - totalOps) {
                return false;
            }
            totalOps += needed;
        }
        return true;
    }
}