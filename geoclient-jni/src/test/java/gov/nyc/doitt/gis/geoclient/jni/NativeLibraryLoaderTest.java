//
// This class is copied from Facebook's RocksDB v5.9.2
// See https://github.com/facebook/rocksdb
//

// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree. An additional grant
// of patent rights can be found in the PATENTS file in the same directory.
package gov.nyc.doitt.gis.geoclient.jni;

import java.io.IOException;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

public class NativeLibraryLoaderTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void tempFolder() throws IOException {
	}

}
