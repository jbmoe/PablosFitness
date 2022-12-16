package com.hyperborge.pablosfitness.domain.extensions

fun <T> List<T>.joinToStringOrNull(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: ((T) -> CharSequence)? = null
): String? {
    if (this.isEmpty()) {
        return null
    }
    return this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}