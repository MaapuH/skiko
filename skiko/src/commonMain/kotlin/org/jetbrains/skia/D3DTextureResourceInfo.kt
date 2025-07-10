package org.jetbrains.skia
//
//struct GrD3DTextureResourceInfo {
//    gr_cp<ID3D12Resource>    fResource             = nullptr;
//    sk_sp<GrD3DAlloc>        fAlloc                = nullptr;
//    D3D12_RESOURCE_STATES    fResourceState        = D3D12_RESOURCE_STATE_COMMON;
//    DXGI_FORMAT              fFormat               = DXGI_FORMAT_UNKNOWN;
//    uint32_t                 fSampleCount          = 1;
//    uint32_t                 fLevelCount           = 0;
//    unsigned int             fSampleQualityPattern = DXGI_STANDARD_MULTISAMPLE_QUALITY_PATTERN;
//    skgpu::Protected         fProtected            = skgpu::Protected::kNo;
//data class D3DTextureResourceInfo(
//    val
//    val format: Int,
//    val sampleCount: Int,
//    val levelCount: Int,
//    val sampleQualityPattern: Int,
//    val protected: Boolean = false
//)
