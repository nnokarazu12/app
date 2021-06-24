package com.example;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

// Handler value: example.Handler
public class Test implements RequestHandler<Map<String,String>, String>{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
  @Override
  public String handleRequest(Map<String,String> event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    String response = "";
    // process event
    logger.log("EVENT: " + gson.toJson(event));
    logger.log("EVENT TYPE: " + event.getClass().toString());
    String bucket = "face-images-saic";
    String sourceImage = "S223-01-t10_01.png";
    //String key = event.get("source");
    
    CompareFaces cf = new CompareFaces(bucket, sourceImage);
    //response = gson.toJson(cf.getResults().toString());
    response = gson.toJson(event);
    return response;
  }
}
