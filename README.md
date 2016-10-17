# READ ME

This a simple application that connect to AppDirect market place.

1. Create subcription
2. Cancel subcription
3. Change subcription
4. Assign user to product
4. Unassign user to product

## Requirements
* Java 8

## Running the tests
* `./gradlew test`

## Running locally
* In your idea with program argument : `--dev=true`
* `./gradlew rundev`
* hit `http://localhost:4567`

## Site
This application is push to heroku at http://pluc-says-hello-spark.herokuapp.com

### Heroku useful commands

1. Connect to remote database : ``heroku pg:psql``
2. Pull remote database to a local one : ``heroku pg:pull DATABASE_URL mylocaldb --app pluc-says-hello-spark``
3. To see application logs : ``heroku logs --tail``