#!/bin/sh
#
#****************************************************************
#
# Component =
#
# Copyright (C) 2009-2010, VirtualLogix. All Rights Reserved.
#
# Use of this product is contingent on the existence of an executed license
# agreement between VirtualLogix or one of its sublicensee, and your
# organization, which specifies this software's terms of use. This software is
# here defined as VirtualLogix Intellectual Property for the purposes of
# determining terms of use as defined within the license agreement.
#
# Contributor(s):
#   Christophe Lizzi (Christophe.Lizzi@virtuallogix.com)
#

PROG=`basename $0`
DIR=`dirname $0`

OPROFILE_DIR=`cd ${DIR} ; pwd`

export OPROFILE_EVENTS_DIR=${OPROFILE_DIR}/prebuilt/linux-x86/oprofile

export TARGET_SYMBOLS_DIR=${OPROFILE_DIR}/out/target/product/android/symbols

export HOST_BIN_DIR=${OPROFILE_DIR}/out/host/linux-x86/bin

ADB=${HOST_BIN_DIR}/adb

#OPREPORT=${OPROFILE_EVENTS_DIR}/bin/opreport
OPREPORT=/usr/bin/opreport

OPIMPORT_PULL=${OPROFILE_DIR}/opimport_pull

OPCONTROL=/system/xbin/opcontrol

KILL=/system/bin/kill

TARGET_IP_ADDR=""
TARGET_KERN=true
TARGET_KERN_IMAGE="/sdcard/vmlinux"
TARGET_KERN_RANGE=""
TARGET_KERN_START=c0c0c000 # set here the default kernel start address specific to your target
TARGET_KERN_END=c0ffffff   # set here the default kernel end address specific to your target
VERBOSE=false
DELAY=10

while getopts t:D:d:k:r:vxnh c
do
    case ${c} in
	t) TARGET_IP_ADDR=${OPTARG} ;;
	D) TARGET_DEVICE=${OPTARG} ;;
	d) DELAY=${OPTARG} ;;
	k) TARGET_KERN_IMAGE=${OPTARG} ;;
	r) TARGET_KERN_RANGE=${OPTARG} ;;
	v) VERBOSE=true ;;
	x) set -x ;;
	n) TARGET_KERN=false ;;
	h) echo "${PROG}: USAGE: $0 [-t <IP addr>] [-D <tty device>] [-d <duration>] " ;
           echo "${PROG}: [-k <vmlinux kernel image>] [-r <kernel start addr>,<kernel end addr>] [-n] [-v] [-x]" ;
           echo "${PROG}:         -n = no vmlinux kernel image" ;
           echo "${PROG}:         -v = verbose mode" ;
           echo "${PROG}:         -x = trace mode" ;
           exit 0 ;;
	*) echo "${PROG}: unexpected parameter" ; exit 1 ;;
    esac
done
if [ ! -x ${ADB} ]
then
    echo "${PROG}: 'adb' not found in ${HOST_BIN_DIR}"
    exit 1
fi

if [ ! -x ${OPREPORT} ]
then
    echo "${PROG}: 'opreport' not found in ${OPROFILE_EVENTS_DIR}"
    exit 1
fi

if [ ! -d ${TARGET_SYMBOLS_DIR} ]
then
    echo "${PROG}: non-stripped Android binaries not found in ${TARGET_SYMBOLS_DIR}"
    exit 1
fi


if [ -z "${TARGET_IP_ADDR}" ]
then
    TARGET_IP_ADDR=${ADBHOST}
fi

#TARGET_DEVICE="004999010640000"
#TARGET_IP_ADDR=""
#echo $TARGET_DEVICE
#echo $TARGET_IP_ADDR

if [ -n "${TARGET_IP_ADDR}" -a -n "${TARGET_DEVICE}" ]
then
echo "Not enter"
exit 1
fi

