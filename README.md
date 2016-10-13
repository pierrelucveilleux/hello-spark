READ ME

# To run
./gradle run

This application is push to heroku at http://pluc-says-hello-spark.herokuapp.com

# Heroku useful commands

1. Connect to remote database : ``heroku pg:psql``
2. Pull remote database to a local one : ``heroku pg:pull DATABASE_URL mylocaldb --app pluc-says-hello-spark``
3. To see application logs : ``heroku logs --tail``