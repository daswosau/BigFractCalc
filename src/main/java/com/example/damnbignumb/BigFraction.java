package com.example.damnbignumb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class BigFraction implements Comparable<BigFraction> {
    private BigInteger numerator;
    private BigInteger denominator;

    public BigInteger getNumerator() {
        return numerator;
    }

    public void setNumerator(BigInteger numerator) {
        this.numerator = numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public void setDenominator(BigInteger denominator) {
        this.denominator = denominator;
    }

    public BigFraction() {
        setNumerator(BigInteger.ZERO);
        setDenominator(BigInteger.ONE);
    }

    // A nevező mindig pozitív marad, az előjel a számlálóba kerül
    public BigFraction(BigInteger numerator, BigInteger denominator) {
        if (denominator.signum() == 0) {
            throw new ArithmeticException("A nevező nem lehet nulla");
        }
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        setNumerator(numerator);
        setDenominator(denominator);
    }

    public BigFraction(String numeratorStr, String denominatorStr) {
        this(new BigInteger(numeratorStr.trim()), new BigInteger(denominatorStr.trim()));
    }

    public BigFraction(long numerator, long denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public BigFraction(int numerator, int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    // "a/b", "(a/b)" vagy tizedestört ("8270211815834.987" -> 8270211815834987/1000)
    public static BigFraction parse(String s) {
        s = s.trim();
        if (s.startsWith("(") && s.endsWith(")")) {
            s = s.substring(1, s.length() - 1).trim();
        }
        int slash = s.indexOf('/');
        if (slash >= 0) {
            return new BigFraction(s.substring(0, slash), s.substring(slash + 1));
        }
        BigDecimal dec = new BigDecimal(s);
        if (dec.scale() >= 0) {
            return new BigFraction(dec.unscaledValue(), BigInteger.TEN.pow(dec.scale()));
        }
        return new BigFraction(dec.unscaledValue().multiply(BigInteger.TEN.pow(-dec.scale())), BigInteger.ONE);
    }

    @Override
    public String toString() {
        return String.format("(%s/%s)", getNumerator().toString(), getDenominator().toString());
    }

    public BigDecimal toBigDecimal(MathContext mc) {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator), mc);
    }

    public BigDecimal toBigDecimal() {
        return toBigDecimal(MathContext.DECIMAL128);
    }

    // Értékes jegyek száma alapján kerekít, így a bitangkicsi értékek sem lesznek 0-ák; sosem ír 1E-21 alakot
    public String toDecimalString(int significantDigits) {
        return toBigDecimal(new MathContext(significantDigits)).toPlainString();
    }

    @Override
    public int compareTo(BigFraction other) {
        return numerator.multiply(other.denominator).compareTo(other.numerator.multiply(denominator));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BigFraction && compareTo((BigFraction) obj) == 0;
    }

    @Override
    public int hashCode() {
        BigInteger g = numerator.gcd(denominator);
        return numerator.divide(g).hashCode() * 31 + denominator.divide(g).hashCode();
    }
}
