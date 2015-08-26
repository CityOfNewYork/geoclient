#
# This build works for Linux and Windows. It requires the following to be
# properly installed: 
#
# Linux
# -----
# - GNU Toolchain:
#   - GNU Make, v3.81 or greater
#   - gcc, v4.4.7 or greater
#   - ld, v2.20.51 or greater
# - JDK, 64-bit,  v1.7 or greater referenced by the JAVA_HOME environment variable 
#
# Windows
# -------
# - MSYS/MINGW installed using mingw-get.exe v0.6.0 or later which includes:
#   - GNU Make, v3.81 or greater
#   - gcc, v4.7.2 or greater
#   - ld (GNU Binutils), v2.23 or greater
#
# - JDK, 32-bit, v1.7 or greater referenced by the JAVA_HOME environment variable 
#   NOTE: A 64-bit JVM will not work with the Win32 Geosupport DLL's
#   NOTE: JAVA_HOME environment variable is read with a msys/mingw specific hack 
#
# - Maven, v3.1 or greater. Assumes project POM is $(PROJECT_DIR)/pom.xml
#
# All Platforms
# -------------
# - bash shell (provided by MSYS/MINGW on Windows)
# - sed (provided by MSYS/MINGW on Windows) 
# - Geosupport installation with platform-specific headers and libraries
#   NOTE: At this time, the "out-of-the-box" Geosupport headers files:
#         geo.h, pac.h (Linux, Windows), NYCGeo.h (Windows)
#         all need manual adjustment to fix overlapping comments and incorrect 
#         macro definitions to work properly with gcc
#
.DEFAULT_GOAL := all

#
# Common variables
#
BUILD_DIR=maven-output
CC=gcc
CLASSPATH=.:$(JAR_GLUEGEN):$(JAR_ANTLR):$(JAR_GEOCLIENT)
CTEST_BASE=ctest
DIST_DIR=$(PROJECT_DIR)/dist/v$(GEOCLIENT_VERSION)/$(PLATFORM)
GEOCLIENT_VERSION=$(shell cat $(BUILD_DIR)/VERSION.txt)
GEOFILES?=$(GEOSUPPORT_SERVER_CURRENT)/fls/
GEO_LIB_DIR=$(GEOSUPPORT_SERVER_CURRENT)/lib
JAR_ANTLR=$(shell echo -en ./$(BUILD_DIR)/antlr-*.jar)
JAR_GEOCLIENT=$(shell echo -en ./$(BUILD_DIR)/geoclient-jni-*.jar)
JAR_GLUEGEN=$(shell echo -en ./$(BUILD_DIR)/gluegen-*.jar)
PAC_H=$(GEO_INC_DIR)/pac.h
SHARED_OBJ_BASE=GeoclientImpl_JNI

# Required for testc and testjni (Linux)
export LD_LIBRARY_PATH=$(GEO_LIB_DIR):.
export GEOFILES

ifeq ($(OS),Windows_NT)  
###### Windows #######
CFLAGS=-I. -I$(GEO_INC_DIR) -I$(JAVA_DIR)/include -I$(JAVA_DIR)/include/win32 -O0 -g3 -Wall -Wno-unused-function
CTEST_EXE=$(CTEST_BASE).exe
GEOSUPPORT_SERVER_CURRENT?=c:/geosupport
GEO_INC_DIR=$(GEOSUPPORT_SERVER_CURRENT)/include
JAVA_DIR=$(shell cd $$JAVA_HOME && echo -en `pwd -W`)
LDFLAGS=-L$(GEO_LIB_DIR)
LDLIBS=-lm -lNYCgeo -lgeo -ledequiv -lapequiv -lsan -lsnd -lstExcpt -lStdLast -lStdUniv -lstEnder -lstretch -lthined
LDSOFLAGS=-shared -Wl,--add-stdcall-alias
PATH_SEP=;
PLATFORM=mingw32-i686
PROJECT_DIR=../../..
SHARED_OBJ_LIB=$(SHARED_OBJ_BASE).dll
###### End Windows #######
else
###### Linux #######
CFLAGS=-I. -I$(GEO_INC_DIR) -I$(JAVA_DIR)/include -I$(JAVA_DIR)/include/linux -O0 -g3 -Wall -fPIC
CTEST_EXE=$(CTEST_BASE)
GEOSUPPORT_SERVER_CURRENT?=/gis/geosupport/server/current
GEO_INC_DIR=$(GEOSUPPORT_SERVER_CURRENT)/include/foruser
JAVA_DIR=$(JAVA_HOME)
JAVA_HOME?=/gis/java/sun/jdk1.7.0_55
LDFLAGS=-fPIC -L$(GEO_LIB_DIR)
LDSOFLAGS=-shared -Wl,-soname,$@
LDLIBS=-lc -lm -lgeo -ledequiv -lapequiv -lsan -lsnd -lstExcpt -lStdLast -lStdUniv -lstEnder -lstretch -lthined
PATH_SEP=:
PLATFORM=linux-x86_64
PROJECT_DIR=.
SHARED_OBJ_LIB=lib$(SHARED_OBJ_BASE).so
###### End Linux #######
endif

