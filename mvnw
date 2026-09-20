#!/bin/sh
set -eu
MAVEN_VERSION=3.9.11
BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
MAVEN_DIR="$BASE_DIR/.mvn/apache-maven-$MAVEN_VERSION"
ARCHIVE="$BASE_DIR/.mvn/apache-maven-$MAVEN_VERSION-bin.tar.gz"
if [ ! -x "$MAVEN_DIR/bin/mvn" ]; then
  mkdir -p "$BASE_DIR/.mvn"
  curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$MAVEN_VERSION/apache-maven-$MAVEN_VERSION-bin.tar.gz" -o "$ARCHIVE"
  tar -xzf "$ARCHIVE" -C "$BASE_DIR/.mvn"
  rm -f "$ARCHIVE"
fi
JAVA_HOME=${JAVA_HOME:-/opt/homebrew/opt/openjdk@21}
export JAVA_HOME
exec "$MAVEN_DIR/bin/mvn" "$@"

