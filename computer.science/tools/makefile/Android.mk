Android Build

build/envsetup.sh命令
    m
        等价于在当前android代码树的顶层路径下执行make命令。
    mm
        make from current directory
    mmm
        给定package的路径，则mm会make相应的package。
        例如， mmm package/apps/Calculator

常用命令:
    make help
        Common make targets:
        ----------------------------------------------------------------------------------
        droid                   Default target
        clean                   (aka clobber) equivalent to rm -rf out/
        snod                    Quickly rebuild the system image from built packages
        offline-sdk-docs        Generate the HTML for the developer SDK docs
        doc-comment-check-docs  Check HTML doc links & validity, without generating HTML
        libandroid_runtime      All the JNI framework stuff
        framework               All the java framework stuff
        services                The system server (Java) and friends
        help                    You are reading it right now

    make showcommands
        输出调试信息 [Good!]

    $(info "test") # 在make的过程中输出调试信息
    $(error "test" $(LOCAL_COPY_FILES)) # 在make的过程中输出调试信息, 并停止make


编译.a库的时候，如果.a库中的.o链接时报告无法找到其他静态库的函数（但是LOCAL_STATIC_LIBRARIES中又确实包括了该库），
试着使用下面变量。

LOCAL_WHOLE_STATIC_LIBRARIES
These are the static libraries that you want to include in your module without allowing the linker to remove dead code from them. This is mostly useful if you want to add a static library to a shared library and have the static library's content exposed from the shared library.

LOCAL_WHOLE_STATIC_LIBRARIES := libsqlite3_android

# 如果framework中的接口是@hide的，使用下面语句会导致找不到该接口
LOCAL_SDK_VERSION := current
去掉这一行就可以了

#为每个apk添加mdpi, ldpi和多种语言
#在每个apk的Android.mk中添加：
LOCAL_AAPT_FLAGS += -c mdpi -c ldpi -c es_ES -c tr_TR

# 拷贝当前目录下的静态库到build目录
# Build with prebuilt lib
include $(CLEAR_VARS)
LOCAL_MODULE := libmtvserver
LOCAL_MODULE_SUFFIX := .a
LOCAL_MODULE_TAGS := user eng
LOCAL_SRC_FILES := libmtvserver.a
LOCAL_MODULE_CLASS := STATIC_LIBRARIES
include $(BUILD_PREBUILT)

# 拷贝当前目录下的动态库到build目录
# Build with prebuilt lib
include $(CLEAR_VARS)
LOCAL_MODULE := libsmsmbbms.so
LOCAL_MODULE_TAGS := user eng
LOCAL_SRC_FILES := libsmsmbbms.so
LOCAL_MODULE_CLASS := SHARED_LIBRARIES
LOCAL_MODULE_PATH := $(TARGET_OUT_SHARED_LIBRARIES)
OVERRIDE_BUILT_MODULE_PATH := $(TARGET_OUT_INTERMEDIATE_LIBRARIES)
include $(BUILD_PREBUILT)

Question:
build/core/main.mk
# 1. What's the theory
$(foreach m,$(ALL_MODULES), \
     $(eval r := $(ALL_MODULES.$(m).REQUIRED)) \
     $(if $(r), \
         $(eval r := $(call module-installed-files,$(r))) \
         $(eval $(call add-required-deps,$(ALL_MODULES.$(m).INSTALLED),$(r))) \
      ) \
)


################################################################################
# 编译静态java包
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_MODULE := Usimutil1
include $(BUILD_STATIC_JAVA_LIBRARY)
################################################################################

################################################################################
# include a static support-v4.jar in this apk
# the name libv4 is abitrary
################################################################################
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := libv4 # 随便的一个名字

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := TouchImage
# LOCAL_AAPT_FLAGS +=-c mdpi

# LOCAL_PROGUARD_ENABLED := full

include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
# 该库位于android apk目录的lib/目录下
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := libv4:lib/support-v4.jar 
include $(BUILD_MULTI_PREBUILT)
################################################################################
