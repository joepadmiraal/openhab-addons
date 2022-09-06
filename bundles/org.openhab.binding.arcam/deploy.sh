#!/bin/sh
set -e

mvn package -DskipTests

scp target/org.openhab.binding.arcam-3.3.0.jar openhabian@openhabian:/usr/share/openhab/addons/