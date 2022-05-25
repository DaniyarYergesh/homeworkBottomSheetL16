package com.example.homework_recyclerview

import android.text.Editable
import androidx.annotation.DrawableRes
import model.Parent

data class Currency(
    val text: Int,
    val type: String,
    @DrawableRes val flag: Int
):Parent