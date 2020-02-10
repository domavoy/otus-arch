#bin/bash
java -jar -Dspring.profiles.active=db-h2mem auth-service.jar & \
java -jar -Dspring.profiles.active=db-h2mem finance-service.jar & \
java -jar -Dspring.profiles.active=db-h2mem budget-service.jar & \
java -jar -Dspring.profiles.active=db-h2mem repeated-payment-service.jar & \
java -jar -Dspring.profiles.active=db-h2mem scheduler-service.jar