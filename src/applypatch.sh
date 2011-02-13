#!/bin/bash
#cp -r minecraft_original/* minecraft
#sed "/^--- \\|^+++ / s#\\\\#/#g" mc.patch | patch -p0 -F3
wineconsole applypatch.bat
