#!/bin/sh
# rotate_fixed_logs.sh - rotate MySQL log file that has a fixed name

# Argument 1: log filename

if [ $# -ne 1 ]; then
  echo "Usage: $0 logname" 1>&2
  exit 1
fi

logfile=$1

mv $logfile.6 $logfile.7
mv $logfile.5 $logfile.6
mv $logfile.4 $logfile.5
mv $logfile.3 $logfile.4
mv $logfile.2 $logfile.3
mv $logfile.1 $logfile.2
mv $logfile $logfile.1
mysqladmin flush-logs
