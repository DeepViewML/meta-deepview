DESCRIPTION = "DeepView VideoStream provides an SDK and GStreamer plugin for zero-copy video frame sharing across processes and Docker containers."
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://doc/LICENSE.txt;md5=e153ccee5db0d7cbd514bc6ba454f981"

inherit python3-dir

SRC_URI = "https://deepviewml.com/videostream/videostream-${PV}-linux-armv8.zip;subdir=${S}"
SRC_URI[md5sum] = "2c2f88b82946e2bcb94c84dc41863647"
SRC_URI[sha256sum] = "f4ac73e49095340a9ee05a2ecb5360fceb2fd3a7878e24d112cdca6552b68845"
S = "${WORKDIR}/${PN}-${PV}"

PACKAGES = "${PN}-dev ${PN}-doc ${PN}-python ${PN}-gstreamer ${PN}"

DEPENDS = "python3 python3-pip-native gstreamer1.0 gstreamer1.0-plugins-base"
RDEPENDS_${PN}-python = "python3 python3-cffi"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${libdir}/gstreamer-1.0
    install -d ${D}${libdir}/cmake
    install -d ${D}${includedir}
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/deepview
    install -d ${D}/${docdir}/${PN}

    cp -rP  ${S}/lib/*so* ${D}${libdir}
    cp -rP  ${S}/lib/gstreamer-1.0/*so* ${D}${libdir}/gstreamer-1.0
    cp -r  ${S}/lib/cmake/* ${D}${libdir}/cmake
    cp -r  ${S}/include/* ${D}${includedir}
    cp -r ${S}/lib/python3/deepview/* ${D}/${PYTHON_SITEPACKAGES_DIR}/deepview
    cp -r ${S}/doc/* ${D}/${docdir}/${PN}

    chown -R root:root "${D}"
}

FILES_SOLIBSDEV = ""

FILES:${PN}-dev += "${includedir}"
FILES:${PN}-dev += "${libdir}/cmake"
FILES:${PN}-gstreamer += "${libdir}/gstreamer-1.0"
FILES:${PN}-doc += "${docdir}"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}"
FILES:${PN} += "${libdir}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP:${PN} += "dev-so"

BBCLASSEXTEND = "nativesdk"
COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
