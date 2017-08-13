package moe.chyyran.sidonia.drawables

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

import com.ustwo.clockwise.wearable.WatchFace

class BatteryDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    internal var mNumTextPaint: Paint
    internal var mTextPaint: Paint

    internal var mTopBlockOffset: PointF
    internal var mTopTextPoint: PointF
    internal var mTopNumPoint: PointF

    init {
        val textNumSize = desiredMinimumWidth / 6.3f

        mTopBlockOffset = this.getCellOffset(1, 0)

        // 数字用
        mNumTextPaint = Paint()

        mNumTextPaint.typeface = this.latinFont
        mNumTextPaint.color = this.backgroundColor
        mNumTextPaint.textSize = textNumSize
        mNumTextPaint.isAntiAlias = true

        // 文字用
        mTextPaint = Paint(mNumTextPaint)
        mTextPaint.color = this.hudColor

        // Top
        // mplus-1m-regular 漢字一字
        val fontMetrics: Paint.FontMetrics = mTextPaint.fontMetrics
        val chargedTextWidthDiff = (hudCellWidth - mTextPaint.measureText("電")) / 2f
        val chargedTextHeightDiff = (hudCellWidth - (fontMetrics.ascent + fontMetrics.descent)) / 2f

        mTopTextPoint = PointF(mTopBlockOffset.x + chargedTextWidthDiff,
                mTopBlockOffset.y + chargedTextHeightDiff)

        // Browing 数字2字
        val numFontMetrics = mNumTextPaint.fontMetrics
        val percentTextWidthDiff = (hudCellWidth - mNumTextPaint.measureText("00")) / 2f
        val percentTextHeightDiff = (hudCellWidth - (numFontMetrics.ascent + numFontMetrics.descent)) / 2f

        mTopNumPoint = PointF(mTopBlockOffset.x + percentTextWidthDiff,
                mTopBlockOffset.y + percentTextHeightDiff)
    }

    fun drawBatteryPercent(canvas: Canvas?, batteryPct: Int, isCharging: Boolean) {
        canvas?.save()
        mTextPaint.typeface = this.kanjiFont
        if (isCharging) {
            mTextPaint.color = this.alertColor
            canvas?.drawText("電", mTopTextPoint.x, mTopTextPoint.y, mTextPaint)
            mTextPaint.color = this.hudColor
        } else if (batteryPct < 20){
            mTextPaint.color = this.criticalColor
            canvas?.drawText("低", mTopTextPoint.x, mTopTextPoint.y, mTextPaint)
            mTextPaint.color = this.hudColor
        } else {
            // Fill the cell
            canvas?.drawRect(mTopBlockOffset.x, mTopBlockOffset.y,
                    mTopBlockOffset.x + hudCellWidth,
                    mTopBlockOffset.y + hudCellWidth, this.hudPaint)

            if(batteryPct < 100) {
                val batteryPercentageString = String.format("%02d", batteryPct)
                val numFontMetrics = mNumTextPaint.fontMetrics
                // Calculate and measure the text every time.
                val percentTextWidthDiff = (hudCellWidth - mNumTextPaint.measureText(batteryPercentageString)) / 2f
                val percentTextHeightDiff = (hudCellWidth - (numFontMetrics.ascent + numFontMetrics.descent)) / 2f

                val percentageOffset = PointF(mTopBlockOffset.x + percentTextWidthDiff,
                        mTopBlockOffset.y + percentTextHeightDiff)
                canvas?.drawText(batteryPercentageString, percentageOffset.x, percentageOffset.y, mNumTextPaint)
            } else {
                mTextPaint.color = this.backgroundColor
                canvas?.drawText("満", mTopTextPoint.x, mTopTextPoint.y, mTextPaint)
                mTextPaint.color = this.hudColor
            }
        }
        canvas?.restore()

    }
}
