package com.example.demo;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Preprocessing {

    static {
        // Load the OpenCV library
        System.load("C:\\Users\\Dell\\Desktop\\opencv\\build\\java\\x64\\opencv_java4100.dll");
    }

    public static Mat preprocessImage(String inputPath, int targetWidth, int targetHeight) {
        // Load the image
        Mat image = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_COLOR);
        if (image.empty()) {
            System.err.println("Error: Cannot load image at " + inputPath);
            return null;
        }

        // Convert to grayscale
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Resize the image
        Mat resizedImage = new Mat();
        Size size = new Size(targetWidth, targetHeight);
        Imgproc.resize(grayImage, resizedImage, size);

        // Normalize pixel values to range [0, 1]
        Mat normalizedImage = new Mat();
        resizedImage.convertTo(normalizedImage, CvType.CV_32F, 1.0 / 255.0);

        return normalizedImage;
    }

    public static void saveImage(Mat image, String outputPath) {
        boolean result = Imgcodecs.imwrite(outputPath, image);
        if (result) {
            System.out.println("Image saved at " + outputPath);
        } else {
            System.err.println("Error: Unable to save image at " + outputPath);
        }
    }

    public static void main(String[] args) {
        // Example usage
        String inputPath = "C:\\Users\\Dell\\Desktop\\signature.png";
        String outputPath = "C:\\Users\\Dell\\Desktop\\image.png";
        int targetWidth = 128;
        int targetHeight = 128;

        Mat processedImage = preprocessImage(inputPath, targetWidth, targetHeight);
        if (processedImage != null) {
            saveImage(processedImage, outputPath);
        }
    }
}
