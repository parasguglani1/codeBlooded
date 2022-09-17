package com.example.frameapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf( android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        findViewById<Button>(R.id.selectImage).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK ){
            val uri= data!!.data

            val filePath= arrayOf(MediaStore.Images.Media.DATA)
            val c=contentResolver.query(uri!!,null,null,null,null)
            c!!.moveToNext()
            val columnIndex= c.getColumnIndex(filePath[0])
            val picturePath= c.getString(columnIndex)
            c.close()
            val inten =Intent(this,EditorActivity::class.java)
            inten.putExtra("path",picturePath)
            startActivity(inten)
        }
    }
    private fun checkPermission(): Boolean
    {
        var result: Int
        val listPermissionsNeeded : MutableList<String> = ArrayList()
for (p in permissions)
        {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(p)
            }
        }
        if (listPermissionsNeeded.isNotEmpty())
        {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 0)
            return false
        }
        return true

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0)
        {
            if ( grantResults[0] == PackageManager.PERMISSION_DENIED )
            {
                Toast.makeText(this, "please allow all permissions", Toast.LENGTH_SHORT).show()
                //permission denied
            }
            checkPermission()
        }
    }
}