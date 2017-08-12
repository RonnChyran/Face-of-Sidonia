package moe.chyyran.sidonia.Drawables


import android.graphics.Canvas

import com.ustwo.clockwise.WatchFace

class GridDrawable(watch: WatchFace) : SidoniaDrawable(watch) {

    private var mBackgroundLines: FloatArray = floatArrayOf(
            // Vertical Lines
            hudCellWidth * 0 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4 + edgeOffset,
            // Horizontal Lines
            hudCellWidth * 1 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 2 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 3 + edgeOffset, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 5 + edgeOffset)

    private var mBackgroundCrosshairs: FloatArray = floatArrayOf(
            // 縦棒
            hudCellWidth * 0 + edgeOffset, hudCellWidth * 0, hudCellWidth * 0 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 0, hudCellWidth * 1 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 2 + edgeOffset, hudCellWidth * 0, hudCellWidth * 2 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 3 + edgeOffset, hudCellWidth * 0, hudCellWidth * 3 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 0, hudCellWidth * 4 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 0, hudCellWidth * 5 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 1, hudCellWidth * 0 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 1, hudCellWidth * 1 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 2 + edgeOffset, hudCellWidth * 1, hudCellWidth * 2 + edgeOffset, hudCellWidth * 1 + 3, // 内側
            hudCellWidth * 3 + edgeOffset, hudCellWidth * 1, hudCellWidth * 3 + edgeOffset, hudCellWidth * 1 + 3, // 内側
            hudCellWidth * 4 + edgeOffset, hudCellWidth * 1, hudCellWidth * 4 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 1, hudCellWidth * 5 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 2, hudCellWidth * 0 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 2, hudCellWidth * 1 + edgeOffset, hudCellWidth * 2 + 8,
            // hudCellWidth * 2 + edgeOffset, hudCellWidth * 2, hudCellWidth * 2 + edgeOffset, hudCellWidth * 2 + 8,
            // hudCellWidth * 3 + edgeOffset, hudCellWidth * 2, hudCellWidth * 3 + edgeOffset, hudCellWidth * 2 + 8,
            hudCellWidth * 4 + edgeOffset, hudCellWidth * 2, hudCellWidth * 4 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 2, hudCellWidth * 5 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 3, hudCellWidth * 0 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 3, hudCellWidth * 1 + edgeOffset, hudCellWidth * 3 + 8,
            // hudCellWidth * 2 + edgeOffset, hudCellWidth * 3, hudCellWidth * 2 + edgeOffset, hudCellWidth * 3 + 8,
            // hudCellWidth * 3 + edgeOffset, hudCellWidth * 3, hudCellWidth * 3 + edgeOffset, hudCellWidth * 3 + 8,
            hudCellWidth * 4 + edgeOffset, hudCellWidth * 3, hudCellWidth * 4 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 3, hudCellWidth * 5 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 4, hudCellWidth * 1 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 2 + edgeOffset, hudCellWidth * 4 + 5, hudCellWidth * 2 + edgeOffset, hudCellWidth * 4 + 8, // 内側
            hudCellWidth * 3 + edgeOffset, hudCellWidth * 4 + 5, hudCellWidth * 3 + edgeOffset, hudCellWidth * 4 + 8, // 内側
            hudCellWidth * 4 + edgeOffset, hudCellWidth * 4, hudCellWidth * 4 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 5, hudCellWidth * 0 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 2 + edgeOffset, hudCellWidth * 5, hudCellWidth * 2 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 3 + edgeOffset, hudCellWidth * 5, hudCellWidth * 3 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 5, hudCellWidth * 4 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 5, hudCellWidth * 5 + edgeOffset, hudCellWidth * 5 + 8,
            // 横棒
            hudCellWidth * 0, hudCellWidth * 0 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 0, hudCellWidth * 1 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 0, hudCellWidth * 2 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 2 + edgeOffset, hudCellWidth * 0, hudCellWidth * 3 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 3 + edgeOffset, hudCellWidth * 0, hudCellWidth * 4 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 0, hudCellWidth * 5 + edgeOffset, hudCellWidth * 0 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 1, hudCellWidth * 0 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 1, hudCellWidth * 1 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 1, hudCellWidth * 2 + edgeOffset, hudCellWidth * 1 + 3, hudCellWidth * 2 + edgeOffset, // 内側
            hudCellWidth * 1, hudCellWidth * 3 + edgeOffset, hudCellWidth * 1 + 3, hudCellWidth * 3 + edgeOffset, // 内側
            hudCellWidth * 1, hudCellWidth * 4 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 1, hudCellWidth * 5 + edgeOffset, hudCellWidth * 1 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 2, hudCellWidth * 0 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 2, hudCellWidth * 1 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 1 + edgeOffset,
            // hudCellWidth * 2, hudCellWidth * 2 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 2 + edgeOffset,
            // hudCellWidth * 2, hudCellWidth * 3 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 3 + edgeOffset,
            hudCellWidth * 2, hudCellWidth * 4 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 2, hudCellWidth * 5 + edgeOffset, hudCellWidth * 2 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 3, hudCellWidth * 0 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 3, hudCellWidth * 1 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 1 + edgeOffset,
            // hudCellWidth * 3, hudCellWidth * 2 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 2 + edgeOffset,
            // hudCellWidth * 3, hudCellWidth * 3 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 3 + edgeOffset,
            hudCellWidth * 3, hudCellWidth * 4 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 3, hudCellWidth * 5 + edgeOffset, hudCellWidth * 3 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 4, hudCellWidth * 1 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 4 + edgeOffset, hudCellWidth * 2 + 5, hudCellWidth * 4 + 8, hudCellWidth * 2 + edgeOffset, // 内側
            hudCellWidth * 4 + edgeOffset, hudCellWidth * 3 + 5, hudCellWidth * 4 + 8, hudCellWidth * 3 + edgeOffset, // 内側
            hudCellWidth * 4, hudCellWidth * 4 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 4, hudCellWidth * 5 + edgeOffset, hudCellWidth * 4 + 8, hudCellWidth * 5 + edgeOffset, hudCellWidth * 5, hudCellWidth * 0 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 0 + edgeOffset, hudCellWidth * 5, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 1 + edgeOffset, hudCellWidth * 5, hudCellWidth * 2 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 2 + edgeOffset, hudCellWidth * 5, hudCellWidth * 3 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 3 + edgeOffset, hudCellWidth * 5, hudCellWidth * 4 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 4 + edgeOffset, hudCellWidth * 5, hudCellWidth * 5 + edgeOffset, hudCellWidth * 5 + 8, hudCellWidth * 5 + edgeOffset)

    fun drawGrid(canvas: Canvas?) {
        canvas?.save()
        canvas?.drawLines(mBackgroundLines, this.hudPaint)
        canvas?.drawLines(mBackgroundCrosshairs, this.hudBoldPaint)
        canvas?.restore()
    }
}