package com.example.damnbignumb;

import java.math.BigInteger;

public class BigFractions {
    public static boolean isAlessthanB(BigFraction a, BigFraction b) {
        return a.compareTo(b) < 0;
    }

    public static boolean equals(BigFraction a, BigFraction b) {
        return a.equals(b);
    }

    public static boolean isAlessthanOrEqualB(BigFraction a, BigFraction b) {
        return equals(a, b) || isAlessthanB(a, b);
    }

    public static boolean isAgreaterthanB(BigFraction a, BigFraction b) {
        return !isAlessthanOrEqualB(a, b);
    }

    public static boolean isAgreaterthanOrEqualB(BigFraction a, BigFraction b) {
        return !isAlessthanB(a, b);
    }

    public static boolean notEquals(BigFraction a, BigFraction b) {
        return !equals(a, b);
    }

    // Bővítés: számláló és nevező szorzása k-val
    public static BigFraction expand(BigFraction a, BigInteger k) {
        if (k.signum() == 0) {
            throw new ArithmeticException("Nullával nem lehet bővíteni");
        }
        return new BigFraction(a.getNumerator().multiply(k), a.getDenominator().multiply(k));
    }

    public static BigFraction simplify(BigFraction a) {
        BigInteger g = a.getNumerator().gcd(a.getDenominator());
        return new BigFraction(a.getNumerator().divide(g), a.getDenominator().divide(g));
    }

    // A műveletek eredménye nincs automatikusan egyszerűsítve, arra ott a simplify
    public static BigFraction multiply(BigFraction a, BigFraction b) {
        return new BigFraction(a.getNumerator().multiply(b.getNumerator()),
                a.getDenominator().multiply(b.getDenominator()));
    }

    public static BigFraction reciprocal(BigFraction a) {
        if (a.getNumerator().signum() == 0) {
            throw new ArithmeticException("A nulla reciproka nem értelmezett");
        }
        return new BigFraction(a.getDenominator(), a.getNumerator());
    }

    public static BigFraction divide(BigFraction a, BigFraction b) {
        return multiply(a, reciprocal(b));
    }

    public static BigFraction add(BigFraction a, BigFraction b) {
        return new BigFraction(
                a.getNumerator().multiply(b.getDenominator()).add(b.getNumerator().multiply(a.getDenominator())),
                a.getDenominator().multiply(b.getDenominator()));
    }

    public static BigFraction subtract(BigFraction a, BigFraction b) {
        return new BigFraction(
                a.getNumerator().multiply(b.getDenominator()).subtract(b.getNumerator().multiply(a.getDenominator())),
                a.getDenominator().multiply(b.getDenominator()));
    }
}
