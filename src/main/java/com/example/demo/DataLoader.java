package com.example.demo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.io.File;

    public class DataLoader {

        public float[][] loadTrainData(String filePath) throws IOException {
            // Assuming the file is a binary file containing a serialized 2D array.
            // You need to adjust this method based on the specific file format.
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException("File not found: " + filePath);
            }

            // Read the file as a byte array
            FileInputStream fis = new FileInputStream(file);
            byte[] fileBytes = IOUtils.toByteArray(fis);
            fis.close();

            // Convert byte array to float array. This assumes the data is serialized floats.
            // If the data format is different (e.g., CSV, JSON), you'll need to parse it accordingly.
            int numFloats = fileBytes.length / Float.BYTES;
            float[] flatArray = new float[numFloats];
            for (int i = 0; i < numFloats; i++) {
                int intBits = (fileBytes[i * 4] & 0xFF) << 24 |
                        (fileBytes[i * 4 + 1] & 0xFF) << 16 |
                        (fileBytes[i * 4 + 2] & 0xFF) << 8 |
                        (fileBytes[i * 4 + 3] & 0xFF);
                flatArray[i] = Float.intBitsToFloat(intBits);
            }

            // Reshape the flat array into a 2D array. Define ROWS and COLUMNS accordingly.
            int ROWS = 100; // Example value, adjust based on your data dimensions
            int COLUMNS = numFloats / ROWS;
            float[][] array2D = new float[ROWS][COLUMNS];
            for (int i = 0; i < ROWS; i++) {
                System.arraycopy(flatArray, i * COLUMNS, array2D[i], 0, COLUMNS);
            }

            return array2D;
        }




}
