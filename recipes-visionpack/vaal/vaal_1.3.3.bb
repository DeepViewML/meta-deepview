DESCRIPTION = "DeepView Vision AI Application Library is part of the VisionPack SDK and provides the vision pipeline enablement."
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://doc/LICENSE.txt;md5=e153ccee5db0d7cbd514bc6ba454f981"

inherit python3-dir

SRC_URI = "https://deepviewml.com/vaal/vaal-${PV}-linux-armv8.zip;subdir=${S}"
SRC_URI[md5sum] = "4e4095d5a85bcac3ce4997a41014a39f"
SRC_URI[sha256sum] = "22ec3eb8dc54e52adc154fc52f39ffe73a8e6328f37c52bb54117b5c24c98644"
S = "${WORKDIR}/${PN}-${PV}"

PACKAGES = "${PN}-dev ${PN}-doc ${PN}-python ${PN}-gstreamer ${PN}-apps ${PN}"

DEPENDS = "python3 python3-pip-native gstreamer1.0 gstreamer1.0-plugins-base deepview-rt"
RDEPENDS_${PN}-python = "python3 python3-cffi"
RDEPENDS_${PN}-gstreamer = "deepview-rt-python"
RDEPENDS_${PN} = "deepview-rt"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -d ${D}${libdir}/gstreamer-1.0
    install -d ${D}${libdir}/cmake
    install -d ${D}${includedir}
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/deepview
    install -d ${D}/${docdir}/${PN}

    cp -rP  ${S}/bin/* ${D}${bindir}
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
FILES:${PN}-apps += "${bindir}"
FILES:${PN} += "${libdir}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP:${PN} += "dev-so"

BBCLASSEXTEND = "nativesdk"
COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
