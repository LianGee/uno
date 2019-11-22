package com.bigdata.uno.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class EncodeUtil {
    public static String getFileMD5(String path) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(FileUtils.readFileToByteArray(new File(path)));
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("failed to generate md5 for file: " + path, e);
        }
    }

    public static String getMD5(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(content.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("failed to generate md5 for: " + content, e);
        }
    }

    public static String getSHA256(String content) {
        try {
            return Hex.encodeHexString(MessageDigest.getInstance("SHA-256").digest(content.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException("failed to generate sha256 for: " + content, e);
        }
    }

    public static String encodeUrl(String url) {
        return encodeUrl(url, 1);
    }

    public static String encodeUrl(String url, int times) {
        for (int i = 0; i < times; i++) {
            try {
                url = URLEncoder.encode(url, "utf8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return url;
    }

    public static String decodeUrl(String url) {
        return decodeUrl(url, 1);
    }

    public static String decodeUrl(String url, int times) {
        for (int i = 0; i < times; i++) {
            try {
                url = URLDecoder.decode(url, "utf8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return url;
    }

    public static String encodeBase64(byte[] data) {
        return Base64.encodeBase64String(data);
    }
}
