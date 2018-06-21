package com.hansck.shadowingu.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import com.hansck.shadowingu.R
import com.squareup.picasso.Picasso


class ImageUtil private constructor() {

    companion object {
        val instance = ImageUtil()
    }

    fun setImageResource(context: Context, url: String, view: ImageView, defaultImg: Int, isCaptured: Boolean) {
        if (isCaptured) {
            Picasso.with(context).load(url).placeholder(defaultImg).error(defaultImg).fit().centerCrop().into(view)
        } else {
            Picasso.with(context).load(url).placeholder(defaultImg).error(defaultImg).into(view)
        }
    }

    fun setImage(context: Context, url: String, view: ImageView) {
        setImageResource(context, url, view, R.drawable.ic_default_image, false)
    }

    fun setImageByName(context: Context, imageName: String, view: ImageView) {
        view.setImageResource(context.resources.getIdentifier(imageName, "drawable", context.packageName))
    }

    fun deleteCacheImage(context: Context, url: String) {
        Picasso.with(context).invalidate(url)
    }

    fun getResizedBitmap(context: Context, uri: Uri?, maxSize: Int): Bitmap? {
        val parcel: ParcelFileDescriptor?
        try {
            parcel = context.contentResolver.openFileDescriptor(uri, "r")
            val descriptor = parcel?.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(descriptor)
            parcel.close()
            val ratio = Math.min(maxSize.toFloat() / image.width, maxSize.toFloat() / image.height)
            if (ratio < 1) {
                val width = Math.round(ratio * image.width)
                val height = Math.round(ratio * image.height)
                return Bitmap.createScaledBitmap(image, width, height, true)
            } else {
                return image
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
