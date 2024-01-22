DESCRIPTION = "DeepView VisionPack UI Library"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit python3-dir

SRC_URI = "https://deepviewml.com/vpkui/vpkui-${PV}-linux-armv8.zip;subdir=${S}"
SRC_URI[sha256sum] = "1ea38ace224bc1df28a86e88e199dc411e3f6d4e7dc63cf24c036eb7350a6825"
S = "${WORKDIR}/${PN}-${PV}"

PACKAGES = "${PN}-apps ${PN}"

DEPENDS = "deepview-rt vaal videostream libsoup-2.4"
RDEPENDS_${PN}-apps = "vpkui"
RDEPENDS_${PN} = "deepview-rt vaal videostream libsoup-2.4"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${bindir}

    cp -rP  ${S}/bin/*_headless ${D}${bindir}

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
