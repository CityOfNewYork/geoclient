//
// This class is copied from Facebook's RocksDB v5.9.2
// See https://github.com/facebook/rocksdb
//

// Copyright (c) 2014, Facebook, Inc.  All rights reserved.
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree. An additional grant
// of patent rights can be found in the PATENTS file in the same directory.
package gov.nyc.doitt.gis.geoclient.jni.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentTest {
  private final static String ARCH_FIELD_NAME = "ARCH";
  private final static String OS_FIELD_NAME = "OS";

  private static String INITIAL_OS;
  private static String INITIAL_ARCH;

  @BeforeClass
  public static void saveState() {
    INITIAL_ARCH = getEnvironmentClassField(ARCH_FIELD_NAME);
    INITIAL_OS = getEnvironmentClassField(OS_FIELD_NAME);
  }

  @Test
  public void linux64() {
    setEnvironmentClassFields("Linux", "x64");
    assertThat(Environment.isWindows()).isFalse();
//    assertThat(Environment.getJniLibraryExtension()).
//        isEqualTo(".so");
//    assertThat(Environment.getJniLibraryFileName("geoclient")).
//        isEqualTo("libgeoclientjni-linux64.so");
//    assertThat(Environment.getSharedLibraryFileName("geoclient")).
//        isEqualTo("libgeoclientjni.so");
  }

  @Test
  public void detectWindows(){
    setEnvironmentClassFields("win", "x64");
//    assertThat(Environment.isWindows()).isTrue();
  }

  @Test
  public void win64() {
    setEnvironmentClassFields("windows", "x64");
//    assertThat(Environment.isWindows()).isTrue();
//    assertThat(Environment.getJniLibraryExtension()).
//      isEqualTo(".dll");
//    assertThat(Environment.getJniLibraryFileName("geoclient")).
//      isEqualTo("geoclientjni-windows-x64.dll");
//    assertThat(Environment.getSharedLibraryFileName("geoclient")).
//      isEqualTo("geoclientjni.dll");
  }

  private void setEnvironmentClassFields(String osName,
      String osArch) {
    setEnvironmentClassField(OS_FIELD_NAME, osName);
    setEnvironmentClassField(ARCH_FIELD_NAME, osArch);
  }

  @AfterClass
  public static void restoreState() {
    setEnvironmentClassField(OS_FIELD_NAME, INITIAL_OS);
    setEnvironmentClassField(ARCH_FIELD_NAME, INITIAL_ARCH);
  }

  private static String getEnvironmentClassField(String fieldName) {
    final Field field;
    try {
      field = Environment.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      final Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
      return (String)field.get(null);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static void setEnvironmentClassField(String fieldName, String value) {
    final Field field;
    try {
      field = Environment.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      final Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
      field.set(null, value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
