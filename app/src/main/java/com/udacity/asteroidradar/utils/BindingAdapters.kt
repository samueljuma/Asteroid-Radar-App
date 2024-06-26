package com.udacity.asteroidradar.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)

        //Set Image content description
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_text_holder)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)

        //Set Image content description
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_text_holder)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)

        //set contentDescription for image
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)

        //set contentDescription for image
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("isSpinnerVisible")
fun spinnerVisibility(view: View, it: Any?){
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

//Binds Image of the day to the Image View
@BindingAdapter("loadPictureOfDay")
fun loadPictureOfDay(imageView: ImageView, pic: PictureOfDay?){
 pic?.let {
     if(pic.mediaType == "image"){
         Picasso.with(imageView.context)
             .load(it.url)
             .into(imageView)
     }else{
         Toast.makeText(
             imageView.context, "Couldn\'t Load Image of the day because it is not Image",
             Toast.LENGTH_SHORT).show()
         imageView.setImageResource(R.drawable.placeholder_picture_of_day)
     }

     //Both Libraries work perfect
//        Glide.with(imageView.context)
//            .load(it)
//            .into(imageView)
 } ?: run {
        imageView.setImageResource(R.drawable.placeholder_picture_of_day)
 }
}

