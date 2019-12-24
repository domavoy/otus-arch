###1) Email
- refactor packages: sender
- put request to queue(up) + add type
- getStatus from central
 
###2) Central
+ start amq + hawtio
- process jms
- process status
- https://itnext.io/event-driven-microservices-with-spring-boot-and-activemq-5ef709928482

###3) SMS
- as email service
- refactor packages: sender
- put request to queue(up) + add type 

###NEXT
- research: remove embedded amq from central and start it on docker-compose(+hawtio)
- research: pojo with json generation
- P1
- add send confirmation with JMSMessageID (central => email => central)

- amq location to config(sms/push/email)
- listeners - unit tests with local amq
- clean pom.xml - minimal rest



### Formats
#### email
{
  "to":"to",
  "title":"title",
  "message":"message"
}