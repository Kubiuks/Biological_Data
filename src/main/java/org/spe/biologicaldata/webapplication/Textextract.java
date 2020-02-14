
package org.spe.biologicaldata.webapplication;
// Imports the Google Cloud client library
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Textextract {


  void getText(String filePath) throws Exception, IOException {
		Credentials myCredentials = ServiceAccountCredentials.fromStream(
			new FileInputStream("src/main/resources/textract-15059e3faf5f.json"));
		
		ImageAnnotatorSettings imageAnnotatorSettings =
			ImageAnnotatorSettings.newBuilder()
			.setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
			.build();




    PrintStream out=System.out;
    
		//String filePath="src/main/resources/static/images/test.jpg";
  List<AnnotateImageRequest> requests = new ArrayList<>();

  ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

  Image img = Image.newBuilder().setContent(imgBytes).build();
  Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
  AnnotateImageRequest request =
      AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
  requests.add(request);

  try (ImageAnnotatorClient client = ImageAnnotatorClient.create(imageAnnotatorSettings)) {
    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
    List<AnnotateImageResponse> responses = response.getResponsesList();

    for (AnnotateImageResponse res : responses) {
      if (res.hasError()) {
        out.printf("Error: %s\n", res.getError().getMessage());
        return;
      }
      for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
		out.printf("Text: %s\n", annotation.getDescription());		
        //out.printf("Position : %s\n", annotation.getBoundingPoly());
      }
    }
  }
}



}



