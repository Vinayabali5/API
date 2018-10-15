CID API
=======

The current way that the API application works is by providing a set of HTTP endpoint that can be used by other services to retrieve and save data to the backend database. This project relies on the CIS-Data project to operate and must be incluided as a build dependency (in build.gradle). 

The CID API application must be run with a profile or customised application.yml file. The current set of profiles available on the CID Template project are dev, prod however these will not work without an application.yml files that specifies the required settings:

## Required Settings:

The following snippet details all the settings that are required for the CID API application to operate:

Example application.yml:

```
spring: 
  profiles: 
    active: { NAME OF PROFILE } (typically: dev or prod)
  datasource:
    url: jdbc:sqlserver://{ SERVER HOST NAME OR IP ADDRESS }\{ SQL INSTANCE NAME OR OMIT IF DEFAULT };databaseName={ SQL DATABASE NAME }
    username: { SQL USERNAME }
    password: { SQL PASSWORD }

ldap:
  enabled: true 
  url: ldap://{ LDAP SERVER HOST NAME OR IP ADDRESS}
  port: { LDAP SERVER PORT } (Default: 389)
  bindUser: { LDAP BIND UESR DN } ( e.g. CN=ldap-user,CN=Users,DC=example,DC=co,DC=uk
  bindPassword: { LDAP BIND USER PASSWORD }
  userSearchBase: { LDAP USER SEARCH BASE  } (e.g. OU=Users,DC=example,DC=co,DC=uk
  userSearchFilter: (sAMAccountName={0})
  
postcode: 
  lookup:
      key: { POSTCODE ANYWHERE API KEY }
      username: { POSTCODE ANYWHERE USERNAME }
```


## Development Profile (dev):

The development profile will load the server onto port: 9001

## Production Profile (prod):

The production profile will load the server onto port: 9009

