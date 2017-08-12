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

    fun drawTime(canvas: Canvas?, time: WatchFaceTime, formatNumeral: (Int) -> String, isArabic: Boolean = false, is24hour: Boolean = true) {
        canvas?.save()
        val timeHour = if (is24hour) time.hour else time.hour12
        val topLeftDigit = formatNumeral(timeHour / 10)
        val mTLTextPoint = getDrawPoint(0, 0, topLeftDigit, mTextPaint)

        val topRightDigit = formatNumeral(timeHour % 10)
        val mTRTextPoint = getDrawPoint(1, 0, topRightDigit, mTextPaint)

        val bottomLeftDigit = formatNumeral(time.minute / 10)
        val mBLTextPoint = getDrawPoint(0, 1, bottomLeftDigit, mTextPaint)

        val bottomRightDigit = formatNumeral(time.minute % 10)
        val mBRTextPoint = getDrawPoint(1, 1, bottomRightDigit, mTextPaint)

        val timePaint = if (isArabic) mArabicTextPaint else mTextPaint
        alertBoldPaint.color = alertColor
        canvas?.drawLine(hudCellWidth * 1f + edgeOffset, desiredMinimumWidth / 2f, hudCellWidth * 4f + edgeOffset, desiredMinimumWidth / 2f, alertBoldPaint)
        canvas?.drawLine(desiredMinimumWidth / 2f, hudCellWidth * 1f + edgeOffset, desiredMinimumWidth / 2f, hudCellWidth * 4f + edgeOffset, alertBoldPaint)
        canvas?.drawLines(mCenterBoldLines, alertBoldPaint)
        canvas?.drawText(topLeftDigit, mTLTextPoint.x, mTLTextPoint.y, timePaint)
        canvas?.drawText(topRightDigit, mTRTextPoint.x, mTRTextPoint.y, timePaint)
        canvas?.drawText(bottomLeftDigit, mBLTextPoint.x, mBLTextPoint.y, timePaint)
        canvas?.drawText(bottomRightDigit, mBRTextPoint.x, mBRTextPoint.y, timePaint)
        canvas?.restore()
    }

    fun getDrawPoint(column: Int, row: Int, numeral: String, paint: Paint): PointF {
        val fontMetrics = paint.fontMetrics
        var halfTextWidth = mTextPaint.measureText(numeral) / 2f
        var halfTextHeight = (fontMetrics.ascent + fontMetrics.descent) / 2f
        var top = edgeOffset + hudCellWidth + mCenterBlockWidth / 2f
        return PointF(top - halfTextWidth + column * mCenterBlockWidth + if (numeral.equals("1")) 8f else 0f, top - halfTextHeight + row * mCenterBlockWidth)
    }

}
