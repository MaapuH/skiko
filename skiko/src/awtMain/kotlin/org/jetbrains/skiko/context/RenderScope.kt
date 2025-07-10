package org.jetbrains.skiko.context

import org.jetbrains.skia.BackendTexture
import org.jetbrains.skia.DirectContext
import org.jetbrains.skiko.GraphicsApi
import org.jetbrains.skiko.backend.BackendInfo

/**
 * Contains [DirectContext] and [BackendInfo] to allow for sharing GPU resources.
 *
 * @property directContext
 */
interface RenderScope {
    /**
     * This will be null if the [DirectContext] is not initialized yet.
     *
     * This allows to create [BackendTexture], or other resources that can be used to provide images, rendered on GPU.
     */
    val directContext: DirectContext?

    /**
     * Platform-specific info about backend, [GraphicsApi], and device being used for rendering.
     */
    val backendInfo: BackendInfo
}