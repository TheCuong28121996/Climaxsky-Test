package com.climaxsky.test.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import java.io.*


class InternalStorageProvider(var context: Context) {

    fun saveBitmap(bitmap: Bitmap, fileName: String) {
        val externalStorageStats = Environment.getExternalStorageState()
        if (externalStorageStats.equals(Environment.MEDIA_MOUNTED)) {
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            val file = File(storageDirectory, fileName)
            try {
                val stream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Log.d("SaveBitmap","Unable to access the storage")
        }
    }

    fun loadBitmap(picName: String): Bitmap? {
        var bitmap: Bitmap? = null
//        var fileInputStream: FileInputStream? = null
        val imgFile = File("/storage/emulated/0/"+picName)
        if(imgFile.exists()){
             bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        }

//        try {
//            fileInputStream = context.openFileInput("/storage/emulated/0/"+picName)
//            bitmap = BitmapFactory.decodeStream(fileInputStream)
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            fileInputStream?.close()
//        }

        return bitmap
    }
}