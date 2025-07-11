package org.jetbrains.skiko.context

import org.jetbrains.skia.BackendTexture
import org.jetbrains.skia.DirectContext
import org.jetbrains.skiko.GraphicsApi
import org.jetbrains.skiko.backend.BackendInfo

/**
 * Contains [DirectContext] and [BackendInfo] to allow for managing skia and backend resources.
 *
 * @property directContext
 */
interface RenderScope {
    /**
     * This will be null if the [DirectContext] is not initialized yet.
     *
     * This allows to create [BackendTexture], or other skia resources directly.
     */
    val directContext: DirectContext?

    /**
     * Platform-specific info about backend, [GraphicsApi], and device being used for rendering.
     */
    val backendInfo: BackendInfo?

    fun addDirectContextChangeListener(listener: (DirectContext) -> Unit)

    fun addBackendInfoChangeListener(listener: (BackendInfo) -> Unit)
}