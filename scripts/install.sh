# Resources:
# https://docs.github.com/en/repositories/releasing-projects-on-github/linking-to-releases
# https://unix.stackexchange.com/questions/8656/usr-bin-vs-usr-local-bin-on-linux
# https://stackoverflow.com/questions/2953081/how-can-i-write-a-heredoc-to-a-file-in-bash-script

ASSET_PATH="https://github.com/mebble/mcj/releases/latest/download/mcj.jar"
VERSION=$(curl https://github.com/mebble/mcj/releases/latest/ -sI | grep "^location" | awk -F '/' '{print $NF}')
JAR_DIR="$HOME/.local/share/mcj"
EXE_PATH="$HOME/.local/bin/mcj"

echo "Downloading version $VERSION"

curl -sSL $ASSET_PATH --output mcj.jar

# Add stuff to the file system
mkdir -p $JAR_DIR
mv ./mcj.jar $JAR_DIR
cat <<EOF > $EXE_PATH
#!/bin/sh

java -jar $JAR_DIR/mcj.jar "\$@"
EOF

chmod +x $EXE_PATH

echo "Successfully installed"
