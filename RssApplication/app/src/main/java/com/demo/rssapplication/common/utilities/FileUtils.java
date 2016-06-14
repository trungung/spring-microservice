package com.demo.rssapplication.common.utilities;

import android.content.Context;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * Save file to internal storage
     *
     * @param context    App context
     * @param frameData  input data
     * @param folderName location of file
     * @return file path
     */
    public static String saveInternalFile(Context context, byte frameData[], String folderName) {

        if (frameData != null && frameData.length > 0) {

            String fileName = String.valueOf(DateTimeUtils.getCurrentUTCInMillis());

            File fileDir = getInternalPath(context, folderName);
            File file = new File(fileDir, fileName);
            try {
                FileOutputStream fis = new FileOutputStream(file);
                fis.write(frameData);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return file.getPath();
        }

        return "";
    }

    /**
     * Get the internal path
     *
     * @param context    App context
     * @param folderName location of file
     * @return internal path
     */
    public static File getInternalPath(Context context, String folderName) {

        File fileDir = new File(context.getFilesDir(), folderName);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        Log.d("FILE UTILS", "InternalPath: " + fileDir.getPath());

        return fileDir;
    }

    /**
     * Delete all files in directory with path
     *
     * @param fileList location of file
     */
    public static void deleteFiles(File fileList) {

        // check if dir is not null
        if (fileList != null) {

            Log.d(TAG, "DELETE FILES: " + fileList.getPath());

            // so we can list all files
            File[] filenames = fileList.listFiles();

            // loop through each file and delete
            for (File tmpf : filenames) {
                tmpf.delete();
            }
        }
    }

    /**
     * Get MimeType of url
     *
     * @param url location
     * @return MimeType of url
     */
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);

        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }

        Log.d("FILE UTILS", "MimeType: " + type);
        return type;
    }

    /**
     * Copy file.
     *
     * @param sourceFile - The source file
     * @param destFile   - The destination file
     * @return True if coping is success. Otherwise, return false.
     * @throws IOException
     */
    public static boolean copyFile(File sourceFile, File destFile)
            throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();

            FileChannel source = null;
            FileChannel destination = null;
            try {
                source = new FileInputStream(sourceFile).getChannel();
                destination = new FileOutputStream(destFile).getChannel();
                destination.transferFrom(source, 0, source.size());
            } finally {
                if (source != null)
                    source.close();
                if (destination != null)
                    destination.close();
            }
            return true;
        }
        return false;
    }
}
