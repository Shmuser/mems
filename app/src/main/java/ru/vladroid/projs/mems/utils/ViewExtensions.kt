package ru.vladroid.projs.mems.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.vladroid.projs.mems.R

fun View.showSnackbar(stringResId: Int) {
    Snackbar.make(this, stringResId, Snackbar.LENGTH_LONG)
        .setBackgroundTint(ContextCompat.getColor(this.context, R.color.colorError))
        .setTextColor(ContextCompat.getColor(this.context, R.color.colorText))
        .show()
}