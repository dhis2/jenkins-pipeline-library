def call(isTag) {
  def branch_name = env.BRANCH_NAME
  def job_path = env.JENKINS_HOME/jobs/${getJobName()}
  def branch_path = job_path + "/branches/"
  if (isTag) {
    def replaced_name = branch_name.replace(".", "-");
    def tag_folder = sh (
      script: "find $branch_path -name $replaced_name*",
      returnStdout: true
    ).trim()
    branch_path = tag_folder;
  }

  isTag ? branch_path : "$branch_path/$branch_name"
}