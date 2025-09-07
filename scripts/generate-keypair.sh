#!/bin/bash

set -e

rm -f ./keys/*.pem

openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -out ./keys/private.pem
openssl rsa -in ./keys/private.pem -pubout -out ./keys/public.pem
