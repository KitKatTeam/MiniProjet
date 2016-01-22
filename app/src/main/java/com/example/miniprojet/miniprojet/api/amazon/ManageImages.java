package com.example.miniprojet.miniprojet.api.amazon;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

/**
 * Created by loicleger on 21/01/16.
 */
public class ManageImages {
    public static void UploadFile(Uri imageUri)
    {
        String existingBucketName = "kitkatdevimages";
        String keyName = "Pic.jpg";

        String amazonFileUploadLocationOriginal = existingBucketName + "/images/";
        AWSCredentials credential = new BasicAWSCredentials("LlegerDev", "jauzion82");

// Transfer a file to an S3 bucket.


        AmazonS3 s3Client = new AmazonS3Client(credential);

        TransferManager manager = new TransferManager(s3Client);
        File stream = null;
        stream = new File(imageUri.getPath());
        PutObjectRequest por = new PutObjectRequest(amazonFileUploadLocationOriginal,keyName,stream);
        // Send the request.
        manager.upload(por);
    }
}
