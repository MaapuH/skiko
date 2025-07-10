package org.jetbrains.skiko.canvas

import org.jetbrains.skia.*
import org.jetbrains.skiko.RenderException

class GLCanvasManager(
    onDraw: Canvas.() -> Unit,
    private val onContextInit: (DirectContext) -> Unit,
    private val onContextDispose: (DirectContext) -> Unit,
    var surfaceProps: SurfaceProps,
    var surfaceOrigin: SurfaceOrigin,
    var surfaceColorFormat: SurfaceColorFormat = SurfaceColorFormat.RGBA_8888,
    var surfaceColorSpace: ColorSpace = ColorSpace.sRGB,
    var samples: Int = 0,
    var stencilBits: Int = 8,
    var frameBufferId: Int,
    var framebufferFormat: Int = FramebufferFormat.GR_GL_RGBA8
) : DirectCanvasManager(onDraw) {
    override fun onDirectContextInit(context: DirectContext) = onContextInit(context)
    override fun onDirectContextDispose(context: DirectContext) = onContextDispose(context)

    override fun resize(width: Int, height: Int) {
        currentWidth = width
        currentHeight = height
        surface?.close()
        renderTarget?.close()
        renderTarget = initBackendRenderTarget()
        surface = initSurface()
        canvas = initCanvas()
    }

    override fun initSurface(): Surface = Surface.makeFromBackendRenderTarget(
        context!!,
        renderTarget!!,
        surfaceOrigin,
        surfaceColorFormat,
        surfaceColorSpace,
        surfaceProps
    ) ?: throw RenderException("Failed to create Surface")

    override fun initBackendRenderTarget(): BackendRenderTarget = BackendRenderTarget.makeGL(
        currentWidth,
        currentHeight,
        samples,
        stencilBits,
        frameBufferId,
        framebufferFormat
    )

    override fun initContext(): DirectContext = DirectContext.makeGL()

    override fun initCanvas(): Canvas = surface!!.canvas
}