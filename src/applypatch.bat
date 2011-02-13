@echo off
xcopy "minecraft_original" "minecraft" /e /y
patch.exe -F3 -p1 -i mc.patch
pause