if [ -n "${TARGET_IP_ADDR}" ]
then
    ping -c 1 -W 5 ${TARGET_IP_ADDR}
    if [ $? -ne 0 ]
    then
        echo "${PROG}: could not ping target device at ${TARGET_IP_ADDR}"
        exit 1
    fi
    echo "TARGET_IP_ADDR FOUND ${TARGET_IP_ADDR}"
    export ADBHOST=${TARGET_IP_ADDR}
fi

if [ -n "${TARGET_DEVICE}" ]
then
    if [ ! -c ${TARGET_DEVICE} ]
    then
	echo "${PROG}: ${TARGET_DEVICE} is not an existing character device"
	exit 1
    fi

    export VLX_ADB_DEV=${TARGET_DEVICE}
fi


retry=true

while true
do
    # Start the adb server if not already started
    ${ADB} start-server
    if [ $? -ne 0 ]
    then
	echo "${PROG}: could not start 'adb' server"
	exit 1
    fi

    # Check device presence
    ${VERBOSE} && echo "${PROG}: checking device presence..."
    serial=`${ADB} get-serialno`
    if [ $? -ne 0 -o "${serial}" = "unknown" ]
    then
	if ${retry}
	then
	    echo "${PROG}: no target device detected; stopping adb server and retrying"
	    # Kill the adb server
	    ${ADB} kill-server
	    retry=false
	    continue
        else
	    echo "${PROG}: no target device detected (is the adbd daemon running on the target device?)"
	    exit 1
	fi
    fi
    break
done

# Check device connectivity
${VERBOSE} && echo "${PROG}: checking connectivity..."
${ADB} shell "/system/bin/ls /" >/dev/null
if [ $? -ne 0 ]
then
    echo "${PROG}: target device not reachable"
    exit 1
fi

${VERBOSE} && echo "${PROG}: target at ${TARGET_IP_ADDR} OK"

${ADB} shell "${KILL} oprofiled"
${ADB} shell "${OPCONTROL} --shutdown"
${ADB} shell "${OPCONTROL} --setup"
${ADB} shell "echo 16 > /dev/oprofile/backtrace_depth"


if ${TARGET_KERN}
then

    if [ -z "${TARGET_KERN_RANGE}" ]
    then
        # Use default kernel address range
        TARGET_KERN_RANGE="${TARGET_KERN_START},${TARGET_KERN_END}"
    fi

    # Prepare extra opcontrol params
    TARGET_KERN_PARMS="--vmlinux=${TARGET_KERN_IMAGE} --kernel-range=${TARGET_KERN_RANGE}"
fi

# The below adb command may refuse to terminate and just hang up
# (probably because the oprofiled daemon is still running).
# As a workaround, we launch it as a background process and kill it
# after having reached its supposed completion time.

${ADB} shell "${OPCONTROL} ${TARGET_KERN_PARMS} --start; \
              sleep ${DELAY} ; \
              ${OPCONTROL} --stop ; \
              ${OPCONTROL} --status" &

ADB_PID=$!
${VERBOSE} && echo "${PROG}: launched adb process ${ADB_PID}"

# Wait, giving the adb process a chance to terminate
sleep `expr ${DELAY} + 3`

# Then kill it
if [ -n "`ps --no-header -p ${ADB_PID}`" ]
then
    ${VERBOSE} && echo "${PROG}: terminating adb process ${ADB_PID}"
    kill ${ADB_PID} >/dev/null
fi

${ADB} shell "${OPCONTROL} --status"

SYMBOLS=${TARGET_SYMBOLS_DIR}

${VERBOSE} && echo "${PROG}: pulling samples for post-processing"

#PATH=${HOST_BIN_DIR}:${PATH} OUT=${SYMBOLS} ${OPIMPORT_PULL} -r /tmp/oprofile.pull
PATH=${HOST_BIN_DIR}:${PATH} OUT=${SYMBOLS} ./opimport_pull -r /tmp/oprofile.pull
