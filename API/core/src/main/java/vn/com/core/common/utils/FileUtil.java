package vn.com.core.common.utils;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;

public class FileUtil {

    public static File getTempFile(String tempFolder) throws IOException {
        if (!ValidationUtil.isNullOrEmpty(tempFolder)) {
            return File.createTempFile("temp_file", ".tmp", new File(tempFolder));
        } else {
            return File.createTempFile("temp_file", ".tmp");
        }
    }

    public static void decodeBase64StringToFile(String base64String, String destFile) throws IOException {
        byte[] byteDecode = Base64.decodeBase64(StringEscapeUtils.unescapeJava(base64String));
        try (FileOutputStream fos = new FileOutputStream(destFile)) {
            fos.write(byteDecode);
        }
    }

    public static String encodeFileToBase64Binary(String fileName)
            throws IOException {
        File file = new File(fileName);
        return encodeFileToBase64Binary(file);
    }

    public static String encodeFileToBase64Binary(File file)
            throws IOException {
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encodeBase64(bytes);

        return new String(encoded);
    }

    public static byte[] loadFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return loadFile(is);
        }
    }

    public static byte[] loadFile(InputStream is) throws IOException {
        return IOUtils.toByteArray(is);
    }

    public static byte[] loadFileClassicType(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                // File is too large
            }
            byte[] bytes = new byte[(int) length];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
            return bytes;
        }
    }

}
