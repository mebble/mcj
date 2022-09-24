# Resources:
# https://docs.github.com/en/repositories/releasing-projects-on-github/linking-to-releases
# https://stackoverflow.com/a/65520268/5811761
# https://unix.stackexchange.com/questions/8656/usr-bin-vs-usr-local-bin-on-linux

ASSET_PATH="https://github.com/mebble/mcj/releases/latest/download/mcj.jar"
JAR_DIR="$HOME/.local/share/mcj"
EXE_PATH="$HOME/.local/bin/mcj"

curl -L $ASSET_PATH --output mcj.jar

# Add stuff to the file system
mkdir -p $JAR_DIR
mv ./mcj.jar $JAR_DIR
tee $EXE_PATH <<EOF
#!/bin/sh

java -jar $JAR_DIR/mcj.jar "\$@"
EOF

chmod +x $EXE_PATH
