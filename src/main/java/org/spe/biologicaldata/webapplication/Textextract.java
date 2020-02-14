
package org.spe.biologicaldata.webapplication;
// Imports the Google Cloud client library

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.protobuf.ByteString;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Textextract implements TextExtractController{


  public String getText(String filePath) throws IOException {
    Credentials myCredentials = ServiceAccountCredentials
        .fromStream(new FileInputStream("src/main/resources/textract-15059e3faf5f.json"));

    ImageAnnotatorSettings imageAnnotatorSettings = ImageAnnotatorSettings.newBuilder()
        .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials)).build();

    PrintStream out = System.out;

    // String filePath="src/main/resources/static/images/test.jpg";
    List<AnnotateImageRequest> requests = new ArrayList<>();

    ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));
String returnvalue="";
    Image img = Image.newBuilder().setContent(imgBytes).build();
    Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
    AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
    requests.add(request);

    try (ImageAnnotatorClient client = ImageAnnotatorClient.create(imageAnnotatorSettings)) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();

      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          out.printf("Error: %s\n", res.getError().getMessage());

        }
        for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
          // out.printf("Text: %s\n", annotation.getDescription());
          returnvalue=returnvalue+annotation.getDescription();

        }
      }
    }
    return returnvalue;
  }



}


