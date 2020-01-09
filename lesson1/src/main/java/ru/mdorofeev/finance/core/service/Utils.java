package ru.mdorofeev.finance.core.service;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;

import java.io.IOException;

public class Utils {

    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static String md5(String str) throws IOException {
        return ByteSource.wrap(str.getBytes()).hash(Hashing.sha256()).toString();
    }
}
