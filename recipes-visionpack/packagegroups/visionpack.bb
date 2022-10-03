DESCRIPTION = "DeepView VisionPack SDK"
PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

RDEPENDS:${PN} = "deepview-rt deepview-rt-python vaal vaal-gstreamer vaal-python videostream videostream-gstreamer videostream-python"
