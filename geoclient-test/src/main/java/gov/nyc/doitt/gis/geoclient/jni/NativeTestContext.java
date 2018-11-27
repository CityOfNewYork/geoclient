package gov.nyc.doitt.gis.geoclient.jni;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import gov.nyc.doitt.gis.geoclient.jni.JniContext.SystemProperty;

public class NativeTestContext {

	final static String TMPDIR = JniContext.getSystemProperty(SystemProperty.JAVA_IO_TMPDIR);
	private static class MutableStringProperty {
		private final String key;
		private final String originalValue;
		private String currentValue;

		MutableStringProperty(String key, String originalValue, String currentValue) {
			super();
			this.key = key;
			this.originalValue = originalValue;
			this.currentValue = currentValue;
		}

		MutableStringProperty(String key, String originalValue) {
			this(key, originalValue, originalValue);
		}

		String getValue() {
			return currentValue;
		}

		String restore() {
			currentValue = originalValue;
			return originalValue;
		}

		String update(String newValue) {
			this.currentValue = newValue;
			return currentValue;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((currentValue == null) ? 0 : currentValue.hashCode());
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((originalValue == null) ? 0 : originalValue.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MutableStringProperty other = (MutableStringProperty) obj;
			if (currentValue == null) {
				if (other.currentValue != null)
					return false;
			} else if (!currentValue.equals(other.currentValue))
				return false;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (originalValue == null) {
				if (other.originalValue != null)
					return false;
			} else if (!originalValue.equals(other.originalValue))
				return false;
			return true;
		}
	}

	private ConcurrentMap<String, MutableStringProperty> db = new ConcurrentHashMap<>();

	public NativeTestContext() {
		super();
		this.db.put(TMPDIR, new MutableStringProperty(TMPDIR, System.getProperty(TMPDIR)));
	}

	public String getTempDir() {
		return db.get(TMPDIR).getValue();
	}

	public synchronized String updateTempDir(String path) {
		return db.get(TMPDIR).update(path);
	}

	public synchronized String restoreTempDir() {
		return db.get(TMPDIR).restore();
	}
}