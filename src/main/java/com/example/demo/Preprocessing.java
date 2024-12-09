package com.example.demo;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.Arrays;

public class Preprocessing {

    static {
        // Load the OpenCV library
        System.load("C:\\Users\\Dell\\Desktop\\opencv\\build\\java\\x64\\opencv_java4100.dll");
    }

    public static Mat preprocessImage(String inputPath, int targetWidth, int targetHeight) {
        // Load the image
        Mat image = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_GRAYSCALE);
//        if (image.channels() > 1) {
//            Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
//        }
        if (image.empty()) {

            System.err.println("Error: Cannot load image at " + inputPath);
            return null;
        }
        //String outputPath = "C:\\Users\\Dell\\Desktop\\output.jpeg";
        //saveImage(image, outputPath);
        // Convert to grayscale
        //Mat grayImage = new Mat();
        //Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Resize the image
        Mat resizedImage = new Mat();
        Size size = new Size(targetWidth, targetHeight);
        Imgproc.resize(image, image, size);

        // Normalize pixel values to range [0, 1]
       // Mat normalizedImage = new Mat();
       //image.convertTo(normalizedImage, CvType.CV_32F, 1.0 / 255.0);
       // Core.subtract(image, new Scalar(255),image);
        //Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
        image.convertTo(image, CvType.CV_8UC1,1.0,0);
       // image = new Mat(image.size(), CvType.CV_8UC1);  // Force a fresh single-channel image
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                double[] pixel = image.get(i, j);  // Get pixel value at (i, j)
                System.out.println("Pixel value at (" + i + ", " + j + "): " + Arrays.toString(pixel));
            }
        }
        String outputPath = "C:\\Users\\Dell\\Desktop\\resized.jpeg";
        saveImage(image, outputPath);


        //Core.subtract(image, new Scalar(255),image);
        outputPath = "C:\\Users\\Dell\\Desktop\\output2.jpeg";
        saveImage(image, outputPath);
        return image;
    }

    public static void saveImage(Mat image, String outputPath) {
        boolean result = Imgcodecs.imwrite(outputPath, image);
        if (result) {
            System.out.println("Image saved at " + outputPath);
        } else {
            System.err.println("Error: Unable to save image at " + outputPath);
        }
    }

    public static void main(String[] args) throws Exception {
        // Example usage
        String inputPath = "C:\\Users\\Dell\\Desktop\\signature1.jpeg";
        String outputPath = "C:\\Users\\Dell\\Desktop\\photos\\image1.jpeg";
        int targetWidth = 400;
        int targetHeight = 200;

        Mat processedImage = preprocessImage(inputPath, targetWidth, targetHeight);
        if (processedImage != null) {
            saveImage(processedImage, outputPath);
        }
        CNNModel model = new CNNModel();
        Mat img= Imgcodecs.imread("C:\\Users\\Dell\\Desktop\\photos\\image1.jpeg");
        if (img.empty()) {
            System.out.println("Image is corrupted");
        } else {
            System.out.println("Image loaded successfully");
        }
        model.trainModel(new File("C:\\Users\\Dell\\Desktop\\photos"));
    }
}
