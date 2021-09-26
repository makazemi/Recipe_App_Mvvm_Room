package com.makazemi.cooking.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawMaterial(
    val nameMaterial: String,
    val value: String) : Parcelable
