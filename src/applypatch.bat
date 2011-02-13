@echo off
del /f /s /q minecraft 
xcopy "minecraft_original" "minecraft" /e
patch.exe -p1 -i mc.patch
pause