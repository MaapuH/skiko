package org.jetbrains.skia

import org.jetbrains.skia.impl.InteropPointer
import org.jetbrains.skia.impl.NativePointer
import org.jetbrains.skia.impl.Stats
import org.jetbrains.skia.impl.withResult
//
///**
// * @param target GL enum
// * @param id GL texture id
// * @param format GL enum, should be a sized, internal format
// * @param protected is texture protected on gpu
// */
//data class GLTextureInfo(
//    val target: Int,
//    val id: Int,
//    val format: Int = 0,
//    val protected: Boolean = false
//) {
//    companion object {
//        fun createUsing(
//            _ptr: NativePointer,
//            _nGetGLTextureInfo: (_ptr: NativePointer, intArrayPointer: InteropPointer) -> Boolean
//        ): GLTextureInfo? {
//            Stats.onNativeCall()
//            var isGL: Boolean = false
//            return withResult(IntArray(4)) { intArrayPointer ->
//                isGL = _nGetGLTextureInfo(_ptr, intArrayPointer)
//            }.let {
//                if (isGL) GLTextureInfo(
//                    target = it[0],
//                    id = it[1],
//                    format = it[2],
//                    protected = (it[3]) > 0,
//                ) else null
//            }
//        }
//    }
//}
