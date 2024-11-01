echo "
+----------------------------------------------------------------------
|                   冒险岛079 FOR CentOS/Ubuntu/Debian
+----------------------------------------------------------------------
"
JVM_PARAM="-DhomePath=./config/ -DscriptsPath=./scripts/ -DwzPath=./scripts/wz"
JVM_PARAM="$JVM_PARAM -Dfile.encoding=UTF-8"
CLASS_PATH="target/maplestory079-1.0.jar:/Users/Amber/.m2/repository/org/apache/mina/mina-core/2.2.3/mina-core-2.2.3.jar:/Users/Amber/.m2/repository/org/slf4j/slf4j-api/2.0.16/slf4j-api-2.0.16.jar:/Users/Amber/.m2/repository/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar:/Users/Amber/.m2/repository/com/google/protobuf/protobuf-java/3.25.1/protobuf-java-3.25.1.jar:/Users/Amber/.m2/repository/org/openjdk/nashorn/nashorn-core/15.4/nashorn-core-15.4.jar:/Users/Amber/.m2/repository/org/ow2/asm/asm/7.3.1/asm-7.3.1.jar:/Users/Amber/.m2/repository/org/ow2/asm/asm-commons/7.3.1/asm-commons-7.3.1.jar:/Users/Amber/.m2/repository/org/ow2/asm/asm-analysis/7.3.1/asm-analysis-7.3.1.jar:/Users/Amber/.m2/repository/org/ow2/asm/asm-tree/7.3.1/asm-tree-7.3.1.jar:/Users/Amber/.m2/repository/org/ow2/asm/asm-util/7.3.1/asm-util-7.3.1.jar:/Users/Amber/.m2/repository/org/apache/logging/log4j/log4j-slf4j2-impl/2.24.1/log4j-slf4j2-impl-2.24.1.jar:/Users/Amber/.m2/repository/org/apache/logging/log4j/log4j-api/2.24.1/log4j-api-2.24.1.jar:/Users/Amber/.m2/repository/org/apache/logging/log4j/log4j-core/2.24.1/log4j-core-2.24.1.jar"

java -cp $CLASS_PATH $JVM_PARAM -server -Xms512m -Xmx2048m -XX:MaxNewSize=512m server.Start
