package g2d.utils;

import com.alibaba.fastjson.JSON;

import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {
    public static byte[] readFile(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readString(String path) {
        try {
            byte data[] = readFile(path);
            if (data != null)
                return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFile(String path, byte data[]) {
        try {
            Files.write(Paths.get(path), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeString(String path, String str) {
        try {
            byte data[] = str.getBytes("UTF-8");
            writeFile(path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readJSON(String path) {
        String str = readString(path);
        if (str != null)
            return JSON.parse(str);
        else
            return null;
    }

    public static void writeJSON(String path, JSON json) {
        writeString(path, json.toJSONString());
    }
}