#
# Common targets
#
.PHONY : all clean dist testc testjni .setcp

all : clean testc testjni

dist : testc testjni
	mkdir -p $(DIST_DIR)
	cp $(SHARED_OBJ_LIB) $(CTEST_EXE) $(DIST_DIR)

testjni : $(SHARED_OBJ_LIB) .setcp
	@echo "CLASSPATH=$(CLASSPATH)"
	@echo "-Djava.library.path=".:$(GEO_LIB_DIR)""
	$(JAVA_DIR)/bin/java -Djava.library.path=".$(PATH_SEP)$(GEO_LIB_DIR)" -cp "$(CLASSPATH)" gov.nyc.doitt.gis.geoclient.jni.test.JniTest --debug

testc : $(CTEST_EXE)
	./$(CTEST_EXE)

# Compile and link shared object library (*.so or *.dll)
$(SHARED_OBJ_LIB): $(SHARED_OBJ_BASE).o geoclient.o
	gcc $(LDSOFLAGS) -o $@ $(LDFLAGS) $(SHARED_OBJ_BASE).o geoclient.o $(LDLIBS)

# Generate the JNI code using Gluegen
# Search and replace geoclient.cfg on Linux to change the location of the 
# generated *.java classes
#
#	sed --in-place 's|'@generatedCSrc@'|$(GEN_JNI_C_DIR)|g' $(BUILD_DIR)/geoclient.cfg
#	sed --in-place 's|'@generatedJavaSrc@'|$(GEN_JNI_JAVA_DIR)|g' $(BUILD_DIR)/geoclient.cfg
#
$(SHARED_OBJ_BASE).c : geoclient.h $(PAC_H) .setcp
	$(JAVA_DIR)/bin/java -cp "$(CLASSPATH)" com.jogamp.gluegen.GlueGen -I$(GEO_INC_DIR) -Ecom.jogamp.gluegen.JavaEmitter -Cgeoclient.cfg geoclient.h

# Let make know that ctest also depends on geoclient.o so pattern rule will
# compile and link it in
$(CTEST_EXE): $(CTEST_BASE).o geoclient.o

#
# Implicit pattern rule for C source compilation where '$@' matches the target 
# and '$<' matches the first dependant (<target> : <dep1> <dep2>) 
#
%.o : %.c %.h $(PAC_H)
	$(CC) -c $(CFLAGS) $< -o $@

# Pattern template to create executable (without extension) from object .o file
# $^ matches all the pre-requsities (no duplicates)
# By default LINK.o = $(CC) $(LDFLAGS) $(TARGET_ARCH)
%: %.o
	$(LINK.o) $^ $(LDLIBS) -o $@

# Windows version of the above
%.exe: %.o
	$(LINK.o) $^ $(LDLIBS) -o $@

clean :
	-rm -f *.o
	-rm -f *.so
	-rm -f *.dll
	-rm -f *.exe	
	-rm -f $(CTEST_EXE)
	-rm -f $(SHARED_OBJ_BASE).c
	-rm -Rf generated

	