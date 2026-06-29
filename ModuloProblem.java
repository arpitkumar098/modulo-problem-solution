import java.io.*;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter wr = new PrintWriter(System.out);
        String line = br.readLine().trim();
        String[] parts = line.split("\\s+");

        int N = Integer.parseInt(parts[0]);
        int X = Integer.parseInt(parts[1]);
        int M = Integer.parseInt(parts[2]);

        long out_ = mod_sol(N, X, M);
        System.out.println(out_);

        wr.close();
        br.close();
    }

    static long mod_sol(int N, int X, int M) {
        if (M == 1) {
            return 0;
        }

        boolean[] isComposite = new boolean[X + 1];
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= X; i++) {
            if (!isComposite[i]) {
                primes.add(i);
                for (long j = (long) i * i; j <= X; j += i) {
                    isComposite[(int) j] = true;
                }
            }
        }

        long phiM = eulerPhi(M);

        final long CAP = 4_000_000_000L;
        long exactProduct = 1L;
        boolean isLarge = false;
        long productModPhi = 1L % phiM;

        for (int p : primes) {
            if (!isLarge) {
                exactProduct *= p;
                if (exactProduct > CAP) {
                    isLarge = true;
                }
            }
            productModPhi = (productModPhi * (p % phiM)) % phiM;
        }

        long exponent;
        if (!isLarge) {
            exponent = exactProduct;
        } else {
            exponent = productModPhi + phiM;
        }

        long base = N % M;
        if (base < 0) base += M;

        return modPow(base, exponent, M);
    }

    static long eulerPhi(int n) {
        long result = n;
        int num = n;
        for (int p = 2; (long) p * p <= num; p++) {
            if (num % p == 0) {
                while (num % p == 0) {
                    num /= p;
                }
                result -= result / p;
            }
        }
        if (num > 1) {
            result -= result / num;
        }
        return result;
    }

    static long modPow(long base, long exp, long mod) {
        base %= mod;
        if (base < 0) base += mod;
        long result = 1 % mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }
}
