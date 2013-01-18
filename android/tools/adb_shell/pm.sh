# 查看所有权限
adb shell pm list permissions 
adb shell pm list permissions -s

pm
usage: pm list packages [-f] [-d] [-e] [-s] [-e] [-u] [FILTER]
       pm list permission-groups
       pm list permissions [-g] [-f] [-d] [-u] [GROUP]
       pm list instrumentation [-f] [TARGET-PACKAGE]
       pm list features
       pm list libraries
       pm path PACKAGE
       pm install [-l] [-r] [-t] [-i INSTALLER_PACKAGE_NAME] [-s] [-f] PATH
       pm uninstall [-k] PACKAGE
       pm clear PACKAGE
       pm enable PACKAGE_OR_COMPONENT
       pm disable PACKAGE_OR_COMPONENT
       pm disable-user PACKAGE_OR_COMPONENT
       pm set-install-location [0/auto] [1/internal] [2/external]
       pm get-install-location
       pm createUser USER_NAME
       pm removeUser USER_ID

pm list packages: prints all packages, optionally only
  those whose package name contains the text in FILTER.  Options:
    -f: see their associated file.
    -d: filter to only show disbled packages.
    -e: filter to only show enabled packages.
    -s: filter to only show system packages.
    -3: filter to only show third party packages.
    -u: also include uninstalled packages.

pm list permission-groups: prints all known permission groups.

pm list permissions: prints all known permissions, optionally only
  those in GROUP.  Options:
    -g: organize by group.
    -f: print all information.
    -s: short summary.
    -d: only list dangerous permissions.
    -u: list only the permissions users will see.

pm list instrumentation: use to list all test packages; optionally
  supply <TARGET-PACKAGE> to list the test packages for a particular
  application.  Options:
    -f: list the .apk file for the test package.

pm list features: prints all features of the system.

pm path: print the path to the .apk of the given PACKAGE.

pm install: installs a package to the system.  Options:
    -l: install the package with FORWARD_LOCK.
    -r: reinstall an exisiting app, keeping its data.
    -t: allow test .apks to be installed.
    -i: specify the installer package name.
    -s: install package on sdcard.
    -f: install package on internal flash.

pm uninstall: removes a package from the system. Options:
    -k: keep the data and cache directories around after package removal.

pm clear: deletes all data associated with a package.

pm enable, disable, disable-user: these commands change the enabled state
  of a given package or component (written as "package/class").

pm get-install-location: returns the current install location.
    0 [auto]: Let system decide the best location
    1 [internal]: Install on internal device storage
    2 [external]: Install on external media

pm set-install-location: changes the default install location.
  NOTE: this is only intended for debugging; using this can cause
  applications to break and other undersireable behavior.
    0 [auto]: Let system decide the best location
    1 [internal]: Install on internal device storage
    2 [external]: Install on external media

