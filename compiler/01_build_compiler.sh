#!/bin/bash

find src/ -iname '*.java' | xargs javac -d bin
