#!/usr/bin/groovy
def call() {
    ServerSocket serverSocket
    try {
        serverSocket = new ServerSocket(0)
        int localServerPort = serverSocket.getLocalPort()
        echo("using port: " + localServerPort)
        serverSocket.close()
        return localServerPort
    } catch (IOException ex) {
        echo("could not find a free port ${ex.message}")
        throw ex;
    }
    finally {
        if (serverSocket != null) {
            try {
                serverSocket.close()
            } catch (IOException ignore) {
            }
        }
    }
}