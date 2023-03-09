package com.haoyue.svhlauncher.gnop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author Nstar
 * @version 1.0
 */
public class GNopUtils {

    //private static final Logger logger = LoggerFactory.getLogger(GNopUtils.class);
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 使用secret对paramValues按以下算法进行签名：
     * uppercase(hex(sha1(secretkey1value1key2value2...secret))
     *
     * @param paramValues 参数列表
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> paramValues, String secret) {
        return sign(paramValues, null, secret);
    }

    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     *
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String sign(Map<String, String> paramValues, List<String> ignoreParamNames, String secret) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] sha1Digest = getSHA1Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (IOException e) {
            throw new GNopException(e);
        }
    }

    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static String getMd5(String data) {

        try {
            byte[] md5Digest = getMD5Digest(data);
            char str[] = new char[32];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                str[k++] = hexDigits[md5Digest[i] >>> 4 & 0xf];
                str[k++] = hexDigits[md5Digest[i] & 0xf];
            }
            //return byte2hex(md5Digest);
            return new String(str);
        } catch (IOException e) {
            throw new GNopException(e);
        }

    }

    public static String getMd5(byte[] data) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Digest = md.digest(data);
            char str[] = new char[32];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                str[k++] = hexDigits[md5Digest[i] >>> 4 & 0xf];
                str[k++] = hexDigits[md5Digest[i] & 0xf];
            }
            //return byte2hex(md5Digest);
            return new String(str);
        } catch (Exception e) {
            throw new GNopException(e);
        }

    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

}

