package moe.chyyran.sidonia.drawables

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

import com.ustwo.clockwise.common.WatchFaceTime
import com.ustwo.clockwise.common.util.Logr
import com.ustwo.clockwise.wearable.WatchFace

import moe.chyyran.sidonia.R

class TimeDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    private var STATUS_WAIT_SEC: Int = 0

    private var mTextPaint: Paint
    private var mArabicTextPaint: Paint

    private var mCenterBlockWidth: Float = 0.toFloat()
    private var mCenterBoldLines: FloatArray

    init {
        val resources = watch.resources
        STATUS_WAIT_SEC = resources.getInteger(R.integer.animation_delay)

        var textSize = desiredMinimumWidth / 5f

        mCenterBlockWidth = (desiredMinimumWidth - (edgeOffset * 2f + hudCellWidth * 2f)) / 2f

        // Digit Display
        mTextPaint = Paint()
        mTextPaint.typeface = this.kanjiFont
        mTextPaint.textSize = textSize
        mTextPaint.color = alertColor
        mTextPaint.isAntiAlias = true

        mArabicTextPaint = Paint(mTextPaint)
        mArabicTextPaint.typeface = this.latinFont

        mCenterBoldLines = floatArrayOf(
                // Vertical Lines
                hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset,
                // Horizontal Lines
                hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset)
    }

    fun drawGrid(canvas: Canvas?) {
        alertBoldPaint.color = alertColor
        canvas?.drawLine(hudCellWidth * 1f + edgeOffset, desiredMinimumWidth / 2f, hudCellWidth * 4f + edgeOffset, desiredMinimumWidth / 2f, alertBoldPaint)
        canvas?.drawLine(desiredMinimumWidth / 2f, hudCellWidth * 1f + edgeOffset, desiredMinimumWidth / 2f, hudCellWidth * 4f + edgeOffset, alertBoldPaint)
        canvas?.drawLines(mCenterBoldLines, alertBoldPaint)
    }

    fun drawCharacter(canvas: Canvas?, row: Int, column: Int, character: String, paint: Paint = mTextPaint) {
        canvas?.save()
        val textPoint = getDrawPoint(column, row, character, paint)
        canvas?.drawText(character, textPoint.x, textPoint.y, paint)
        canvas?.restore()
    }
    fun drawTime(canvas: Canvas?, time: WatchFaceTime, formatNumeral: (Int) -> String, isArabic: Boolean = false, is24hour: Boolean = true) {
        canvas?.save()
        val timeHour = if (is24hour) time.hour else time.hour12
        val timePaint = if (isArabic) mArabicTextPaint else mTextPaint

        val topLeftDigit = formatNumeral(timeHour / 10)
        val topRightDigit = formatNumeral(timeHour % 10)
        val bottomLeftDigit = formatNumeral(time.minute / 10)
        val bottomRightDigit = formatNumeral(time.minute % 10)

        drawCharacter(canvas, 0, 0, topLeftDigit, timePaint)
        drawCharacter(canvas, 0, 1, topRightDigit, timePaint)
        drawCharacter(canvas, 1, 0, bottomLeftDigit, timePaint)
        drawCharacter(canvas, 1, 1, bottomRightDigit,timePaint)

        canvas?.restore()
    }

    fun drawSortieOrder(canvas: Canvas?) {
        canvas?.save()
        drawCharacter(canvas, 0, 0, "出", mTextPaint)
        drawCharacter(canvas, 0, 1, "撃", mTextPaint)
        drawCharacter(canvas, 1, 0, "命", mTextPaint)
        drawCharacter(canvas, 1, 1, "令",mTextPaint)
        canvas?.restore()
    }
    private fun getDrawPoint(column: Int, row: Int, numeral: String, paint: Paint): PointF {
        val fontMetrics = paint.fontMetrics
        var halfTextWidth = paint.measureText(numeral) / 2f
        var halfTextHeight = (fontMetrics.ascent + fontMetrics.descent) / 2f
        var top = edgeOffset + hudCellWidth + mCenterBlockWidth / 2f
        return PointF(top - halfTextWidth + column * mCenterBlockWidth, top - halfTextHeight + row * mCenterBlockWidth)
    }

}
