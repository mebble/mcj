# mcj

[![Tests Badge](https://github.com/mebble/mcj/actions/workflows/tests.yml/badge.svg)](https://github.com/mebble/mcj/actions/workflows/tests.yml)
[![Relase Badge](https://github.com/mebble/mcj/actions/workflows/release.yml/badge.svg)](https://github.com/mebble/mcj/actions/workflows/release.yml)

A math CLI tool in Clojure.

## Installation

### Install

```
curl --proto '=https' --tlsv1.2 -sSf https://raw.githubusercontent.com/mebble/mcj/main/scripts/install.sh | sh
```

### Remove

```
curl --proto '=https' --tlsv1.2 -sSf https://raw.githubusercontent.com/mebble/mcj/main/scripts/remove.sh | sh
```

## Usage

```
mcj add 2 3 | mcj div . 2
```

## Development

- Scaffolded using `lein new app mcj`
- [Get started with clojure](https://www.braveclojure.com/getting-started/)

### Setup

Run `cp .env.sample .env` and set the appropriate environment variables in `.env`.

### Development Run

```
lein run add 2 3
```

### REPL

```
cd <project-root>
lein repl
```

Now you can access variables in the `mcj.core` namespace. You can also switch to another namespace in the REPL. For example, to switch to `mcj.command`, run `(ns mcj.command)`. To exit the REPL, press `ctrl+d`.

### Test

```
make test
```

### Build

```
make build
```

Run the build with:

```
java -jar target/mcj.jar -h
java -jar target/mcj.jar add 2 3
```

### Test Installation

```
cd ~/.local
watch 'tree -L 2'
watch 'cat bin/mcj'
watch 'file share/mcj/mcj.jar'
sh ./scripts/install.sh
sh ./scripts/remove.sh
```
