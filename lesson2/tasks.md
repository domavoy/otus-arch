+ send email and sms viq amq
- save and return status for sms/email

- research: pojo with json generation
- P1
- add send confirmation with JMSMessageID (central => email => central)
- amq location to config(sms/push/email)

- listeners - unit tests with local amq
- research: remove embedded amq from central and start it on docker-compose(+hawtio)
- clean pom.xml - minimal rest

- docs
- docker


### Formats
#### email
{
  "to":"to",
  "title":"title",
  "message":"message"
}