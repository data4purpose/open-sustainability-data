#
# The script is used to collect all release commands during the Apache Wayang 1.0.0 release.
#--------------------------------------------------------------------------------------------

mvn release:clean

#mvn versions:set -DnewVersion=1.0.0-RC # WORKS NOT !!! MUST BE SNAPSHOT
mvn versions:set -DnewVersion=1.0.0-SNAPSHOT
mvn versions:commit

git add .
git commit -m "prepare release 1.0.0 - 6th attempt"

mvn release:prepare -Darguments='-DskipTests=True' -Dresume=true
#mvn release:prepare -DbranchName=rel/1.0.0 -DdevelopmentVersion=1.0.1 -Dtag=releases/1.0.0-RC -Darguments='-DskipTests=True'
#mvn release:prepare -DbranchName=rel/1.0.0 -DdevelopmentVersion=1.0.1 -Dtag=releases/1.0.0-RC -Darguments='-DskipTests=True' -Dresume=false -DargLine="--illegal-access=permit"

=> OPEN

[INFO] 7/17 prepare:map-development-versions
What is the new development version for "Apache Wayang (incubating)"? (wayang) 0.7.2-SNAPSHOT: : 1.0.1-SNAPSHOT
...
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-release-plugin:3.0.1:prepare (default-cli) on project wayang: Unable to tag SCM
[ERROR] Provider message:
[ERROR] The git-tag command failed.
[ERROR] Command output:
[ERROR] fatal: tag 'wayang-0.7.1' already exists

=> During the process of the release:preparation command I observe a reset of the version
   to 0.7.1 in the POM.xml and this finally causes the SCM-TAG step to fail.


