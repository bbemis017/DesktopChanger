@echo off


reg add "HKCU\Control Panel\Desktop" /v Wallpaper /f /t REG_SZ /d "%~dp0day.bmp"

Rundll32.exe user32.dll,UpdatePerUserSystemParameters

