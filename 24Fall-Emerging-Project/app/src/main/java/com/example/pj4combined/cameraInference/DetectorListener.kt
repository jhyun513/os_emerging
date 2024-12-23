package com.example.pj4combined.cameraInference

import android.util.Log
import org.tensorflow.lite.task.vision.detector.Detection

class DetectorListener(
    private val onDetectionResult: (DetectionResult) -> Unit,
    private val onDetectionError: (String) -> Unit
) : PersonClassifier.DetectorListener {

    // Update UI after objects have been detected. Extracts original image height/width
    // to scale and place bounding boxes properly through OverlayView
    override fun onObjectDetectionResults(
        results: MutableList<Detection>?,
        inferenceTime: Long,
        imageHeight: Int,
        imageWidth: Int
    ) {
        if (results != null) {
            // Ensure that results are non-null before passing to composable
            onDetectionResult(DetectionResult(results, inferenceTime, imageHeight, imageWidth))
        } else {
            onDetectionError("No detections found")
        }
    }

    override fun onObjectDetectionError(error: String) {
        Log.d("overlay", error)
        onDetectionError(error)
    }
}

data class DetectionResult(
    val detections: MutableList<Detection>,
    val inferenceTime: Long,
    val imageHeight: Int,
    val imageWidth: Int
)