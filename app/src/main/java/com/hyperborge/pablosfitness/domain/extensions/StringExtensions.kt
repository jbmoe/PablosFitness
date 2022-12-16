package com.hyperborge.pablosfitness.domain.extensions

import java.net.URLEncoder

object StringExtensions {
    fun String.utf8Encode(): String {
        return URLEncoder.encode(this, "utf-8")
    }
}