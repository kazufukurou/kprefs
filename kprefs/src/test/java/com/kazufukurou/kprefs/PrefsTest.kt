/*
 * Copyright 2019 Artyom Mironov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.artyommironov.kprefs

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class PrefsTest {
  val ctx = ApplicationProvider.getApplicationContext<Application>()
  val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx)

  @Test
  fun booleanPref() {
    var b by prefs.boolean()
    assertFalse(b)
    b = true
    assertTrue(b)
    assertTrue(prefs.getBoolean("b", false))
  }

  @Test
  fun booleanPrefArgs1() {
    var b by prefs.boolean(false, "bb")
    assertFalse(b)
    b = true
    assertTrue(b)
    assertTrue(prefs.getBoolean("bb", false))
  }

  @Test
  fun booleanPrefArgs2() {
    var b by prefs.boolean(true, "bb")
    assertTrue(b)
    b = false
    assertFalse(b)
    assertFalse(prefs.getBoolean("bb", false))
  }

  @Test
  fun booleanPrefWrongType() {
    prefs.edit().putString("bb", "wtf").commit()
    var b by prefs.boolean(true, "bb")
    assertTrue(b)
    b = false
    assertFalse(b)
    assertFalse(prefs.getBoolean("bb", false))
    b = true
    assertTrue(b)
    assertTrue(prefs.getBoolean("bb", false))
  }

  @Test
  fun intPref() {
    var i by prefs.int()
    assertEquals(0, i)
    i = 12
    assertEquals(12, i)
    assertEquals(12, prefs.getInt("i", 0))
  }

  @Test
  fun intPrefArgs() {
    var i by prefs.int(42, "ii")
    assertEquals(42, i)
    i = 12
    assertEquals(12, i)
    assertEquals(12, prefs.getInt("ii", 0))
  }

  @Test
  fun intPrefWrongType() {
    prefs.edit().putString("ii", "wtf").commit()
    intPrefArgs()
  }

  @Test
  fun longPref() {
    var l by prefs.long()
    assertEquals(0L, l)
    l = 12L
    assertEquals(12L, l)
    assertEquals(12L, prefs.getLong("l", 0L))
  }

  @Test
  fun longPrefArgs() {
    var l by prefs.long(42L, "ll")
    assertEquals(42L, l)
    l = 12L
    assertEquals(12L, l)
    assertEquals(12L, prefs.getLong("ll", 0))
  }

  @Test
  fun longPrefWrongType() {
    prefs.edit().putString("ll", "wtf").commit()
    longPrefArgs()
  }

  @Test
  fun floatPref() {
    var f by prefs.float()
    assertEquals(0f, f)
    f = 12.3f
    assertEquals(12.3f, f)
    assertEquals(12.3f, prefs.getFloat("f", 0f))
  }

  @Test
  fun floatPrefArgs() {
    var f by prefs.float(45.6f, "ff")
    assertEquals(45.6f, f)
    f = 12.3f
    assertEquals(12.3f, f)
    assertEquals(12.3f, prefs.getFloat("ff", 0f))
  }

  @Test
  fun floatPrefWrongType() {
    prefs.edit().putString("ff", "wtf").commit()
    floatPrefArgs()
  }

  @Test
  fun stringPref() {
    var s by prefs.string()
    assertEquals("", s)
    s = "a"
    assertEquals("a", s)
    assertEquals("a", prefs.getString("s", ""))
  }

  @Test
  fun stringPrefArgs() {
    var s by prefs.string("b", "ss")
    assertEquals("b", s)
    s = "a"
    assertEquals("a", s)
    assertEquals("a", prefs.getString("ss", ""))
  }

  @Test
  fun stringPrefWrongType() {
    prefs.edit().putInt("ss", 42).commit()
    stringPrefArgs()
  }

  @Test
  fun stringSetPref() {
    var ss by prefs.stringSet()
    assertEquals(emptySet(), ss)
    ss = setOf("a", "b")
    assertEquals(setOf("a", "b"), ss)
    assertEquals(setOf("a", "b"), prefs.getStringSet("ss", emptySet()))
  }

  @Test
  fun stringSetPrefArgs() {
    var ss by prefs.stringSet(setOf("c", "d"), "sss")
    assertEquals(setOf("c", "d"), ss)
    ss = setOf("a", "b")
    assertEquals(setOf("a", "b"), ss)
    assertEquals(setOf("a", "b"), prefs.getStringSet("sss", emptySet()))
  }

  @Test
  fun stringSetPrefWrongType() {
    prefs.edit().putInt("sss", 42).commit()
    stringSetPrefArgs()
  }

  @Test
  fun enumPref() {
    var e by prefs.enum(Race.Human)
    assertEquals(Race.Human, e)
    e = Race.Vampire
    assertEquals(Race.Vampire, e)
    assertEquals(Race.Vampire.name, prefs.getString("e", ""))
  }

  @Test
  fun enumPrefArgs() {
    var e by prefs.enum(Race.Dog, "ee")
    assertEquals(Race.Dog, e)
    e = Race.Vampire
    assertEquals(Race.Vampire, e)
    assertEquals(Race.Vampire.name, prefs.getString("ee", ""))
  }

  @Test
  fun enumPrefsWrongType1() {
    prefs.edit().putInt("ee", 42).commit()
    enumPrefArgs()
  }

  @Test
  fun enumPrefsWrongType2() {
    prefs.edit().putString("ee", "wtf").commit()
    enumPrefArgs()
  }

  enum class Race { Human, Dog, Vampire }
}
