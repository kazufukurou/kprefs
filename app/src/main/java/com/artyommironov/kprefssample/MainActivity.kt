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

package com.artyommironov.kprefssample

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceManager
import com.artyommironov.kprefssample.databinding.MainBinding
import kotlin.properties.Delegates

class MainActivity : Activity() {
  private val binding by lazy { MainBinding.inflate(layoutInflater) }
  private val prefs by lazy { Prefs(PreferenceManager.getDefaultSharedPreferences(this)) }
  private var notes by Delegates.observable(emptySet<String>()) { _, _, _ -> render() }
  private var kaomoji by Delegates.observable(Prefs.Kaomoji.values().first()) { _, _, _ -> render() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    init()
  }

  private fun init() = with(binding) {
    edit.setText(prefs.text)
    check.isChecked = prefs.isChecked
    check.setOnClickListener { prefs.isChecked = check.isChecked }
    seek.progress = prefs.progress
    buttonKaomoji.setOnClickListener {
      kaomoji = Prefs.Kaomoji.values().run { this[(kaomoji.ordinal + 1).takeIf { it < size } ?: 0] }
    }
    buttonClear.setOnClickListener { notes = emptySet() }
    buttonAdd.setOnClickListener { notes = notes + edit.text.toString() }
    notes = prefs.notes
    kaomoji = prefs.kaomoji
  }

  override fun onPause() = with(binding) {
    super.onPause()
    prefs.text = edit.text.toString()
    prefs.isChecked = check.isChecked
    prefs.progress = seek.progress
    prefs.notes = notes
    prefs.kaomoji = kaomoji
  }

  private fun render(): Unit = with(binding) {
    buttonKaomoji.text = kaomoji.text
    textNotes.text = notes.toSet().joinToString("\n")
  }
}
