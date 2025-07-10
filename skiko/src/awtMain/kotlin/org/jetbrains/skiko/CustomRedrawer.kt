package org.jetbrains.skiko

import org.jetbrains.skiko.SkiaLayerAnalytics.DeviceAnalytics
import org.jetbrains.skiko.redrawer.Redrawer

@OptIn(ExperimentalSkikoApi::class)
abstract class CustomRedrawer(
    protected val layer: SkiaLayer,
    protected val analytics: SkiaLayerAnalytics,
    protected val graphicsApi: GraphicsApi,
) : Redrawer, DesktopRedrawer {
    protected var isFirstFrameRendered = false

    protected val rendererAnalytics = analytics.renderer(Version.skiko, hostOs, graphicsApi)
    protected var deviceAnalytics: DeviceAnalytics? = null
    protected var isDisposed = false
        private set

    init {
        rendererAnalytics.init()
    }

    override fun dispose() {
        require(!isDisposed) { "$javaClass is disposed" }
        isDisposed = true
    }

    /**
     * Should be called when the device name is known as early, as possible.
     */
    protected fun onDeviceChosen(deviceName: String?) {
        require(!isDisposed) { "$javaClass is disposed" }
        require(deviceAnalytics == null) { "deviceAnalytics is not null" }
        rendererAnalytics.deviceChosen()
        deviceAnalytics = analytics.device(Version.skiko, hostOs, graphicsApi, deviceName)
        deviceAnalytics?.init()
    }

    /**
     * Should be called when initialization of graphic context is ended. Only call it after [onDeviceChosen]
     */
    protected fun onContextInit() {
        require(!isDisposed) { "$javaClass is disposed" }
        requireNotNull(deviceAnalytics) { "deviceAnalytics is not null. Call onDeviceChosen after choosing the drawing device" }
        deviceAnalytics?.contextInit()
    }

    protected fun update(nanoTime: Long) {
        require(!isDisposed) { "$javaClass is disposed" }
        layer.update(nanoTime)
    }

    protected fun inDrawScope(body: () -> Unit) {
        requireNotNull(deviceAnalytics) { "deviceAnalytics is not null. Call onDeviceChosen after choosing the drawing device" }
        if (!isDisposed) {
            if (!isFirstFrameRendered) {
                deviceAnalytics?.beforeFirstFrameRender()
            }
            deviceAnalytics?.beforeFrameRender()
            layer.inDrawScope(body)
            if (!isFirstFrameRendered && !isDisposed) {
                deviceAnalytics?.afterFirstFrameRender()
            }
            deviceAnalytics?.afterFrameRender()
            isFirstFrameRendered = true
        }
    }
}