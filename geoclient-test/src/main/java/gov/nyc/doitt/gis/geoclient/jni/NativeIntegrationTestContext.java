package gov.nyc.doitt.gis.geoclient.jni;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NativeIntegrationTestContext {

	private final static String TMPDIR = "java.io.tmpdir";
	private final static String BACK_PFX = "bak_";
	
	private ConcurrentMap<String, String> db = new ConcurrentHashMap<>();
	
	public void useTempDir(String path) {
		backup(TMPDIR, getCurrentTmpDir());
		override(TMPDIR, path);
	}
	
	public void restoreTempDir() {
		restore(TMPDIR);
	}
	
	private String override(String key, String value) {
		return db.put(key, value);
	}
	
	private String backup(String key, String value) {
		return db.put(String.format("%s%s",  BACK_PFX, key), value);
	}
	
	private String restore(String key) {
		String original = key.substring(3);
		return db.put(original, db.get(String.format("%s%s",  BACK_PFX, key)));
	}
	
	private String getCurrentTmpDir() {
		return System.getProperty("java.io.tmpdir", "");
	}
}
