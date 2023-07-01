./gradlew bootJar
scp -i ~/key/hoodie-aws.pem ./build/libs/hamoney-server-*-SNAPSHOT.jar ubuntu@dev.hamoney.life:~/happiness.jar