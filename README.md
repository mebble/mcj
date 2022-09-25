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

1. Run `cp profiles.example.clj profiles.clj` and set the appropriate environment variable values in `profiles.clj`

### Development Run

```
lein run add 2 3
```

### Test

```
lein eftest
```

### Build

First export the environment variables in the shell so they can be picked up at build time. For each variable found in `profiles.clj`, the corresponding variable that should be exported in the shell must be in uppercase and replace `-` with `_`. For example, for `:app-version`, you must export `APP_VERSION` in the shell.

Then build the app:

```
lein uberjar
```

### Test Installation

```
cd ~/.local
watch 'tree -L 2'
watch 'cat bin/mcj'
watch 'file share/mcj/mcj.jar'
```
