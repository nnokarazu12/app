package com.example;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import java.util.List;

public class CompareFaces {

    private Float similarityThreshold = 0F;
    private String bucket = "face-images-saic";
    private String sourceImage;

    public CompareFaces(String bucket, String sourceImage) {
        this.bucket = bucket;
        this.sourceImage = sourceImage;
    }

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();


   public String getResults() {
        String result = "";
        S3Service s3 = new S3Service();
        List<String> objects = s3.listBucketObjects(bucket);
        for (String o: objects) {
            CompareFacesRequest request = new CompareFacesRequest()
            .withSourceImage(new Image()
            .withS3Object(new S3Object()
            .withName(sourceImage)
            .withBucket(bucket)))
            .withTargetImage(new Image()
            .withS3Object(new S3Object()
            .withName(o)
            .withBucket(bucket)))
            .withSimilarityThreshold(similarityThreshold);
            

            // Call operation
            CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);


            // Display results
            List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
            List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

            //Return no match string if there is no match
            if (uncompared.size() > 0) {
                System.out.println("Not a match");
            }
            for (CompareFacesMatch match: faceDetails){
                result += sourceImage + " | " + o + "\nSimilarity: " + match.getSimilarity() + "\n";
            }
        }
        return result;
   }

   /*public static void main(String[] args) throws Exception {
    String sourceImage = "S223-01-t10_01.png";
    System.out.println(getResults(sourceImage));
   }*/
}
