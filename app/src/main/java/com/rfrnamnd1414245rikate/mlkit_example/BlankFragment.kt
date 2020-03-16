package com.rfrnamnd1414245rikate.mlkit_example


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.rfrnamnd1414245rikate.mlkit_example.databinding.FragmentBlankBinding
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 *
 */
class BlankFragment : Fragment() {
    private lateinit var binding: FragmentBlankBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false)
        binding.openButton.setOnClickListener {
            openLibrary()
        }
        return binding.root
    }

    val RESULT_PICK_IMAGEFILE = 2
    fun openLibrary() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                val image: FirebaseVisionImage
                try {
                    image = FirebaseVisionImage.fromFilePath(context!!, uri)
                    detectML(image)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun detectML(image: FirebaseVisionImage) {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()
        val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)
        detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                // Task completed successfully
                // ...
                Log.d("barcodes", barcodes.size.toString())
                for (barcode in barcodes) {
                }
            }
            .addOnFailureListener {
                // Task failed with an exception
                // ...
            }
    }
}
