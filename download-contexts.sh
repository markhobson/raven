#!/bin/bash

OUTPUT_DIR=src/main/resources/contexts

curl -s -L -H 'Accept: application/ld+json' https://www.w3.org/ns/activitystreams >${OUTPUT_DIR}/activitystreams.jsonld
curl -s -L -H 'Accept: application/ld+json' https://w3id.org/security/v1 >${OUTPUT_DIR}/security-v1.jsonld
