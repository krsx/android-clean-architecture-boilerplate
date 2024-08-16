package com.project.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Auth(
    val user: User,
    val token: String
): Parcelable
