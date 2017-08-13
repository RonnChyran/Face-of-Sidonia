/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package moe.chyyran.sidonia

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.ustwo.clockwise.wearable.WatchFace
import moe.chyyran.sidonia.drawables.*
import android.os.BatteryManager
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.text.format.DateUtils
import android.support.wearable.watchface.WatchFaceStyle
import android.preference.PreferenceManager
import com.ustwo.clockwise.common.WatchShape
import android.os.Vibrator
import android.support.wearable.watchface.WatchFaceService
import com.ustwo.clockwise.common.WatchMode
import java.util.*


/**
 * Digital watch face with seconds. In ambient mode, the seconds aren't displayed. On devices with
 * low-bit ambient mode, the text is drawn without anti-aliasing in ambient mode.
 */
class Sidonia : WatchFace() {
    internal lateinit var mGrid: GridDrawable
    internal lateinit var mOverlay: OverlayDrawable
    internal lateinit var mTime: TimeDrawable
    internal lateinit var mStatus: StatusDrawable
    internal lateinit var mBattery: BatteryDrawable
    internal var mBatteryPct: Int = 0
    internal var isCharging: Boolean = false
    internal lateinit var mSharedPreferences: SharedPreferences
    internal var mAlertMode: Boolean = false
    internal var mFlashCounter: Boolean = true
    private val mPilotNames = arrayOf("星", "谷", "科", "岐", "弦", "赤", "焔", "烽", "煉", "炉", "燿", "炒")
    internal var mCurrentPilot: String = mPilotNames[0]

    override fun getWatchFaceStyle(): WatchFaceStyle {
        val builder = WatchFaceStyle.Builder(this)
                .setAcceptsTapEvents(true)
                .setCardProgressMode(WatchFaceStyle.PROGRESS_MODE_NONE)
        return builder.build()
    }

    override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
        if (tapType == WatchFaceService.TAP_TYPE_TAP) {
            this.mAlertMode = !this.mAlertMode
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val vibrationPattern = longArrayOf(0, 50)
            //-1 - don't repeat
            val indexInPatternToRepeat = -1
            vibrator.vibrate(vibrationPattern, indexInPatternToRepeat)
            mCurrentPilot = getRandomPilotName()
            invalidate()
        }
        super.onTapCommand(tapType, x, y, eventTime)
    }

    override fun onCreate() {
        mGrid = GridDrawable(this)
        mOverlay = OverlayDrawable(this)
        mTime = TimeDrawable(this)
        mStatus = StatusDrawable(this)
        mBattery = BatteryDrawable(this)
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        updateBatteryStatus()
        super.onCreate()
    }

    override fun onWatchModeChanged(watchMode: WatchMode?) {
        if (!watchMode?.equals(WatchMode.INTERACTIVE)!!) {
            this.mAlertMode = false
        }
        super.onWatchModeChanged(watchMode)
    }

    override fun onDraw(canvas: Canvas?) {
        this.updateBatteryStatus()
        val converter = getConverter()
        val isArabic = mSharedPreferences.getString(this.resources.getString(R.string.pref_sidonia_number_format), "daiji") == "arabic"
        val is24hour = mSharedPreferences.getBoolean(this.resources.getString(R.string.pref_am_pm), true)
        canvas?.drawColor(Color.BLACK)
        canvas?.save()
        if (watchShape.equals(WatchShape.CIRCLE) && canvas != null) {
            val centerX = canvas.width / 2f
            val centerY = canvas.height / 2f
            val ratio = 2f / Math.PI.toFloat()
            canvas.scale(ratio, ratio, centerX, centerY)
            canvas.save()
        }

        if(!this.mAlertMode) {
            mBattery.drawBatteryPercent(canvas, this.mBatteryPct, this.isCharging)
            mGrid.drawGrid(canvas)
            mStatus.drawDate(canvas, this.time)
            mTime.drawGrid(canvas)
            mTime.drawTime(canvas, this.time, converter, isArabic, is24hour)
            mOverlay.drawOverlay(canvas, this.time, isReserve = true)
            mGrid.drawCrosshairs(canvas)
        } else {
            mGrid.drawGrid(canvas)
            mStatus.drawTime(canvas, this.time)
            mTime.drawGrid(canvas)
            if (this.mFlashCounter) mTime.drawSortieOrder(canvas)
            mOverlay.drawOverlay(canvas, null, true, false, isSortie = true)
            mOverlay.drawCharacter(canvas, mCurrentPilot, 0, 1)
            mGrid.drawCrosshairs(canvas)
            this.mFlashCounter = !this.mFlashCounter

        }
        canvas?.restore()
    }

    private fun getRandomPilotName(): String {
        val randomInt = Random().nextInt(mPilotNames.count())
        return mPilotNames[randomInt]
    }
    private fun getConverter(): (Int) -> String {
        val pref = mSharedPreferences.getString(this.resources.getString(R.string.pref_sidonia_number_format), "daiji")
        when (pref) {
            "daiji" -> return ::convertDaiji
            "arabic" -> return ::convertArabic
            "daijifull" -> return ::convertDaijiFull
            "kyuujitai" -> return ::convertKyuujitai
            "suuji" -> return ::convertSuuji
            else -> return ::convertDaiji
        }
    }

    private fun updateBatteryStatus() {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = applicationContext.registerReceiver(null, ifilter)
        val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: 0
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: 0
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: 0

        mBatteryPct = level * 100 / scale
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
    }

    override fun getInteractiveModeUpdateRate(): Long {
        return DateUtils.SECOND_IN_MILLIS;
    }
}
