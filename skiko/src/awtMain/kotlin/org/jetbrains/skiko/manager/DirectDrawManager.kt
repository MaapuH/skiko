package org.jetbrains.skiko.manager

import org.jetbrains.skia.BackendRenderTarget
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.DirectContext
import org.jetbrains.skia.Surface
import org.jetbrains.skiko.RenderException

/**
 * Contains GPU [context].
 */
abstract class DirectDrawManager(
    onDraw: Canvas.() -> Unit
): AbstractDrawManager(onDraw) {
    var context: DirectContext? = null; protected set
    var renderTarget: BackendRenderTarget? = null; protected set
    var surface: Surface? = null; protected set

    protected abstract fun onDirectContextInit(context: DirectContext)
    protected abstract fun onDirectContextDispose(context: DirectContext)

    /**
     * Create [Surface], throw [RenderException] if failed, otherwise return [Surface].
     *
     * [context], [renderTarget] are initialized at this point.
     *
     * @throws RenderException if failed to create [Surface]
     */
    protected abstract fun initSurface(): Surface
    /**
     * Create [BackendRenderTarget], throw [RenderException] if failed, otherwise return [BackendRenderTarget].
     *
     * [context] is initialized at this point.
     *
     * @throws RenderException if failed to create [BackendRenderTarget]
     */
    protected abstract fun initBackendRenderTarget(): BackendRenderTarget
    /**
     * Create [DirectContext], throw [RenderException] if failed, otherwise return [DirectContext].
     *
     * @throws RenderException if failed to create [DirectContext]
     */
    protected abstract fun initContext(): DirectContext

    protected open fun flush() {
        context?.flush()
    }

    override fun dispose() {
        canvas?.close()
        surface?.close()
        renderTarget?.close()
        context?.also(::onDirectContextDispose)?.close()
    }

    override fun initiate() {
        context = initContext().also(::onDirectContextInit)
        renderTarget = initBackendRenderTarget()
        surface = initSurface()
        canvas = initCanvas()
    }
}