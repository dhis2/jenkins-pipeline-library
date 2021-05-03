def startCluster( name, port, credentials ) {
    sh "d2 cluster --channel dev --port ${port} up --update ${name}"
    
    wait("$credentials", "$port")

}

def startClusterAndSeed( name, port, credentials ) {
    sh "d2 cluster --channel dev --port ${port} up --update ${name} --seed"
    wait("$credentials", "$port")
}

def wait(credentials, port) {
   def response = "not_started"
    
    while (response != "200") {
        echo "status: $response"
        sleep(5) 
        response = ["curl", "-k", "-X", "GET", "-w", "%{http_code}", "--silent", "-o /dev/null", "-u", "${credentials}", "http://localhost:${port}/api/system/info"].execute().text
    }
}