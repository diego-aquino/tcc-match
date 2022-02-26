#!/bin/bash

TEMPORARY_DIR="tmp/format"

function main() {
  java_files="$(echo "$@" | grep '.*java$')"

  if [[ -z "$java_files" ]]; then
    echo 'No Java files to format. Skipping.'
    exit 0
  fi

  if [[ ! -d "$TEMPORARY_DIR" ]]; then
    mkdir -p "$TEMPORARY_DIR"
  fi

  files_pairs_to_format=()
  for file in $java_files; do
    if [[ ! -f "$file" ]]; then
      echo "Skipping file as it was not found: $file"
      continue
    fi

    temporary_file="$(save_file_from_index "$file")"
    files_pairs_to_format+=("$temporary_file $file")
  done

  echo 'Formatting staged Java files...'

  files_to_format=""
  for file_pair_index in "${!files_pairs_to_format[@]}"; do
    prefix="$([[ $file_pair_index -gt 0 ]] && printf ',' || printf '')"
    file_pair_as_string="${files_pairs_to_format[$file_pair_index]}"
    read -r -a file_pair <<<"$file_pair_as_string"
    files_to_format+="$prefix${file_pair[0]},${file_pair[1]}"
  done

  format_java "$files_to_format"

  for file_pair_as_string in "${files_pairs_to_format[@]}"; do
    read -r -a file_pair <<<"$file_pair_as_string"
    temporary_file="${file_pair[0]}"
    original_file="${file_pair[1]}"

    update_file_on_index "$original_file" "$temporary_file"
  done

  rm -r "${TEMPORARY_DIR:?}/*"

  echo 'Formatting done.'
}

function save_file_from_index() {
  file="$1"

  mkdir -p "$TEMPORARY_DIR/$(dirname "$file")"
  temporary_file="$TEMPORARY_DIR/$file"
  git show ":$file" >"$temporary_file"

  echo "$temporary_file"
}

function format_java() {
  files_separated_by_commas="$1"
  mvn prettier:write "-Dprettier.inputGlobs=$files_separated_by_commas"
}

function update_file_on_index() {
  original_file="$1"
  new_file="$2"

  hash=$(git hash-object -w "$new_file")

  UPDATE_INDEX_MODE='100644' # replace file hash on index
  git update-index --add --cacheinfo "$UPDATE_INDEX_MODE" "$hash" "$original_file"
}

main "$@"
