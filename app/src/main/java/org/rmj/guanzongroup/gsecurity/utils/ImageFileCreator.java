package org.rmj.guanzongroup.gsecurity.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageFileCreator {

    private static final String PATROL_SELFIE_LOG = "PatrolSelfieImages";

    public static Uri CreateImageUri(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        // Create the File where the photo should go
        String root = String.valueOf(context.getExternalFilesDir(null));
        File storageDirectory = new File(root + "/" + PATROL_SELFIE_LOG + "/");
        if (!storageDirectory.exists()) {
            storageDirectory.mkdirs();
        }

        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fileName = "Patrol" + dateTime + ".jpg";

        File imageFile = new File(
                storageDirectory,
                fileName);

        // Continue only if the File was successfully created
        Uri photoURI = FileProvider.getUriForFile(context,
                "org.rmj.guanzongroup.gsecurity" + ".provider",
                imageFile);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        return photoURI;
    }
}
