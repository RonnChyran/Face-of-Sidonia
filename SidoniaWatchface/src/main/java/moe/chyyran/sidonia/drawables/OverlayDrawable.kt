package moe.chyyran.sidonia.drawables
import android.graphics.*
import com.ustwo.clockwise.common.WatchFaceTime
import com.ustwo.clockwise.wearable.WatchFace


class OverlayDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    private val mTextPaint: Paint = Paint(this.hudPaint)
    private val mInvertedPaint: Paint

    private val overlayCharacterWidthDiff : Float
    private val overlayCharacterHeightDiff : Float

    init {
        mTextPaint.color = this.hudColor
        mTextPaint.textSize = desiredMinimumWidth / 5.5f

        mInvertedPaint = Paint(mTextPaint)
        mInvertedPaint.color = this.backgroundColor

        val fontMetrics: Paint.FontMetrics = mTextPaint.fontMetrics
        overlayCharacterWidthDiff = (hudCellWidth - mTextPaint.measureText("圧")) / 2f
        overlayCharacterHeightDiff = (hudCellWidth - (fontMetrics.ascent + fontMetrics.descent)) / 2f

    }

    fun drawOverlay(canvas: Canvas?, time: WatchFaceTime?, drawKokoro: Boolean = false,
                    drawWeekday: Boolean = true, isReserve: Boolean = false, isSortie: Boolean = false) {
        canvas?.save()
        if (drawKokoro) {
           drawCharacter(canvas, "心", 1, 0)
        }
        if (drawWeekday) {
            drawCharacter(canvas, getWeekDayKanji(time?.weekDay), 0, 1)
        }
        drawCharacter(canvas, "圧", 2, 0)
        drawCharacter(canvas, "温", 3, 0)
        drawCharacter(canvas, "能", 4, 1)
        drawCharacter(canvas, "析", 4, 2)
        drawCharacter(canvas, "設", 4, 3)
        drawCharacter(canvas, getTimeChar(time,"通"), 1, 4, isReserve)
        drawCharacter(canvas, "令", 2, 4, isSortie)
        drawCharacter(canvas, "位", 3, 4, isReserve)
        canvas?.restore()
    }

    private fun getTimeChar(time: WatchFaceTime?, default: String): String {
        if (time == null) return default

        when {
            time.hour >= 18 || time.hour <= 5 -> return "夜"
            time.hour >= 12 || time.hour <= 17 -> return "昼"
            time.hour >= 6 || time.hour <= 11 -> return "朝"
            else -> return default
        }
    }

    private fun getWeekDayKanji(num: Int?): String {
        var c: String = ""

        when (num) {
            0 -> c = "曰"
            1 -> c = "月"
            2 -> c = "火"
            3 -> c = "水"
            4 -> c = "木"
            5 -> c = "金"
            6 -> c = "土"
        }
        return c
    }

    fun drawCharacter(canvas: Canvas?, character: String, row: Int, column: Int, inverted: Boolean = false) {
        canvas?.save()
        mTextPaint.typeface = this.kanjiFont
        mInvertedPaint.typeface = this.kanjiFont

        val cellOffset = this.getCellOffset(row, column)
        val characterOffset = PointF(cellOffset.x + overlayCharacterWidthDiff,
                cellOffset.y + overlayCharacterHeightDiff)
        if(!inverted) {
            canvas?.drawText(character, characterOffset.x, characterOffset.y, mTextPaint)
        } else {
            canvas?.drawRect(cellOffset.x, cellOffset.y,
                    cellOffset.x + hudCellWidth,
                    cellOffset.y + hudCellWidth, this.hudPaint)
            canvas?.drawText(character, characterOffset.x, characterOffset.y, mInvertedPaint)

        }
        canvas?.restore()
    }
    //

}
