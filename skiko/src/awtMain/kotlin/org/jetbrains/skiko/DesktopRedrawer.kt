package org.jetbrains.skiko

import org.jetbrains.skia.DirectContext
import org.jetbrains.skiko.backend.BackendInfo

interface DesktopRedrawer {
    val backendInfo: BackendInfo
    val directContext: DirectContext?
}

fun directContextNotInitializedError(): Nothing =
    throw UninitializedPropertyAccessException("property directContext is not initialized")

fun backendInfoIsNotInitializedError(): Nothing =
    throw UninitializedPropertyAccessException("property backendInfo is not initialized")