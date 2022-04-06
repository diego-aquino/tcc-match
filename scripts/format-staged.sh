#!/bin/bash

DIRNAME="$(dirname "$0")"

function main() {
  staged_files=$(git diff --cached --name-only --diff-filter=ACMR)
  "$DIRNAME"/format-java.sh "$staged_files"
}

main
