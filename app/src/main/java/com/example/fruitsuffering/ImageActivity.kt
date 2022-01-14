package com.example.fruitsuffering

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.graphics.Bitmap.createScaledBitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageProxy
import androidx.core.content.FileProvider
import com.example.fruitsuffering.ml.MobileNet
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ImageActivity: AppCompatActivity() {

    companion object {
        const val FILE_NAME = "photo.jpg"
        const val TAG = "ImageActivity"
        const val RETURN_IMAGE = 200
        const val REQUEST_CAMERA = 100
        const val SEND_IMAGE_TO_RESULT = "Send image"
    }

    private lateinit var photoFile: File
    private lateinit var btnCamera: Button
    private lateinit var btnUpload: Button
    private lateinit var btnConfirm: Button
    private lateinit var imageView: ImageView
    private lateinit var imageUri: Uri
    private lateinit var result: TextView
    private lateinit var labels: List<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        btnCamera = findViewById(R.id.btnCamera)
        btnUpload = findViewById(R.id.btnUpload)
        btnConfirm = findViewById(R.id.btnConfirm)
        imageView = findViewById(R.id.imageView)
        result = findViewById(R.id.result)
        labels = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        //Hide that ugly title bar
        this.supportActionBar?.hide();

        btnCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(this, "com.example.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CAMERA)
            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpload.setOnClickListener {
            val uploadPictureIntent = Intent(Intent.ACTION_GET_CONTENT)
            uploadPictureIntent.type = "image/*"
            startActivityForResult(Intent.createChooser(uploadPictureIntent, "Choose your image"), RETURN_IMAGE)
        }

//        btnConfirm.setOnClickListener{
//        }
    }

    private fun getPhotoFile(fileName: String): File {
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            imageView.setImageBitmap(takenImage)
            runClassifier(takenImage)
        }
        else if (requestCode == RETURN_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            //Get bitmap version of the image
            imageUri = data.data!!
            val uploadImage = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            runClassifier(uploadImage)
            imageView.setImageBitmap(uploadImage)
        }

    }

    private fun runClassifier(image: Bitmap) {
        val model = MobileNet.newInstance(this)

        // Creates inputs for reference.

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)

        //Convert bitmap to that (1,128,128,3) size
        val byteBuffer = convertBitmapToByteBuffer(image)

//        val byteBuffer = tensorImage.buffer
        if (byteBuffer != null) {
            inputFeature0.loadBuffer(byteBuffer)
        }

        Log.d(TAG, byteBuffer.toString())
        Log.d(TAG, inputFeature0.buffer.toString())

        // Runs model inference and gets result.
        val output = model.process(inputFeature0)
        val outputFeature0 = output.outputFeature0AsTensorBuffer

        val max = getMaxScore(outputFeature0.floatArray)

        Log.d(TAG, labels.toString())
        Log.d(TAG, outputFeature0.floatArray.joinToString{it.toString()})

        result.text = "I think it is: ${labels[max]} : ${outputFeature0.floatArray[max]}"

        // Releases model resources if no longer used.
        model.close()

    }

    private fun convertBitmapToByteBuffer(bp: Bitmap): ByteBuffer? {
        val imgData: ByteBuffer = ByteBuffer.allocateDirect(java.lang.Float.BYTES * 128 * 128 * 3)
        imgData.order(ByteOrder.nativeOrder())
        val bitmap = createScaledBitmap(bp, 128, 128, true)
        val intValues = IntArray(128 * 128)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        // Convert the image to floating point.
        var pixel = 0
        for (i in 0..127) {
            for (j in 0..127) {
                val `val` = intValues[pixel++]
                imgData.putFloat((`val` shr 16 and 0xFF) / 255f)
                imgData.putFloat((`val` shr 8 and 0xFF) / 255f)
                imgData.putFloat((`val` and 0xFF) / 255f)
            }
        }
        return imgData
    }

    private fun getMaxScore(arr: FloatArray): Int{
        var ind = 0
        var min = 0.0f
        for(i in 0..1000){
            if(i < arr.size){
                if(arr[i] > min){
                    ind = i
                    min = arr[i]
                }
            }
        }
        return ind
    }

}