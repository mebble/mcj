# mcj

[![Tests Badge](https://github.com/mebble/mcj/actions/workflows/tests.yml/badge.svg)](https://github.com/mebble/mcj/actions/workflows/tests.yml)
[![Relase Badge](https://github.com/mebble/mcj/actions/workflows/release.yml/badge.svg)](https://github.com/mebble/mcj/actions/workflows/release.yml)

A math CLI tool in Clojure.

## Usage

FIXME: explanation

    $ java -jar mcj-0.1.0-standalone.jar [args]

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

### Test scripts

```
cd ~/.local
watch 'tree -L 2'
watch 'cat bin/mcj'
watch 'file share/mcj/mcj.jar'
```

