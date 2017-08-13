package moe.chyyran.sidonia.drawables

import android.graphics.*
import com.ustwo.clockwise.common.WatchFaceTime
import com.ustwo.clockwise.common.util.Logr
import com.ustwo.clockwise.wearable.WatchFace

private class TextOffsets(val rightTextOffset: PointF, val halfHeightTextOffset: PointF)

class StatusDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    private var mRightTextPaint: Paint = createDatePaint()
    private var mHalfHeightTextPaint: Paint = createHalfHeightDisplayPaint()

    private fun getTextOffsets(time: String): TextOffsets {

        val rightCellOffset = this.getCellOffset(0, 2) // x offset actually 2.5 ish, but we will ignore the x.
        val rightTextMetrics = mRightTextPaint.fontMetrics
        val rightTextWidth = mRightTextPaint.measureText(time) / 2f
        val rightTextHeightDiff = (hudCellWidth - (rightTextMetrics.ascent + rightTextMetrics.descent)) / 2f
        val rightLeftOffset = edgeOffset + desiredMinimumWidth / 1.60f - rightTextWidth
        // Here the x offset has been manually specified, but the top offset is the same principle
        val rightOffset = PointF(rightLeftOffset, rightCellOffset.y + rightTextHeightDiff)

        // Half height text is it's own special case.
        val halfHeightTextWidth = mHalfHeightTextPaint.measureText("T") / 2f
        val halfTextMetrics = mHalfHeightTextPaint.fontMetrics


        val halfHeightTextHeight = (halfTextMetrics.ascent + halfTextMetrics.descent) / 2f
        val halfHeightTextHeightDiff = (hudCellWidth - (halfHeightTextHeight)) / 2.5f

        val halfHeightLeftOffset = edgeOffset + desiredMinimumWidth / 2.3f - halfHeightTextWidth
        val halfHeightOffset = PointF(halfHeightLeftOffset, rightCellOffset.y + halfHeightTextHeightDiff)

        return TextOffsets(rightOffset, halfHeightOffset)
    }

    private fun createHalfHeightDisplayPaint(): Paint {
        val centerTextSize = desiredMinimumWidth / 12.0f
        val halfHeightPaint = Paint()
        halfHeightPaint.typeface = this.latinFont
        halfHeightPaint.color = this.backgroundColor
        halfHeightPaint.textSize = centerTextSize
        halfHeightPaint.isAntiAlias = true
        return halfHeightPaint
    }

    private fun createDatePaint() : Paint {
        val textSize = desiredMinimumWidth / 6.3f
        val paint = Paint()
        paint.typeface = this.latinFont
        paint.color = this.backgroundColor
        paint.textSize = textSize
        paint.isAntiAlias = true
        return paint
    }

    fun drawTime(canvas: Canvas?, time: WatchFaceTime) {
        // We use twelve hour time here because it is guaranteed not to go past 1259.
        // If for example the clock turned 0000, the grid would be unable to contain the numerics.
        val text = (if (time.hour % 12 == 0) 12 else (time.hour % 12)).toString() + ((if (time.minute > 9) "" else "0") + time.minute.toString())
        val textOffsets = getTextOffsets(text)
        canvas?.drawRect(hudCellWidth * 2f + edgeOffset,
                edgeOffset,
                hudCellWidth * 4f + edgeOffset,
                hudCellWidth * 1f + edgeOffset,
                this.hudPaint
        )

        drawStatusIndicator(canvas, "T", if (time.hour > 11) "P" else "A")

        canvas?.drawText(text, textOffsets.rightTextOffset.x, textOffsets.rightTextOffset.y, mRightTextPaint)
    }

    fun drawDate(canvas: Canvas?, time: WatchFaceTime) {
        // Months are zero-indexed, so we have to add one in order to correct for it.
        val month = time.month + 1
        val text = month.toString() + ((if (time.monthDay > 9) "" else "0") + time.monthDay.toString())
        val textOffsets = getTextOffsets(text)

        val startOffset = this.getCellOffset(0, 2)
        val endOffset = this.getCellOffset(1, 4)
        canvas?.drawRect(startOffset.x, startOffset.y,
                endOffset.x,
                endOffset.y,
                this.hudPaint
        )
        drawStatusIndicator(canvas, "T", "S")
        canvas?.drawText(text, textOffsets.rightTextOffset.x, textOffsets.rightTextOffset.y, mRightTextPaint)
    }

    fun drawStatusIndicator(canvas: Canvas?, top: String, bottom: String) {
        val textOffsets = getTextOffsets("")
        canvas?.drawText(top, textOffsets.halfHeightTextOffset.x, textOffsets.halfHeightTextOffset.y,
                mHalfHeightTextPaint)
        canvas?.drawText(bottom, textOffsets.halfHeightTextOffset.x,
                textOffsets.halfHeightTextOffset.y + (edgeOffset + hudCellWidth / 3f),
                mHalfHeightTextPaint
        )
    }

}
