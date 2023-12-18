DESCRIPTION = "DeepViewRT is Au-Zone's proprietary inference engine optimized for embedded devices."
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://doc/LICENSE.rtf;md5=9c227e8e93757feebd98ec96cd51b715"

inherit python3-dir

SRC_URI = "https://deepviewml.com/rt/deepview-rt-${PV}-linux-armv8.tar.gz;subdir=${S}"
SRC_URI[md5sum] = "06b130bf0f3c048f63323eabaf0c9395"
SRC_URI[sha256sum] = "6801c39eb9c60cbb96c07d31769dcb32907467c3b5dd36bbdc888dc232c7047e"
S = "${WORKDIR}/${PN}-${PV}"

PACKAGECONFIG ?= " \
    ${PACKAGECONFIG_DEFAULT} \
    ${PACKAGECONFIG_OPENVX} \
"

PACKAGECONFIG_DEFAULT                 = ""
PACKAGECONFIG_DEFAULT:mx8-nxp-bsp     = ""
PACKAGECONFIG_DEFAULT:mx8mm-nxp-bsp   = ""
PACKAGECONFIG_DEFAULT:mx8mnul-nxp-bsp = ""
PACKAGECONFIG_DEFAULT:mx8mpul-nxp-bsp = ""
PACKAGECONFIG_OPENVX                  = ""
PACKAGECONFIG_OPENVX:mx8-nxp-bsp      = "openvx"
PACKAGECONFIG_OPENVX:mx8mm-nxp-bsp    = ""
PACKAGECONFIG_OPENVX:mx8mnul-nxp-bsp  = ""
PACKAGECONFIG_OPENVX:mx8mpul-nxp-bsp  = ""
PACKAGECONFIG_OPENVX:mx8ulp-nxp-bsp   = ""

PACKAGECONFIG[openvx] = ",,,libopenvx-imx"

PACKAGES = "${PN}-dev ${PN}-doc ${PN}-python ${PN}-modelrunner ${PN}"

DEPENDS = "python3 python3-pip-native"
RDEPENDS_${PN}-python = "python3 python3-cffi"
RDEPENDS_${PN} = ""

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -d ${D}${libdir}/cmake
    install -d ${D}${includedir}
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/deepview
    install -d ${D}/${docdir}/${PN}

    cp ${S}/bin/modelrunner ${D}${bindir}
    cp -rP  ${S}/lib/*so* ${D}${libdir}
    cp -r  ${S}/lib/cmake/* ${D}${libdir}/cmake
    cp -r  ${S}/include/* ${D}${includedir}
    cp -r ${S}/lib/python3/deepview/* ${D}/${PYTHON_SITEPACKAGES_DIR}/deepview
    cp -r ${S}/doc/* ${D}/${docdir}/${PN}

    if ! [ "${@bb.utils.filter('PACKAGECONFIG', 'openvx', d)}" ]; then
        rm ${D}${libdir}/deepview-rt-openvx.so
    fi

    chown -R root:root "${D}"
}

FILES_SOLIBSDEV = ""

FILES:${PN}-dev += "${includedir}"
FILES:${PN}-dev += "${libdir}/libdeepview-rt.so"
FILES:${PN}-dev += "${libdir}/cmake"
FILES:${PN}-doc += "${docdir}"
FILES:${PN}-python += "${PYTHON_SITEPACKAGES_DIR}"
FILES:${PN}-modelrunner += "${bindir}/modelrunner"
FILES:${PN} += "${libdir}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP:${PN} += "dev-so"

BBCLASSEXTEND = "nativesdk"
COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
