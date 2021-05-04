
def startCluster( name, port, channel, credentials ) {
  sh "d2 cluster --channel ${channel} --port ${port} up --update ${name}"
  
  wait("$credentials", "$port")
}

def startClusterAndSeed( name, port, channel, credentials ) {
    sh "d2 cluster --channel ${channel} --port ${port} up --update ${name} --seed"
    
    wait("$credentials", "$port")
}

def stopCluster( name ) {
    sh "d2 cluster down --clean ${name}"
}

def wait(credentials, port) {
   def response = "not_started"
    
    while (response != "200") {
        echo "status: $response"
        sleep(5) 
        response = ["curl", "-k", "-X", "GET", "-w", "%{http_code}", "--silent", "-o /dev/null", "-u", "${credentials}", "http://localhost:${port}/api/system/info"].execute().text
    }
}