def call(isTag) {
  def branch_name = env.BRANCH_NAME
  def job_path = env.JENKINS_HOME + "/jobs/${getJobName()}"
  def branch_folder_path = job_path + "/branches"
  def branch_path = "$branch_folder_path/$branch_name"
  if (isTag) {
    def replaced_name = branch_name.replace(".", "-");
    def tag_folder = sh (
      script: "find $branch_folder_path -name $replaced_name*",
      returnStdout: true
    ).trim()
    branch_path = tag_folder;
  }
  echo "getBranchPath() found a path for branch $branch_name: $branch_path"
  branch_path
}