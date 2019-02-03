/**
 * Demonstrates using the AutoML client to predict an image.
 *
 * @param projectId the Id of the project.
 * @param computeRegion the Region name.
 * @param modelId the Id of the model which will be used for text classification.
 * @param filePath the Local text file path of the content to be classified.
 * @param scoreThreshold the Confidence score. Only classifications with confidence score above
 *     scoreThreshold are displayed.
 * @throws IOException on Input/Output errors.
 */
public static void predict(
    String projectId,
    String computeRegion,
    String modelId,
    String filePath,
    String scoreThreshold)
    throws IOException {

  // Instantiate client for prediction service.
  PredictionServiceClient predictionClient = PredictionServiceClient.create();

  // Get the full path of the model.
  ModelName name = ModelName.of(projectId, computeRegion, modelId);

  // Read the image and assign to payload.
  //ByteString content = ByteString.copyFrom(Files.readAllBytes(Paths.get(filePath)));
  Image image = Image.newBuilder().setImageBytes(Base64).build();
  ExamplePayload examplePayload = ExamplePayload.newBuilder().setImage(image).build();

  // Additional parameters that can be provided for prediction e.g. Score Threshold
  Map<String, String> params = new HashMap<>();
  if (scoreThreshold != null) {
    params.put("score_threshold", scoreThreshold);
  }
  // Perform the AutoML Prediction request
  PredictResponse response = predictionClient.predict(name, examplePayload, params);

  System.out.println("Prediction results:");
  for (AnnotationPayload annotationPayload : response.getPayloadList()) {
    System.out.println("Predicted class name :" + annotationPayload.getDisplayName());
    System.out.println(
        "Predicted class score :" + annotationPayload.getClassification().getScore());
  }
}