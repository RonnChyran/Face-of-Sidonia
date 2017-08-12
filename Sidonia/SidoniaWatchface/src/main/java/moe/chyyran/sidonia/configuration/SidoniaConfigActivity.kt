package moe.chyyran.sidonia.configuration

import android.os.Bundle
import android.preference.PreferenceActivity

import moe.chyyran.sidonia.R
import android.preference.PreferenceFragment



class SidoniaConfigActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, SidoniaPreferenceFragments()).commit()
    }

    class SidoniaPreferenceFragments : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }
    }
}

