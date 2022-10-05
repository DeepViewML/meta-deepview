DESCRIPTION = "DeepView VisionPack SDK"
PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PACKAGES = "${PN}-base ${PN}-gstreamer ${PN}-python ${PN}-apps ${PN}"

RDEPENDS:${PN}-base = "deepview-rt vaal videostream"
RDEPENDS:${PN}-gstreamer = "vaal-gstreamer videostream-gstreamer"
RDEPENDS:${PN}-python = "deepview-rt-python vaal-python videostream-python"
RDEPENDS:${PN}-apps = "vaal-apps"
RDEPENDS:${PN} = "${PN}-base ${PN}-gstreamer ${PN}-python ${PN}-apps"
