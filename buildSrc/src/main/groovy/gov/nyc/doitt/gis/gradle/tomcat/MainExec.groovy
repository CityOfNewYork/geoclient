package gov.nyc.doitt.gis.gradle.tomcat

class MainExec {

  private File war
  private String contextPath
  private int port

  private static String HTTP_PROTOCOL = 'org.apache.coyote.http11.Http11NioProtocol'

  MainExec(war, contextPath, port) {
    this.war = new File(war)
    this.contextPath = contextPath
    this.port = port.toInteger()
  }

  void run() {
    def tomcat = new org.apache.catalina.startup.Tomcat()
  
    // add webapp
    def ctx = tomcat.addWebapp(null, contextPath, war.getCanonicalPath())
    ctx.unpackWAR = false
    // add connector
    tomcat.service.removeConnector(tomcat.getConnector())
    tomcat.service.addConnector(makeConnector())
    // base dir (cwd of forked process)
    tomcat.setBaseDir(".")

    // start!
    tomcat.start()
    println("\nServer is started: http://127.0.0.1:${port}${contextPath}\n")
    tomcat.server.await()
  }

  def makeConnector() {
    def conn = new org.apache.catalina.connector.Connector(HTTP_PROTOCOL)
    conn.setPort(port)
    conn.setURIEncoding('UTF-8')

    conn
  }

  static void main(String[] args) {
    new MainExec(args[0], args[1], args[2]).run();
  }

}