#release configuration
#Mon May 13 11:34:23 CEST 2024
projectVersionPolicyId=default
project.rel.org.apache.wayang\:wayang-core=0.7.1
project.scm.org.apache.wayang\:wayang-api-scala-java.empty=true
scm.branchCommitComment=@{prefix} prepare branch @{releaseLabel}
project.dev.org.apache.wayang\:wayang-plugins=1.0.0-SNAPSHOT
project.rel.org.apache.wayang\:wayang-sqlite3=0.7.1
project.rel.org.apache.wayang\:wayang-commons=0.7.1
project.scm.org.apache.wayang\:wayang-benchmark.empty=true
project.scm.org.apache.wayang\:wayang-ml4all.empty=true
project.dev.org.apache.wayang\:wayang=1.0.0-SNAPSHOT
project.dev.org.apache.wayang\:wayang-postgres=1.0.0-SNAPSHOT
project.dev.org.apache.wayang\:wayang-flink=1.0.0-SNAPSHOT
project.rel.org.apache.wayang\:wayang-api-sql=0.7.1
project.scm.org.apache.wayang\:wayang-commons.empty=true
project.rel.org.apache.wayang\:wayang=0.7.1
project.dev.org.apache.wayang\:wayang-basic=1.0.0-SNAPSHOT
project.rel.org.apache.wayang\:wayang-tests-integration=0.7.1
project.dev.org.apache.wayang\:wayang-generic-jdbc=1.0.0-SNAPSHOT
scm.tag=wayang-0.7.1
preparationGoals=clean verify
exec.pomFileName=pom.xml
project.rel.org.apache.wayang\:wayang-benchmark=0.7.1
project.dev.org.apache.wayang\:wayang-sqlite3=1.0.0-SNAPSHOT
project.rel.org.apache.wayang\:wayang-giraph=0.7.1
project.scm.org.apache.wayang\:wayang-resources.empty=true
project.rel.org.apache.wayang\:wayang-spark=0.7.1
project.scm.org.apache.wayang\:wayang-api-python.empty=true
project.rel.org.apache.wayang\:wayang-utils-profile-db=0.7.1
project.rel.org.apache.wayang\:wayang-ml4all=0.7.1
pushChanges=true
project.rel.org.apache.wayang\:wayang-profiler=0.7.1
project.scm.org.apache.wayang\:wayang.url=https\://github.com/apache/incubator-wayang
project.rel.org.apache.wayang\:wayang-jdbc-template=0.7.1
project.scm.org.apache.wayang\:wayang-postgres.empty=true
scm.rollbackCommitComment=@{prefix} rollback the release of @{releaseLabel}
project.dev.org.apache.wayang\:wayang-profiler=1.0.0-SNAPSHOT
project.scm.org.apache.wayang\:wayang-core.empty=true
project.dev.org.apache.wayang\:wayang-giraph=1.0.0-SNAPSHOT
project.dev.org.apache.wayang\:wayang-ml4all=1.0.0-SNAPSHOT
scm.url=scm\:git\:https\://gitbox.apache.org/repos/asf/incubator-wayang.git
scm.developmentCommitComment=@{prefix} prepare for next development iteration
project.dev.org.apache.wayang\:wayang-benchmark=1.0.0-SNAPSHOT
project.dev.org.apache.wayang\:wayang-java=1.0.0-SNAPSHOT
project.rel.org.apache.wayang\:wayang-iejoin=0.7.1
project.rel.org.apache.wayang\:wayang-flink=0.7.1
project.rel.org.apache.wayang\:wayang-generic-jdbc=0.7.1
project.scm.org.apache.wayang\:wayang-api.empty=true
project.rel.org.apache.wayang\:wayang-java=0.7.1
project.scm.org.apache.wayang\:wayang.developerConnection=scm\:git\:https\://gitbox.apache.org/repos/asf/incubator-wayang.git
project.scm.org.apache.wayang\:wayang-jdbc-template.empty=true
project.rel.org.apache.wayang\:wayang-api=0.7.1
project.dev.org.apache.wayang\:wayang-resources=1.0.0-SNAPSHOT
projectVersionPolicyConfig=<projectVersionPolicyConfig>${projectVersionPolicyConfig}</projectVersionPolicyConfig>\n
project.dev.org.apache.wayang\:wayang-utils-profile-db=1.0.0-SNAPSHOT
project.dev.org.apache.wayang\:wayang-iejoin=1.0.0-SNAPSHOT
project.scm.org.apache.wayang\:wayang-profiler.empty=true
project.rel.org.apache.wayang\:wayang-resources=0.7.1
project.dev.org.apache.wayang\:wayang-api-sql=1.0.0-SNAPSHOT
scm.commentPrefix=[maven-release-plugin]
releaseStrategyId=default
project.dev.org.apache.wayang\:wayang-api-python=1.0.0-SNAPSHOT
project.scm.org.apache.wayang\:wayang-assembly.empty=true
project.scm.org.apache.wayang\:wayang-basic.empty=true
project.rel.org.apache.wayang\:wayang-api-python=0.7.1
project.scm.org.apache.wayang\:wayang-plugins.empty=true
autoResolveSnapshots=all
project.scm.org.apache.wayang\:wayang.tag=wayang-0.7.1
project.dev.org.apache.wayang\:wayang-jdbc-template=1.0.0-SNAPSHOT
completedPhase=generate-release-poms
project.scm.org.apache.wayang\:wayang-api-sql.empty=true
project.scm.org.apache.wayang\:wayang-giraph.empty=true
project.rel.org.apache.wayang\:wayang-api-scala-java=0.7.1
project.dev.org.apache.wayang\:wayang-commons=1.0.0-SNAPSHOT
project.scm.org.apache.wayang\:wayang-spark.empty=true
project.rel.org.apache.wayang\:wayang-plugins=0.7.1
project.dev.org.apache.wayang\:wayang-api-scala-java=1.0.0-SNAPSHOT
pinExternals=false
project.scm.org.apache.wayang\:wayang-generic-jdbc.empty=true
project.scm.org.apache.wayang\:wayang-platforms.empty=true
project.scm.org.apache.wayang\:wayang-java.empty=true
project.dev.org.apache.wayang\:wayang-spark=1.0.0-SNAPSHOT
project.scm.org.apache.wayang\:wayang-utils-profile-db.empty=true
project.scm.org.apache.wayang\:wayang-sqlite3.empty=true
project.rel.org.apache.wayang\:wayang-postgres=0.7.1
project.rel.org.apache.wayang\:wayang-assembly=0.7.1
project.scm.org.apache.wayang\:wayang-tests-integration.empty=true
remoteTagging=true
project.dev.org.apache.wayang\:wayang-assembly=1.0.0-SNAPSHOT
project.dev.org.apache.wayang\:wayang-tests-integration=1.0.0-SNAPSHOT
project.scm.org.apache.wayang\:wayang-flink.empty=true
project.dev.org.apache.wayang\:wayang-platforms=1.0.0-SNAPSHOT
project.scm.org.apache.wayang\:wayang-iejoin.empty=true
project.rel.org.apache.wayang\:wayang-basic=0.7.1
scm.tagNameFormat=v1.0.0-SNAPSHOT
exec.additionalArguments=-DskipTests\=True
project.dev.org.apache.wayang\:wayang-api=1.0.0-SNAPSHOT
project.dev.org.apache.wayang\:wayang-core=1.0.0-SNAPSHOT
project.rel.org.apache.wayang\:wayang-platforms=0.7.1
exec.snapshotReleasePluginAllowed=false
project.scm.org.apache.wayang\:wayang.connection=scm\:git\:https\://gitbox.apache.org/repos/asf/incubator-wayang.git
scm.releaseCommitComment=@{prefix} prepare release 0.7.1



Observations:
=============

[1]
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-release-plugin:3.0.1:prepare (default-cli) on project wayang: You don't have a SNAPSHOT project in the reactor projects list. -> [Help 1]
(SOLVED) => I have to set the version to a ....-SNAPSHOT in all POM files (using mvn versions:set)

[2]
[ERROR] WARNING: An illegal reflective access operation has occurred
[ERROR] WARNING: Illegal reflective access by org.codehaus.groovy.reflection.CachedClass (file:/Users/mkaempf/.m2/repository/org/codehaus/groovy/groovy-all/2.4.9/groovy-all-2.4.9.jar) to method java.lang.Object.finalize()
[ERROR] WARNING: Please consider reporting this to the maintainers of org.codehaus.groovy.reflection.CachedClass
[ERROR] WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
[ERROR] WARNING: All illegal access operations will be denied in a future release
(SOLVED) => can be handled with an CLI argument ... -DargLine="--illegal-access=permit"



