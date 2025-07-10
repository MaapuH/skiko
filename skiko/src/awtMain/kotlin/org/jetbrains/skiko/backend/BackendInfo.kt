package org.jetbrains.skiko.backend

import org.jetbrains.skiko.GraphicsApi

/**
 * This is used to provide platform-specific information to share GPU resources, like textures.
 *
 * @property deviceInfo returns [DeviceInfo] or null if there's no device being used,
 * for example when [graphicsApi] is Software.
 */
interface BackendInfo {
    val deviceInfo: DeviceInfo?
    val graphicsApi: GraphicsApi

    /**
     * @property deviceInfo is [DeviceInfo.LinuxOpenGL] for Linux and [DeviceInfo.WindowsOpenGL] for Windows.
     */
    data class OpenGL(
        val glContext: Long,
        override val deviceInfo: DeviceInfo
    ) : BackendInfo {
        override val graphicsApi: GraphicsApi = GraphicsApi.OPENGL
        override fun toString(): String {
            return "OpenGL(glContext=$glContext, deviceInfo=$deviceInfo, graphicsApi=$graphicsApi)"
        }
    }

    data object SoftwareFast: BackendInfo {
        override val deviceInfo: DeviceInfo? = null
        override val graphicsApi: GraphicsApi = GraphicsApi.SOFTWARE_FAST
    }

    data object SoftwareCompat: BackendInfo {
        override val deviceInfo: DeviceInfo? = null
        override val graphicsApi: GraphicsApi = GraphicsApi.SOFTWARE_COMPAT
    }
}