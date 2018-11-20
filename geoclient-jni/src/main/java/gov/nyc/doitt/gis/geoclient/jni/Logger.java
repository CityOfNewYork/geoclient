package gov.nyc.doitt.gis.geoclient.jni;

public class Logger {

	public static final int LEVEL_DEBUG = 2;
	public static final int LEVEL_INFO = 1;
	public static final int LEVEL_ERROR = 0;

	private static final String DEFAULT_PREFIX = "JNI_TEST";
	private int level;
	private String prefix;

	private Logger(int level) {
		this(level, null);
	}

	private Logger(int level, String prefix) {
		super();
		this.level = level;
		this.prefix = prefix != null ? prefix : DEFAULT_PREFIX;
	}

	public static <T> Logger getLogger(Class<T> clazz) {
		return new Logger(LEVEL_INFO, clazz.getName());
	}

	public static <T> Logger getInfoLogger(Class<T> clazz) {
		return getLogger(clazz);
	}

	public static <T> Logger getDebugLogger(Class<T> clazz) {
		return new Logger(LEVEL_DEBUG, clazz.getName());
	}

	public static <T> Logger getErrorLogger(Class<T> clazz) {
		return new Logger(LEVEL_ERROR, clazz.getName());
	}

	public void debug() {
		debug("");
	}
	
	public void debug(String message) {
		if (level >= LEVEL_DEBUG) {
			doLog(LEVEL_DEBUG, prefix, message);
		}
	}

	public void info() {
		info("");
	}
	
	public void info(String message) {
		if (level >= LEVEL_INFO) {
			doLog(LEVEL_INFO, prefix, message);
		}
	}

	public void error() {
		error("");
	}
	
	public void error(String message) {
		if (level >= LEVEL_ERROR) {
			doLog(LEVEL_ERROR, prefix, message);
		}
	}

	public void raw(int withLevel, String message) {
		if (withLevel <= level) {
			doLog(withLevel, prefix, message);
		}
	}

	private void doLog(int level, String prefix, String message) {
		String levelText = "";
		switch (level) {
		case LEVEL_ERROR:
			levelText = "ERROR ";
			break;

		case LEVEL_INFO:
			levelText = "INFO ";
			break;

		case LEVEL_DEBUG:
			levelText = "DEBUG ";
			break;

		default:
			break;
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(levelText);
		
		if (prefix != null) {
			buffer.append("[");
			buffer.append(prefix);
			buffer.append("] ");
		}
		if (message != null) {
			buffer.append(message);
		}
		System.out.println(buffer);
	}
}