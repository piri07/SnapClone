package com.example.snapclone

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest

class CreateSnapActivity : AppCompatActivity() {

    var CreateSnapImageView : ImageView? = null
    var MessageEditText : EditText?=null
    val imageName = UUID.randomUUID().toString() + ".jpg"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)
        CreateSnapImageView = findViewById(R.id.CreateSnapimageView)
        MessageEditText = findViewById(R.id.MessageEditText)
    }

    fun getPhoto(){
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,1)
    }

    fun chooseImageClicked(view: View){
        getPhoto()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data !!.data
        if(requestCode==1 && resultCode == Activity.RESULT_OK && data!=null){
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                CreateSnapImageView?.setImageBitmap(bitmap)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==1){
            if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getPhoto()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun nextClicked(view: View){
        // Get the data from an ImageView as bytes
        CreateSnapImageView?.isDrawingCacheEnabled = true
        CreateSnapImageView?.buildDrawingCache()
        val bitmap = (CreateSnapImageView?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()



        var uploadTask = FirebaseStorage.getInstance().getReference().child("Images").child(imageName).putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(this,"Uplaod failed",Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            //
            val intent = Intent(this,chooseUserActivity::class.java)
            startActivity(intent)
        }


    }


}
