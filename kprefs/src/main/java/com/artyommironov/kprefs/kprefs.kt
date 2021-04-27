/*
 * Copyright 2021 Artyom Mironov
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

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private fun <T> SharedPreferences.delegate(
  default: T,
  key: String?,
  commit: Boolean,
  getter: SharedPreferences.(String, T) -> T,
  setter: Editor.(String, T) -> Editor
) = object : ReadWriteProperty<Any?, T> {

  override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
    return runCatching { getter(key ?: property.name, default) }.getOrNull() ?: default
  }

  @SuppressLint("ApplySharedPref")
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    edit().setter(key ?: property.name, value ?: default).apply { if (commit) commit() else apply() }
  }
}

fun SharedPreferences.boolean(
  default: Boolean = false,
  key: String? = null,
  commit: Boolean = false
): ReadWriteProperty<Any?, Boolean> {
  return delegate(default, key, commit, SharedPreferences::getBoolean, Editor::putBoolean)
}

fun SharedPreferences.long(
  default: Long = 0L,
  key: String? = null,
  commit: Boolean = false
): ReadWriteProperty<Any?, Long> {
  return delegate(default, key, commit, SharedPreferences::getLong, Editor::putLong)
}

fun SharedPreferences.int(
  default: Int = 0,
  key: String? = null,
  commit: Boolean = false
): ReadWriteProperty<Any?, Int> {
  return delegate(default, key, commit, SharedPreferences::getInt, Editor::putInt)
}

fun SharedPreferences.float(
  default: Float = 0f,
  key: String? = null,
  commit: Boolean = false
): ReadWriteProperty<Any?, Float> {
  return delegate(default, key, commit, SharedPreferences::getFloat, Editor::putFloat)
}

fun SharedPreferences.string(
  default: String = "",
  key: String? = null,
  commit: Boolean = false
): ReadWriteProperty<Any?, String> {
  return delegate(
    default = default,
    key = key,
    commit = commit,
    getter = { k, d -> getString(k, d).orEmpty() },
    setter = { k, v -> putString(k, v) }
  )
}

fun SharedPreferences.stringSet(
  default: Set<String> = emptySet(),
  key: String? = null,
  commit: Boolean = false
): ReadWriteProperty<Any?, Set<String>> {
  return delegate(
    default = default,
    key = key,
    commit = commit,
    getter = { k, d -> getStringSet(k, d).orEmpty() },
    setter = { k, v -> putStringSet(k, v) }
  )
}

fun <T : Enum<*>> SharedPreferences.enum(
  default: T,
  key: String? = null,
  commit: Boolean = false
): ReadWriteProperty<Any?, T> {
  return delegate(
    default = default,
    key = key,
    commit = commit,
    getter = { k, d ->
      getString(k, d.name)?.let { s -> d::class.java.enumConstants?.find { it.name == s } } ?: d
    },
    setter = { k, v -> putString(k, v.name) }
  )
}
