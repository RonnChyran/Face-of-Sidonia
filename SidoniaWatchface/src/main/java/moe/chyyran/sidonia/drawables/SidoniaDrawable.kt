package moe.chyyran.sidonia.drawables

import android.app.WallpaperManager
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import com.ustwo.clockwise.common.util.Logr
import com.ustwo.clockwise.wearable.WatchFace
import moe.chyyran.sidonia.R

abstract class SidoniaDrawable(watch: WatchFace) {
    val desiredMinimumWidth: Int = WallpaperManager.getInstance(watch).desiredMinimumWidth
    val hudColor: Int = ContextCompat.getColor(watch.applicationContext, R.color.hud)
    val criticalColor: Int = ContextCompat.getColor(watch.applicationContext, R.color.critical)
    val backgroundColor: Int = ContextCompat.getColor(watch.applicationContext, R.color.digital_background)
    val alertColor: Int = ContextCompat.getColor(watch.applicationContext, R.color.alert)
    val latinFont: Typeface = Typeface.createFromAsset(watch.assets, watch.resources.getString(R.string.latin_font))
    val edgeOffset: Float =  this.desiredMinimumWidth / 80f
    val hudCellWidth: Float = (this.desiredMinimumWidth - this.edgeOffset * 2f) / 5f
    val hudBoldPaint: Paint
    val hudPaint: Paint
    val alertPaint: Paint
    val alertBoldPaint: Paint
    val criticalPaint: Paint
    val preferences = PreferenceManager.getDefaultSharedPreferences(watch)
    private val watch = watch
    private val makinasKanjiFont: Typeface = Typeface.createFromAsset(watch.assets, watch.resources.getString(R.string.kanji_font_makinas))
    private val kaisoKanjiFont: Typeface = Typeface.createFromAsset(watch.assets, watch.resources.getString(R.string.kanji_font_kaiso))
    private val mplusKanjiFont: Typeface = Typeface.createFromAsset(watch.assets, watch.resources.getString(R.string.kanji_font_mplus))

    val kanjiFont: Typeface get(){
        val font = preferences.getString(watch.resources.getString(R.string.pref_sidonia_font), watch.resources.getString(R.string.kanji_font_mplus))
        when (font) {
            watch.resources.getString(R.string.kanji_font_makinas) -> return makinasKanjiFont
            watch.resources.getString(R.string.kanji_font_kaiso) -> return kaisoKanjiFont
            watch.resources.getString(R.string.kanji_font_mplus) -> return mplusKanjiFont
            else -> return mplusKanjiFont
        }
    }

    init {
        this.hudBoldPaint = createTextPaint(this.hudColor)
        this.hudBoldPaint.strokeWidth = this.edgeOffset / 2f
        this.hudBoldPaint.isAntiAlias = true

        this.hudPaint = createTextPaint(this.hudColor)
        this.hudPaint.strokeWidth = this.edgeOffset / 4f
        this.hudPaint.isAntiAlias = true

        this.alertBoldPaint = createTextPaint(this.alertColor)
        this.alertBoldPaint.strokeWidth = this.edgeOffset / 2f
        this.alertBoldPaint.isAntiAlias = true

        this.alertPaint = createTextPaint(this.alertColor)
        this.alertPaint.strokeWidth = this.edgeOffset / 4f
        this.alertPaint.isAntiAlias = true

        this.criticalPaint = Paint(this.hudPaint)
        this.criticalPaint.color = this.criticalColor
    }

    fun getCellOffset(row: Int, column: Int) : PointF {
        return PointF(column * this.hudCellWidth + this.edgeOffset, row * this.hudCellWidth + this.edgeOffset)
    }
}