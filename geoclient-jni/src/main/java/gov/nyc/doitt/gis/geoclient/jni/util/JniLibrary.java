package gov.nyc.doitt.gis.geoclient.jni.util;

import java.util.Objects;

public class JniLibrary {

	private final String name;
	private final String componentName;
	private final String operatingSystem;
	private final String architecture;
	private final String version;

	public static class Builder {

		private String name;
		private String componentName;
		private String operatingSystem;
		private String architecture;
		private String version;

		public Builder componentName(String componentName) {
			this.componentName = componentName;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder operatingSystem(String operatingSystem) {
			this.operatingSystem = operatingSystem;
			return this;
		}

		public Builder architecture(String architecture) {
			this.architecture = architecture;
			return this;
		}

		public Builder version(String version) {
			this.version = version;
			return this;
		}

		public JniLibrary build() {
			if (this.name == null) {
				throw new IllegalStateException("Property 'name' has cannot be null");
			}

			if (this.operatingSystem == null) {
				throw new IllegalStateException("Property 'operatingSystem' has cannot be null");
			}

			if (this.architecture == null) {
				throw new IllegalStateException("Property 'architecture' has cannot be null");
			}

			if (this.version == null) {
				this.version = "UNKNOWN";
			}

			return new JniLibrary(name, componentName, operatingSystem, architecture, version);
		}

	}

	private JniLibrary(String name, String componentName, String osName, String architecture, String version) {
		super();
		this.name = name;
		this.componentName = componentName;
		this.operatingSystem = osName;
		this.architecture = architecture;
		this.version = version;
	}

	public static Builder builder() {
		return new Builder();
	}

	
	public String getComponentName() {
		return componentName;
	}

	public String getPlatformName() {
		return String.format("%s-%s", getOperatingSystem(), getArchitecture());
	}

	public String getName() {
		return name;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public String getArchitecture() {
		return architecture;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(architecture, name, operatingSystem, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JniLibrary other = (JniLibrary) obj;
		return Objects.equals(architecture, other.architecture) && Objects.equals(name, other.name)
				&& Objects.equals(operatingSystem, other.operatingSystem) && Objects.equals(version, other.version);
	}

	@Override
	public String toString() {
		return "JniLibrary [name=" + name + ", operatingSystem=" + operatingSystem + ", architecture=" + architecture
				+ ", version=" + version + "]";
	}

}
