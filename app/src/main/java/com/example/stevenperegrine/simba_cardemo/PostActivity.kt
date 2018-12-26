package com.example.stevenperegrine.simba_cardemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.activity_post.*




class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)





    }
    //Take Car Image
    fun TakeCarImage(view:View){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, 1)
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            postImage.setImageBitmap(imageBitmap)
           // postImage.rotation = 0F
        }
        if (requestCode === 2 && resultCode === RESULT_OK) {
            val imageUri = data?.getData()
            postImage.setImageURI(imageUri)
          //  postImage.rotation = 90F

        }
    }
    //Load Car Image
    fun pickImage(view:View) {

        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*"
        startActivityForResult(gallery, 2)

    }

  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {

                //Get ImageURi and load with help of picasso
                //Uri selectedImageURI = data.getData();

                Picasso.with(this@PostActivity).load(data!!.data).noPlaceholder().centerCrop().fit()
                    .into(findViewById(R.id.postImage) as ImageView)
            }

        }
    }*/

}