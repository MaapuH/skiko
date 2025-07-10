package org.jetbrains.skiko

class AlreadyInitializedException(override val message: String? = null): Exception(
    "Initialization call was made second time, after initialization was already done"
)