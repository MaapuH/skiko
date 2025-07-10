package org.jetbrains.skiko.backend

/**
 * Encapsulates platform-specific information about device used to render, and its capabilities: [deviceName], [vramTotalMb].
 */
interface DeviceInfo {
    val deviceName: String
    val vramTotalMb: Int

    data class WindowsOpenGL(
        val deviceContext: Long,
        override val deviceName: String,
        override val vramTotalMb: Int
    ): DeviceInfo

    data class LinuxOpenGL(
        val display: Long,
        val drawable: Long,
        override val deviceName: String,
        override val vramTotalMb: Int
    ): DeviceInfo
}