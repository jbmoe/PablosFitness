package com.hyperborge.pablosfitness

import com.hyperborge.pablosfitness.domain.extensions.orZero
import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NumberExtensionTests {
    @Test
    fun `null int orZero returns 0`() {
        val value: Int? = null
        val expected = 0
        val actual = value.orZero()

        assertEquals(expected, actual)
    }

    @Test
    fun `non null int orZero returns int`() {
        val value: Int = Random.nextInt()
        val expected = value
        val actual = value.orZero()

        assertEquals(expected, actual)
    }
}