package org.jetbrains.skiko.canvas

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skiko.AlreadyInitializedException
import org.jetbrains.skiko.RenderException

abstract class AbstractCanvasManager(
    private val onDraw: Canvas.() -> Unit,
): CanvasManager {
    var canvas: Canvas? = null; protected set
    /**
     * Create [Canvas], throw [RenderException] if failed, otherwise return [Canvas].
     *
     * @throws RenderException if failed to create [Canvas]
     */
    protected abstract fun initCanvas(): Canvas

    override fun initiate(width: Int, height: Int) {
        if (canvas != null) throw AlreadyInitializedException()
        currentWidth = width
        currentHeight = height
        canvas = initCanvas()
    }

    override fun dispose() {
        canvas?.close()
        canvas = null
    }

    open var clearColor: Int = Color.TRANSPARENT

    override fun draw() {
        canvas?.let {
            it.clear(clearColor)
            it.onDraw()
        }
    }

    protected var currentWidth: Int = 0
    protected var currentHeight: Int = 0

    fun isSizeChanged(width: Int, height: Int): Boolean {
        if (width != currentWidth) return true
        if (height != currentHeight) return true
        return false
    }
}