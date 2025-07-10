package org.jetbrains.skiko

import org.jetbrains.skiko.context.RenderScope

interface DesktopSkiaLayer {
    fun <T> withRenderInfo(block: RenderScope.() -> T): T
}