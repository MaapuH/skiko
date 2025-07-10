package org.jetbrains.skiko.redrawer

interface Redrawer {
    fun dispose()
    fun needRedraw()
    fun redrawImmediately()
    fun syncBounds() = Unit
    fun setVisible(isVisible: Boolean) = Unit
    val renderInfo: String
}