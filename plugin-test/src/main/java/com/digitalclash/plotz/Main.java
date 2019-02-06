package com.digitalclash.plotz;

public class Main {

    public Main() {
        super();
    }

    public String getSystemProperty(String name) {
        return System.getProperty(name, "null");
    }

    public String getEnvironmentVariable(String name) {
        return System.getenv(name);
    }

    public static void main(String[] args) {
        Main instance = new Main();
        System.out.println("System Properties");
        instance.getSystemProperty("java.io.tmpdir");
        System.out.println("Environment Variables");
        instance.getEnvironmentVariable("JAVA_HOME");
    }
}
