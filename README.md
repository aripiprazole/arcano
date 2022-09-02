# arcano

Arcano submission project.

## Documentation

The project's documentation can be found [here](https://gabrielleeg1.github.io/arcano/) in the GitHub Pages.

## Building

You can run the project by using:

```bash
./gradlew run
```

And can set the port and the host by using environment variables like `PORT` and `HOST`.

## Running in the container

You can run the [dockerfile](dockerfile) to run the project. Or you can check all existing tags
at [docker hub](https://hub.docker.com/repository/docker/gabrielleeg1/arcano), and run:

```bash
docker pull gabrielleeg1/arcano:main
docker run --publish 8080:8080 gabrielleeg1/arcano:main
```

## Running tests

You can run the tests of the projects:

```bash
./gradlew check
```

This will run the tests and the linter.
