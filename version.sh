#!/usr/bin/env bash

echo --- Prepare to refresh the spring-project-infras project version ---

mvn versions:set -DprocessAllModules=true -DgenerateBackupPoms=false -DnewVersion="$1"

mvn versions:update-child-modules
mvn versions:commit