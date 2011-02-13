#!/bin/bash
cp -r minecraft_original/* minecraft
patch -p1 < mc.patch
