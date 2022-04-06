#!/bin/bash

function main() {
  echo 'Intalling pre-commit hooks...'
  save_pre_commit_hooks
  echo 'Installation complete.'
}

function save_pre_commit_hooks() {
  PRE_COMMIT_FILE='.git/hooks/pre-commit'
  printf '#!/bin/bash\n\nset -e\n\n./scripts/format-staged.sh\n' >"$PRE_COMMIT_FILE"
  chmod 700 "$PRE_COMMIT_FILE"
}

main
