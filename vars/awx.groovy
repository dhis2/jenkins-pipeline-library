import groovy.json.*

def resetWar(credentials, host, name) {
  def json = JsonOutput.toJson(
    [extra_vars: [
      instance_host: [host], 
      instance_name: name, 
      instance_action: "reset_war"
    ]]
  )
	def response = ["curl", "-u", credentials, "-k", "-X", "POST", "-H", "Content-Type: application/json", "-d", "${json}", "https://awx.dhis2.org/api/v2/job_templates/10/launch/"].execute().text
  def job_id = new JsonSlurper().parseText(response).get("job")
  def status="not_started"

  while (status != "successful") {
    echo "$status"
    sleep(2) 
    def job_response=["curl", "-u", credentials, "-X", "GET", "-s", "https://awx.dhis2.org/api/v2/jobs/$job_id/"].execute().text
           
    status = new JsonSlurper().parseText(job_response).get("status")

    if(status=='failed') {
      sh(
        returnStatus: true,
        script: "exit 0",
        returnStdout: true
      )
    }
  }
}
