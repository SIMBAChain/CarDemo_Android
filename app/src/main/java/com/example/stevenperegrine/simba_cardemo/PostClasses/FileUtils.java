package com.example.stevenperegrine.simba_cardemo.PostClasses;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FileUtils {

    public static File createImageFile(Context context) throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }


    public static String getFileExtension(String filename)
    {
        if (filename == null) {
            return null;
        }
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int indexOfLastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        int extensionPos = filename.lastIndexOf('.');
        int lastSeparator = indexOfLastSeparator;
        int indexOfExtension = lastSeparator > extensionPos ? -1 : extensionPos;
        int index = indexOfExtension;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }


}
