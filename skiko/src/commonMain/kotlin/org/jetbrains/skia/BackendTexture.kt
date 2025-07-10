package org.jetbrains.skia

import org.jetbrains.skia.impl.*
import org.jetbrains.skia.impl.Library.Companion.staticLoad
import org.jetbrains.skia.impl.getPtr
import org.jetbrains.skia.impl.reachabilityBarrier

class BackendTexture internal constructor(ptr: NativePointer) : Managed(ptr, _FinalizerHolder.PTR) {
    companion object {
        /**
         * Creates BackendTexture from GL texture.
         *
         * @param width - width of the [BackendTexture] to be created
         * @param height - height of the [BackendTexture] to be created
         * @param isMipmapped - if the passed [textureId] has a GL mipmap, this should be true, otherwise false
         * @param textureId - GL id of the texture to use
         * @param textureTarget - GL enum, must be valid texture target for 2D, e.g. GL_TEXTURE_2D
         * @param textureFormat - GL enum, must be valid color format, e.g. GL_RGBA or GL_BGRA_INTEGER
         *
         * @see glTextureParametersModified
         */
        fun makeGL(
            width: Int,
            height: Int,
            isMipmapped: Boolean,
            textureId: Int,
            textureTarget: Int,
            textureFormat: Int
        ): BackendTexture {
            Stats.onNativeCall()
            val ptr = _nMakeGL(
                width,
                height,
                isMipmapped,
                textureId,
                textureTarget,
                textureFormat
            )
            return BackendTexture(ptr)
        }

        init {
            staticLoad()
        }
    }

    /**
     * Call this to indicate to DirectContext that the texture parameters have been modified in the GL context externally
     */
    fun glTextureParametersModified() {
        return try {
            Stats.onNativeCall()
            _nGLTextureParametersModified(getPtr(this))
        } finally {
            reachabilityBarrier(this)
        }
    }
//    val backend: GraphicsApi get() = TODO()
//
//    /**
//     * @return If the [backend] API is [GraphicsApi.OPENGL], returns [GLTextureInfo].
//     * Otherwise, returns null if the [backend] API is not [GraphicsApi.OPENGL].
//     */
//    fun getGLTextureInfo(): GLTextureInfo? {
//        return try {
//            var result: GLTextureInfo? = null
//            commonSynchronized(this) {
//                result = GLTextureInfo.createUsing(
//                    getPtr(this),
//                    _nGetGLTextureInfo = ::_nGetGLTextureInfo
//                )
//            }
//            result
//        } finally {
//            reachabilityBarrier(this)
//        }
//    }

    private object _FinalizerHolder {
        val PTR = BackendTexture_nGetFinalizer()
    }
}

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nGetFinalizer")
@ModuleImport("./skiko.mjs", "org_jetbrains_skia_BackendTexture__1nGetFinalizer")
private external fun BackendTexture_nGetFinalizer(): NativePointer

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nMakeGL")
@ModuleImport("./skiko.mjs", "org_jetbrains_skia_BackendTexture__1nMakeGL")
private external fun _nMakeGL(
    width: Int,
    height: Int,
    isMipmapped: Boolean,
    textureId: Int,
    target: Int,
    format: Int
): NativePointer

@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nGLTextureParametersModified")
@ModuleImport("./skiko.mjs", "org_jetbrains_skia_BackendTexture__1nGLTextureParametersModified")
private external fun _nGLTextureParametersModified(backendTexturePtr: NativePointer)
//
//@ExternalSymbolName("org_jetbrains_skia_BackendTexture__1nGetGLTextureInfo")
//@ModuleImport("./skiko.mjs", "org_jetbrains_skia_BackendTexture__1nGetGLTextureInfo")
//private external fun _nGetGLTextureInfo(backendTexturePtr: NativePointer, textureInfoResult: InteropPointer): Boolean