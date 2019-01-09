# Pal Tracker (January 2019)

This is a sample application built to facilitate learning PCF / CloudFoundary.

## Learning Materials

* [PAL Cloud Native Developer Program Overview](https://courses.education.pivotal.io/c/349802825/) - Pivotal account required
* [12 Factor Applications](https://12factor.net/)
* [Diego Design Notes](https://github.com/cloudfoundry/diego-design-notes)
* [Diego: Re-envisioning the Elastic Runtime][https://www.youtube.com/watch?v=1OkmVTFhfLY]
* [Martin Fowler's CI Certification](https://martinfowler.com/bliki/ContinuousIntegrationCertification.html)
* [Evolution of a cloud native architecture](http://www.appcontinuum.io/)
* [Flyway Docs](https://flywaydb.org/documentation/) and [Versioning Migrations](https://flywaydb.org/documentation/migrations#versioned-migrations)
* [Spring JDBC Template docs](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html)
* [Spring JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
* [Configure Java Apps for Local Development](https://cloudnative.tips/configuring-a-java-application-for-local-development-60e2c9794ca7)


## Useful CF Commands

Add `--help` for docs on any command.

Login:

```bash
cf login -a ${CF_API_ENDPOINT}
```

Get available CF Services, create a service, bind a service:

```bash
cf marketplace
cf create-service ${SERVICE} ${PLAN} ${SERVICE_INSTANCE}
cf bind-service ${APP_NAME} ${SERVICE_INSTANCE} # Service info added to VCAP_SERVICES env var
```

Delete apps and services.

```bash
cf delete ${APP_NAME}
cf delete-service ${SERVICE_NAME}
```

Push your app jar:

```bash
cf push -p ${JAR_PATH} # use --random-route to get a random route
```

Get app health status:

```bash
cf app ${APP_NAME}
```

Get recent logs:

```bash
cf logs --recent
```

Set an environment variable:

```bash
cf set-env ${APP_NAME} ${ENV_VAR_NAME} ${ENV_VAR_VALUE}
```

Restart, restage app container:

```bash
cf restart ${APP_NAME} # stops and starts container
cf restage ${APP_NAME} # rebuilds container (droplet)
```

Scale your app:

```bash
cf scale APP -i ${INSTANCES} -k ${DISK_SPACE} -m ${MEMORY}
```



## Handy CF Environment Variables

```
    PORT
    MEMORY_LIMIT
    CF_INSTANCE_INDEX
    CF_INSTANCE_ADDR
```


Travis CI: https://travis-ci.org/jamestharpe/pal-tracker/settings
