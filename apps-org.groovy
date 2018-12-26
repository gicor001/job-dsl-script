githubOrg = "Gil-org"
credId = "github"
apiUri = "https://api.github.com"
orgPipelineRepo = "https://github.com/Gil-org/pipeline-config.git"
libraryRepo = "https://github.com/Gil-org/SDP-Library.git"

organizationFolder githubOrg, {
  configure{
    it / 'properties' << 'org.boozallen.plugins.jte.config.TemplateConfigFolderProperty' {
        tier {
            baseDir('./resources/sdp-org')
            scm (class: 'hudson.plugins.git.GitSCM'){
                configVersion 2
                userRemoteConfigs{
                  'hudson.plugins.git.UserRemoteConfig' {
                    url orgPipelineRepo
                    credentialsId credId
                  }
                }
                branches{
                  'hudson.plugins.git.BranchSpec'{
                    name "*/master"
                  }
                }
      		}
            librarySources{
                'org.boozallen.plugins.jte.config.TemplateLibrarySource'{
                    scm (class: 'hudson.plugins.git.GitSCM'){
                        configVersion 2
                        userRemoteConfigs{
                          'hudson.plugins.git.UserRemoteConfig' {
                            url libraryRepo
                            credentialsId credId
                          }
                        }
                        branches{
                          'hudson.plugins.git.BranchSpec'{
                            name "*/master"
                          }
                        }
      				      }
                }
            }
        }
    }
    it / 'navigators' << 'org.jenkinsci.plugins.github__branch__source.GitHubSCMNavigator'{
      repoOwner githubOrg
      scanCredentialsId credId
      checkoutCredentialsId 'SAME'
      apiUri apiUri
      buildOriginBranch true
      buildOriginBranchWithPR false
      buildOriginPRMerge true
      buildOriginPRHead false
      buildForkPRMerge false
      buildForkPRHead false
    }
    it / 'projectFactories' << 'org.boozallen.plugins.jte.job.TemplateMultiBranchProjectFactory' {
    }
  }
}
