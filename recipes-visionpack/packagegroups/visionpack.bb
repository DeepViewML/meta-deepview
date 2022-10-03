DESCRIPTION = "DeepView VisionPack SDK"
PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

RDEPENDS:${PN} = "vaal vaal-gstreamer vaal-python videostream videostream-gstreamer videostream-python"
