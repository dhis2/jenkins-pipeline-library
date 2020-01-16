def resetWar(credentials, host, name) {
  def json = "{\"extra_vars\":{\"instance_host\":\"$host\",\"instance_name\": \"$name\",\"instance_action\": \"reset_war\"}}"
	def response = ["curl", "-u", credentials, "-k", "-X", "POST", "-H", "Content-Type: application/json", "-d", "${json}", "https://awx.dhis2.org/api/v2/job_templates/10/launch/"].execute().text
  echo "$response"

  def job_id=sh(
    script: "echo $response | python -c 'import sys, json; print json.load(sys.stdin)[\"job\"]' ",
    returnStdout: true
  )

  def status="not_started"

  while (status != "successful") {
    echo "$status"
    sleep(10) 
    def job_response=["curl", "-u", "$credentials", "-X", "GET", "-s", "https://awx.dhis2.org/api/v2/jobs/$job_id/"].execute().text
           
    status=sh(
      script: "echo $job_response | python -c 'import sys, json; print json.load(sys.stdin)[\"status\"]'",
      returnStdout: true
    )

    if(status=='failed') {
      System.exit(0)
    }
  }
}
