package org.jetbrains.skiko.canvas

import org.jetbrains.skia.*
import org.jetbrains.skiko.AlreadyInitializedException
import org.jetbrains.skiko.RenderException

/**
 * Creates [Canvas], [draw]s on it, [resize]s it, [dispose]s it.
 *
 * Before drawing, [initiate] must be called.
 *
 * On resize, [resize] should be called.
 *
 * When not needed, [dispose] should be called.
 *
 * @see AbstractCanvasManager
 */
interface CanvasManager {
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
     * In order to reuse [CanvasManager] after [dispose], call this.
     *
     * @throws RenderException if failed to create resources
     * @throws AlreadyInitializedException if this was already called and was not disposed yet
     */
    fun initiate(width: Int, height: Int)

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
}

