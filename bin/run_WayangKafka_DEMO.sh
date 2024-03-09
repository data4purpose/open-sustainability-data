cd ../wayang-test-01

source ./../bin/env.sh; mvn clean compile exec:java -Dexec.mainClass="KafkaTopicWordCount"
