package ps.demo.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.apache.catalina.core.ApplicationPart;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTool {

    private static int FILE_NAME_LENGTH = 128;

    public static MessageDigest MD5 = null;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
    }

    public static File touchFileInHomeWithDateDirAndTsPrefixByName(String filename) {
        File dir = new File(FileUtil.getUserHomePath() + StringXTool.DIR_SEPERATOR
                + LocalDate.now() + StringXTool.DIR_SEPERATOR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir.getPath() + StringXTool.DIR_SEPERATOR + StringXTool.getNowTimeWithMsStrOnly() + "-" + StringXTool.toValidFileName(filename));
        return file;
    }


    public static File writeToHomeFileInJsonUTF8(Object object, String fileName) {
        File file = touchFileInHomeWithDateDirAndTsPrefixByName(fileName);
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(object), file);
        return file;
    }

    public static File writeToHomeFileInJsonUTF8(Object object, File file) {
        return writeToHomeFileInJsonUTF8(object, file, false);
    }

    public static File writeToHomeFileInJsonUTF8(Object object, File file, boolean isAppend) {
        try (
                BufferedWriter bufferedWriter = FileUtil.getWriter(file, StandardCharsets.UTF_8, isAppend);) {
            bufferedWriter.write("\n");
            bufferedWriter.write(JSONUtil.toJsonPrettyStr(object));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getAppPath() {
        String path = FileTool.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        if (path.toLowerCase(Locale.ROOT).trim().endsWith(".jar")) {
            File jar = new File(path);
            return jar.getParentFile();
        } else {
            return new File(path);
        }
    }

    public static File getFileInAppPath(String fileName) {
        File path = getAppPath();
        return new File(path.getAbsolutePath() + File.separator + fileName);
    }

    public static JSON readFromFileJsonUTF8(File file) {
        //String str = FileUtil.readUtf8String(file);
        return JSONUtil.readJSON(file, StandardCharsets.UTF_8);
    }

    public static void deleteFileOrFolder(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFileOrFolder(f);
            }
        } else {
            file.delete();
        }
    }

    public static String fileMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new BigInteger(1, MD5.digest()).toString(16);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> searchInFile(File file, String regex) {
        List<String> findings = new ArrayList<>();
        Pattern pt = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        String content = null;
        try {
            content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Matcher mt = pt.matcher(content);
        while (mt.find()) {
            findings.add(content.substring(mt.start(), mt.end()));
        }
        return findings;
    }


    public static void copy(File from, File to, int bufsize) {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);
            byte[] buf = new byte[bufsize];
            int index;
            Random rd = new Random();
            while (-1 != (index = fis.read(buf, 0, buf.length))) {
                fos.write(buf, 0, index);
                try {
                    Thread.sleep(100 + rd.nextInt(900));
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeSilently(fis);
            closeSilently(fos);
        }
    }

    public static void closeSilently(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {

            }
        }
    }

    public static File resolverTempFile(MultipartFile multipartFile) throws IOException {
        Class<? extends MultipartFile> multipartFileClass = multipartFile.getClass();
        try {
            Field partField = multipartFileClass.getDeclaredField("part");
            partField.setAccessible(true);
            ApplicationPart part = (ApplicationPart) partField.get(multipartFile);
            Field fileItemField = ApplicationPart.class.getDeclaredField("fileItem");
            fileItemField.setAccessible(true);
            FileItem fileItem = (FileItem) fileItemField.get(part);
            Class<? extends FileItem> fileItemClass = fileItem.getClass();
            Field tempFileField = fileItemClass.getDeclaredField("tempFile");
            tempFileField.setAccessible(true);
            return (File) tempFileField.get(fileItem);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }



}
