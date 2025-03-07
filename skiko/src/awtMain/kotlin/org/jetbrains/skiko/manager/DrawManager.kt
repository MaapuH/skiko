package org.jetbrains.skiko.manager

import org.jetbrains.skia.*
import org.jetbrains.skiko.*

/**
 * Creates [Canvas], [draw]s on it, [resize]s it, [dispose]s it.
 *
 * Before drawing, [initiate] must be called.
 *
 * On resize, [resize] should be called.
 *
 * When not needed, [dispose] should be called.
 */
interface DrawManager {
    /**
     * Dispose all resources, such as [Surface], [BackendRenderTarget], [DirectContext].
     *
     * This might be called before [initiate].
     */
    fun dispose()

    /**
     * Create [Canvas] and other resources, needed for drawing.
     *
     * This should throw [RenderException] if failed to create resources.
     *
     * This should only be called once before any [draw],[resize] calls.
     * In order to reuse [DrawManager] after [dispose], call this.
     *
     * @throws RenderException if failed to create resources
     */
    fun initiate()

    /**
     * Draw onto previously created [Canvas].
     * This should only be called after [initiate].
     *
     * **MUST NOT** create or dispose any resources during this call.
     *
     * If resources are missing, should return immediately.
     */
    fun draw()

    /**
     * Resize resources needed for drawing.
     * In case of [BackendRenderTarget], this means disposing current and creating new one with new size.
     *
     * This should only be called after [initiate].
     *
     * Reuses resources as much as possible: it should not recreate [DirectContext], if it's not required.
     *
     * This should throw [RenderException] if failed.
     *
     * @param width width in pixels
     * @param height height in pixels
     *
     * @throws RenderException if failed to resize
     */
    fun resize(width: Int, height: Int)

    /**
     * @return info about underlying rendering context, for example [GraphicsApi], [OS], [Arch].
     */
    fun renderInfo(): String
}

abstract class AbstractDrawManager(
    private val onDraw: Canvas.() -> Unit,
): DrawManager {
    protected abstract val graphicsApi: GraphicsApi
    var canvas: Canvas? = null; protected set
    /**
     * Create [Canvas], throw [RenderException] if failed, otherwise return [Canvas].
     *
     * @throws RenderException if failed to create [Canvas]
     */
    protected abstract fun initCanvas(): Canvas

    override fun initiate() {
        canvas = initCanvas()
    }

    override fun dispose() {
        canvas?.close()
    }

    var clearColor: Int = Color.TRANSPARENT

    open fun rendererInfo(): String = "GraphicsApi: ${graphicsApi}\nOS: ${hostOs.id} ${hostArch.id}\n"

    override fun draw() {
        canvas?.let {
            it.clear(clearColor)
            it.onDraw()
        }
    }
}

fun isTransparentBackground(layer: SkiaLayer): Boolean {
    if (hostOs == OS.MacOS) {
        // MacOS transparency is always supported
        return true
    }
    if (layer.fullscreen) {
        // for non-MacOS in fullscreen transparency is not supported
        return false
    }
    // for non-MacOS in non-fullscreen transparency provided by [layer]
    return layer.transparency
}