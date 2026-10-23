package com.example.damnbignumb;

public class DamnBigNumber {
    private String numStr = "0";

    public String getNumStr() {
        return numStr;
    }

    public void setNumStr(String numStr) {
        this.numStr = numStr;
    }

    public DamnBigNumber() {
        setNumStr("0");
    }
    public DamnBigNumber(int x) {
        setNumStr(String.format("%d", x));
    }
    public DamnBigNumber(long x) {
        setNumStr(String.format("%d", x));
    }
    public DamnBigNumber(String xStr) {
        setNumStr(xStr);
    }

    public static final DamnBigNumber DBN_ZERO = new DamnBigNumber(0);
    public static final DamnBigNumber DBN_ONE = new DamnBigNumber(1);

    @Override
    public String toString() {
        return getNumStr();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public long getLong() {
        return Long.parseLong(getNumStr());
    }


    /**
     * TODO increment() - What if number is damn too big?
     * @return DamnBigNumber
     */
    public DamnBigNumber increment() {
        return this;
    }

    /**
     * TODO decrement() - What if number is damn too big?
     * @return DamnBigNumber
     */
    public DamnBigNumber decrement() {
        return this;
    }

    /**
     * TODO - implement isPrime
     * @return boolean
     */
    public boolean isPrime() {
        return false;
    }
}