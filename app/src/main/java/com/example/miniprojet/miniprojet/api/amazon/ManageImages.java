package com.example.miniprojet.miniprojet.api.amazon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.amazonaws.util.IOUtils;
import com.example.miniprojet.miniprojet.db.pojo.Comment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright (C) 2016 Chaloupy Julien, Leger Loic, Magras Steeve

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

public class ManageImages  extends AsyncTask<String,Void,Void>{
    private static final String BUCKET_NAME = "kitkatdevimages";
    private static final String ACCESS_KEY = "AKIAJF6BO4GMU6TY7ATA";
    private static final String PRIVATE_KEY = "kQkj+Nv7RNzW3vTG8zAWOGn5jnDIlXmlJScBZePB";

    private File photo;
    private Bitmap bitmap;
    private String keyName;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private void uploadFile()
    {
        AWSCredentials credential = new BasicAWSCredentials(ACCESS_KEY, PRIVATE_KEY);
        AmazonS3 s3Client = new AmazonS3Client(credential);

        PutObjectRequest por =   new PutObjectRequest(BUCKET_NAME,keyName,photo);
        PutObjectResult object = s3Client.putObject(por);

        Log.d("AMAZONEEE",object.getETag());
    }

    private void downloadFile()
    {
        AWSCredentials credential = new BasicAWSCredentials(ACCESS_KEY, PRIVATE_KEY);

        AmazonS3 s3Client = new AmazonS3Client(credential);

        GetObjectRequest por =   new GetObjectRequest(BUCKET_NAME,keyName);
        S3Object object = s3Client.getObject(por);
        //process inputStream
        try {
            byte[] photo = IOUtils.toByteArray(object.getObjectContent());
            bitmap = BitmapFactory.decodeByteArray(photo,0,photo.length);
        } catch (IOException e) {
            Log.e("AMAZONEEE",object.getBucketName());
        }
        Log.d("AMAZONEEE", String.valueOf(bitmap.getWidth()));
    }

    @Override
    protected Void doInBackground(String...params) {
        if(params[0] == "Upload") {
            this.uploadFile();
        } else {
            this.downloadFile();
        }
        return null;
    }

}
