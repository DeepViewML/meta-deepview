DESCRIPTION = "DeepView VisionPack UI Library"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit python3-dir systemd

SRC_URI = "https://deepviewml.com/vpkui/vpkui-${PV}-linux-armv8.zip;subdir=${S}"
SRC_URI += "file://segmentation.service"
SRC_URI += "file://detection.service"
SRC_URI += "file://facedetect.service"
SRC_URI += "file://faceblur.service"
SRC_URI += "file://headpose.service"
SRC_URI += "file://bodypose.service"
SRC_URI[sha256sum] = "a2c556c6cd7e6de9c7e3aa5dc1eaace7824da44e22b09534167d1917a5d97828"
S = "${WORKDIR}/${PN}-${PV}"

PACKAGES = "${PN}-apps ${PN}"

DEPENDS = "deepview-rt vaal videostream libsoup-2.4"
RDEPENDS_${PN}-apps = "vpkui"
RDEPENDS_${PN} = "deepview-rt vaal videostream libsoup-2.4"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${systemd_system_unitdir}

    cp -rP  ${S}/bin/*_headless ${D}${bindir}

    install -m 0644 ${WORKDIR}/segmentation.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/detection.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/facedetect.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/faceblur.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/headpose.service ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/bodypose.service ${D}${systemd_system_unitdir}

    chown -R root:root "${D}"
}

FILES_SOLIBSDEV = ""

FILES:${PN}-apps += "${bindir}"
FILES:${PN} += "${libdir}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP:${PN} += "dev-so"

BBCLASSEXTEND = "nativesdk"
COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
