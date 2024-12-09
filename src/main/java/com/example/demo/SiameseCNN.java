package com.example.demo;


import java.util.Map;

public class SiameseCNN {
    private String dataPath;
    private int trainBatchSize;
    private int validBatchSize;

    public SiameseCNN(Map<String, Object> config) {
        this.dataPath = (String) config.get("data_path");
        this.trainBatchSize = (int) config.get("valid_batch_size");
        this.validBatchSize = (int) config.get("train_batch_size");
    }
}
