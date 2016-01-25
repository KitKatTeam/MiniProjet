package com.example.miniprojet.miniprojet.api.amazon;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.example.miniprojet.miniprojet.db.pojo.Comment;

import java.io.File;

/**
 * Created by loicleger on 21/01/16.
 */
public class ManageImages  extends AsyncTask<String,Void,Void>{

    private File photo;
    private Context context;

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void UploadFile(File photo, Context context)
    {
        String existingBucketName = "kitkatdevimages/";
        String keyName = "Pic.jpg";

        AWSCredentials credential = new BasicAWSCredentials("AKIAJF6BO4GMU6TY7ATA", "kQkj+Nv7RNzW3vTG8zAWOGn5jnDIlXmlJScBZePB");



        AmazonS3 s3Client = new AmazonS3Client(credential);

        PutObjectRequest por =   new PutObjectRequest( "kitkatdevimages",keyName,photo);
        PutObjectResult object = s3Client.putObject(por);

        Log.d("AMAZONEEE",object.getETag());
    }

    private void downloadFile()
    {
        String existingBucketName = "kitkatdevimages/";
        String keyName = "Pic.jpg";

        AWSCredentials credential = new BasicAWSCredentials("AKIAJF6BO4GMU6TY7ATA", "kQkj+Nv7RNzW3vTG8zAWOGn5jnDIlXmlJScBZePB");



        AmazonS3 s3Client = new AmazonS3Client(credential);

        GetObjectRequest por =   new GetObjectRequest( "kitkatdevimages",keyName);
        S3Object object = s3Client.getObject(por);
        Log.d("AMAZONEEE",object.getBucketName());
    }

    @Override
    protected Void doInBackground(String...params) {
        this.UploadFile(getPhoto(),getContext());
        return null;
    }
}
