package com.example.demo;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;

import java.io.File;

public class ImageProcessing {
    //static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
static {
 //Load the OpenCV library
 System.load("C:\\Users\\Dell\\Desktop\\opencv\\build\\java\\x64\\opencv_java4100.dll");
  }
//}

    public static Mat processImage(String imagePath, int width, int height) {
        // Load the image
        Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);

        // Resize the image
        Imgproc.resize(image, image, new Size(width, height));

        // Invert the image
        Core.subtract(image, new Scalar(255),image);
        // Core.subtract(new Scalar(255), image, image);

        // Return processed image
        return image;
    }

    public static void main(String[] args) {
        File fp = new File("C:\\Users\\Dell\\Desktop\\signature.jpg");
        Mat image  = processImage(fp.getPath(), 400,200);
        saveImage(image, "C:\\Users\\Dell\\Desktop\\photos\\output.jpeg");

    }


    public static void saveImage(Mat image, String outputPath) {
        boolean result = Imgcodecs.imwrite(outputPath, image);
        if (result) { System.out.println("Image saved at " + outputPath);
        } else { System.err.println("Error: Unable to save image at " + outputPath); }}
}
