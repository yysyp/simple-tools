command:
mvn clean test -pl sub-abc -Dspring.profiles.active=dev -Dpass=123


In intelliIDE:

Open the entry class which has @Cucumber annotation
Right click choose "Modify Run Configuration"
in the VM options to add your vm variables before the "-ea" like: -Dmongo_user=test -ea
in the Environment variables add the Env variables like: spring.profiles.active=dev



