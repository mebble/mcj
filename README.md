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

### Test

```
lein eftest
```

### Build

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

