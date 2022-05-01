package main.service;

import main.domain.Keyable;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import static java.lang.Math.max;
import static java.math.BigInteger.ZERO;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.Arrays.copyOf;

public class InterpolationSearch {
    public static <K extends Comparable<K>, T extends Serializable & Keyable<K>> int interpolationSearch(K key, List<T> array) {
        int lowEnd = 0;
        int highEnd = array.size() - 1;
        K leftKey = array.get(lowEnd).getKey();
        K rightKey = array.get(highEnd).getKey();
        int result = 0;
        while (key.compareTo(leftKey) >= 0 && key.compareTo(rightKey) <= 0) {
            int probe = lowEnd + interpolate((String) leftKey, (String) rightKey, (String) key, highEnd - lowEnd);
             result = key.compareTo(array.get(probe).getKey());
            if (result == 0) {
                return 0;
            }
            if (result > 0) {
                lowEnd = probe + 1;
            } else {
                highEnd = probe - 1;
            }
        }
        return result;
    }


    public static int interpolate(String ys, String xs, String iOfTs, int id) {
        int maxLen = max(max(xs.length(), ys.length()), iOfTs.length());
        BigInteger x = new BigInteger(1, copyOf(xs.getBytes(US_ASCII), maxLen));
        BigInteger y = new BigInteger(1, copyOf(ys.getBytes(US_ASCII), maxLen));
        BigInteger iOfT = new BigInteger(1, copyOf(iOfTs.getBytes(US_ASCII), maxLen));
        BigInteger d = BigInteger.valueOf(id);
        BigInteger den = x.subtract(y);
        return ZERO.equals(den) ? 0 : (int) d.multiply(iOfT.subtract(y)).divide(den).longValue();
    }

}
