package org.jetbrains.skiko.canvas

import org.jetbrains.skia.BackendRenderTarget
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.DirectContext
import org.jetbrains.skia.Surface
import org.jetbrains.skiko.AlreadyInitializedException
import org.jetbrains.skiko.RenderException

/**
 * Contains GPU [context].
 * You must call [onDirectContextInit] and [onDirectContextDispose] on [context] changes.
 */
abstract class DirectCanvasManager(
    onDraw: Canvas.() -> Unit
): AbstractCanvasManager(onDraw) {
    var context: DirectContext? = null; protected set
    var renderTarget: BackendRenderTarget? = null; protected set
    var surface: Surface? = null; protected set

    /**
     * Should be called right after [context] was created.
     */
    protected abstract fun onDirectContextInit(context: DirectContext)

    /**
     * Should be called right before [context] disposal.
     */
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

    /**
     * Flush the [context] calls to be executed by GPU backend.
     *
     * Should only be called after the [context] was initialized.
     */
    open fun flush() = context!!.flush()

    override fun dispose() {
        canvas?.close()
        surface?.close()
        renderTarget?.close()
        context?.also(::onDirectContextDispose)?.close()
        canvas = null
        surface = null
        renderTarget = null
        context = null
    }

    override fun initiate(width: Int, height: Int) {
        if (context != null) throw AlreadyInitializedException()
        context = initContext().also(::onDirectContextInit)
        renderTarget = initBackendRenderTarget()
        surface = initSurface()
        canvas = initCanvas()
    }
}