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
    public static void UploadFile()
    {
        // Création de l'intent de type ACTION_IMAGE_CAPTURE
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Création d'un fichier de type image
        File photo = new File("/mnt/emmc/",  "Pic.jpg");
        // On fait le lien entre la photo prise et le fichier que l'on vient de créer
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        Uri imageUri;

        imageUri = Uri.fromFile(photo);

        String existingBucketName = "kitkatdevimages";
        String keyName = "Pic.jpg";

        String filePath = "/Users/loicleger/Downloads/avatar10-4.jpg";
        String amazonFileUploadLocationOriginal = existingBucketName + "/images/";
        AWSCredentials credential = new BasicAWSCredentials("access key", "secret key");

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
