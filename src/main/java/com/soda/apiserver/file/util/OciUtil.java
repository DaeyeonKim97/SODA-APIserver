package com.soda.apiserver.file.util;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreateBucketDetails;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import com.oracle.bmc.objectstorage.transfer.UploadManager.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class OciUtil {
    private final ConfigFile config;
    private final AuthenticationDetailsProvider provider;

    public OciUtil() throws IOException {
        config = ConfigFileReader.parse("~/.oci/config","DEFAULT");
        this.provider = new ConfigFileAuthenticationDetailsProvider(config);
    }

    public void upload(String bucketName, String objectName, String contentType, String pathName) throws Exception {
        ObjectStorage client = new ObjectStorageClient(provider);
        client.setRegion(Region.AP_SEOUL_1);
        String namespace = client.getNamespace(GetNamespaceRequest.builder().build()).getValue();

        UploadConfiguration uploadConfiguration = UploadConfiguration.builder()
                .allowMultipartUploads(true)
                .allowParallelUploads(true)
                .build();

        UploadManager uploadManager = new UploadManager(client, uploadConfiguration);
        Map<String, String> metadata = null;
        String contentEncoding = null;
        String contentLanguage = null;

        File body = new File(pathName);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucketName(bucketName)
                .namespaceName(namespace)
                .objectName(objectName)
                .contentType(contentType)
                .build();

        UploadRequest uploadDetails = UploadRequest.builder(body).allowOverwrite(true).build(request);
        UploadResponse response = uploadManager.upload(uploadDetails);

        System.out.println(response);
        client.close();
    }

    public void read(String bucketName, String objectName) throws Exception {
        ObjectStorage client = new ObjectStorageClient(provider);
        client.setRegion(Region.AP_SEOUL_1);
        String namespace = client.getNamespace(GetNamespaceRequest.builder().build()).getValue();

        GetObjectRequest request =
                GetObjectRequest.builder()
                        .namespaceName(namespace)
                        .bucketName(bucketName)
                        .objectName(objectName)
                        .build();
        GetObjectResponse response = client.getObject(request);
        System.out.println(response);

        client.close();
    }
}
