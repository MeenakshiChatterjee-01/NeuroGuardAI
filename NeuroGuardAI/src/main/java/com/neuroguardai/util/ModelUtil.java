package com.neuroguardai.util;

import weka.clusterers.SimpleKMeans;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.InputStream;

public class ModelUtil {

    private static SimpleKMeans kMeansModel;
    private static Instances dataStructure;

    // Labels for each cluster
    private static final String[] CLUSTER_LABELS = {
        "Stress", "Drowsiness", "Attention", "Depression", "Seizure", "Brain Dead"
    };

    /**
     * Trains the KMeans model using a CSV file from the classpath.
     * @param filePath path to the CSV file inside resources (e.g., "data/NeuroguardAIdatasets/Dataset_eeg.csv")
     * @throws Exception if loading or training fails
     */
    public static void trainModelFromCSV(String filePath) throws Exception {
        try (InputStream inputStream = ModelUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("CSV file not found in resources: " + filePath);
            }

            CSVLoader loader = new CSVLoader();
            loader.setSource(inputStream);
            Instances data = loader.getDataSet();

            // Remove non-numeric columns like timestamp and patientId
            for (int i = data.numAttributes() - 1; i >= 0; i--) {
                if (!data.attribute(i).isNumeric()) {
                    data.deleteAttributeAt(i);
                }
            }

            // Save structure for future predictions
            dataStructure = new Instances(data, 0);

            // Train KMeans model
            kMeansModel = new SimpleKMeans();
            kMeansModel.setNumClusters(CLUSTER_LABELS.length);
            kMeansModel.setSeed(42);
            kMeansModel.buildClusterer(data);
        }
    }

    /**
     * Predicts the mental condition based on EEG feature vector.
     * @param features array of EEG features (channels + frequency bands)
     * @return predicted mental state label
     * @throws Exception if model is not trained or prediction fails
     */
    public static String predictCondition(double[] features) throws Exception {
        if (kMeansModel == null || dataStructure == null) {
            throw new IllegalStateException("Model not trained. Call trainModelFromCSV() first.");
        }

        DenseInstance instance = new DenseInstance(dataStructure.numAttributes());
        instance.setDataset(dataStructure);

        for (int i = 0; i < features.length; i++) {
            instance.setValue(i, features[i]);
        }

        int cluster = kMeansModel.clusterInstance(instance);
        return CLUSTER_LABELS[cluster];
    }
}
