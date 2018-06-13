package io.jffiorillo.venezuelanews.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtensionsKtTest {

  @Test
  fun isNull() {
    val r = null
    assertTrue(r.isNull())
  }

  @Test
  fun isNotNull() {
    val r = Any()
    assertFalse(r.isNull())
  }
}