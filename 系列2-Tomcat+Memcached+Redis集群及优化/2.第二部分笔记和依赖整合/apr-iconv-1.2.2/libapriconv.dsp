# Microsoft Developer Studio Project File - Name="libapriconv" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

CFG=libapriconv - Win32 Release
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "libapriconv.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "libapriconv.mak" CFG="libapriconv - Win32 Release"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "libapriconv - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "libapriconv - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "libapriconv - x64 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "libapriconv - x64 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "libapriconv - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MD /W3 /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /FD /c
# ADD CPP /nologo /MD /W3 /Zi /O2 /Oy- /I "./include" /I "../apr/include" /D "NDEBUG" /D "API_DECLARE_EXPORT" /D "WIN32" /D "_WINDOWS" /Yu"iconv.h" /Fo"$(INTDIR)\" /Fd"$(INTDIR)\libapriconv_src" /FD /c
# ADD BASE MTL /nologo /D "NDEBUG" /mktyplib203 /o /win32 "NUL"
# ADD MTL /nologo /D "NDEBUG" /mktyplib203 /o /win32 "NUL"
# ADD BASE RSC /l 0x409 /d "NDEBUG"
# ADD RSC /l 0x409 /d "NDEBUG" /d "API_VERSION_ONLY" /I "./include" /I "../apr/include"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug /opt:ref
# ADD LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug /out:"Release\libapriconv-1.dll" /pdb:"Release\libapriconv-1.pdb" /implib:"Release\libapriconv-1.lib" /MACHINE:X86 /opt:ref
# Begin Special Build Tool
TargetPath=Release\libapriconv-1.dll
SOURCE="$(InputPath)"
PostBuild_Desc=Embed .manifest
PostBuild_Cmds=if exist $(TargetPath).manifest mt.exe -manifest $(TargetPath).manifest -outputresource:$(TargetPath);2
# End Special Build Tool

!ELSEIF  "$(CFG)" == "libapriconv - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MDd /W3 /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /FD /EHsc /c
# ADD CPP /nologo /MDd /W3 /Zi /Od /I "./include" /I "../apr/include" /D "_DEBUG" /D "API_DECLARE_EXPORT" /D "WIN32" /D "_WINDOWS" /Yu"iconv.h" /Fo"$(INTDIR)\" /Fd"$(INTDIR)\libapriconv_src" /FD /EHsc /c
# ADD BASE MTL /nologo /D "_DEBUG" /mktyplib203 /o /win32 "NUL"
# ADD MTL /nologo /D "_DEBUG" /mktyplib203 /o /win32 "NUL"
# ADD BASE RSC /l 0x409 /d "_DEBUG"
# ADD RSC /l 0x409 /d "_DEBUG" /d "API_VERSION_ONLY" /I "./include" /I "../apr/include"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug
# ADD LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug /out:".\Debug\libapriconv-1.dll" /pdb:"Debug\libapriconv-1.pdb" /implib:"Debug\libapriconv-1.lib" /MACHINE:X86
# Begin Special Build Tool
TargetPath=Debug\libapriconv-1.dll
SOURCE="$(InputPath)"
PostBuild_Desc=Embed .manifest
PostBuild_Cmds=if exist $(TargetPath).manifest mt.exe -manifest $(TargetPath).manifest -outputresource:$(TargetPath);2
# End Special Build Tool

!ELSEIF  "$(CFG)" == "libapriconv - x64 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "x64\Release"
# PROP BASE Intermediate_Dir "x64\Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "x64\Release"
# PROP Intermediate_Dir "x64\Release"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MD /W3 /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /FD /c
# ADD CPP /nologo /MD /W3 /Zi /O2 /Oy- /I "./include" /I "../apr/include" /D "NDEBUG" /D "API_DECLARE_EXPORT" /D "WIN32" /D "_WINDOWS" /Yu"iconv.h" /Fo"$(INTDIR)\" /Fd"$(INTDIR)\libapriconv_src" /FD /c
# ADD BASE MTL /nologo /D "NDEBUG" /mktyplib203 /o /win32 "NUL"
# ADD MTL /nologo /D "NDEBUG" /mktyplib203 /o /win32 "NUL"
# ADD BASE RSC /l 0x409 /d "NDEBUG"
# ADD RSC /l 0x409 /d "NDEBUG" /d "API_VERSION_ONLY" /I "./include" /I "../apr/include"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug /opt:ref
# ADD LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug /out:"x64\Release\libapriconv-1.dll" /pdb:"x64\Release\libapriconv-1.pdb" /implib:"x64\Release\libapriconv-1.lib" /MACHINE:X64 /opt:ref
# Begin Special Build Tool
TargetPath=x64\Release\libapriconv-1.dll
SOURCE="$(InputPath)"
PostBuild_Desc=Embed .manifest
PostBuild_Cmds=if exist $(TargetPath).manifest mt.exe -manifest $(TargetPath).manifest -outputresource:$(TargetPath);2
# End Special Build Tool

!ELSEIF  "$(CFG)" == "libapriconv - x64 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "x64\Debug"
# PROP BASE Intermediate_Dir "x64\Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "x64\Debug"
# PROP Intermediate_Dir "x64\Debug"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MDd /W3 /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /FD /EHsc /c
# ADD CPP /nologo /MDd /W3 /Zi /Od /I "./include" /I "../apr/include" /D "_DEBUG" /D "API_DECLARE_EXPORT" /D "WIN32" /D "_WINDOWS" /Yu"iconv.h" /Fo"$(INTDIR)\" /Fd"$(INTDIR)\libapriconv_src" /FD /EHsc /c
# ADD BASE MTL /nologo /D "_DEBUG" /mktyplib203 /o /win32 "NUL"
# ADD MTL /nologo /D "_DEBUG" /mktyplib203 /o /win32 "NUL"
# ADD BASE RSC /l 0x409 /d "_DEBUG"
# ADD RSC /l 0x409 /d "_DEBUG" /d "API_VERSION_ONLY" /I "./include" /I "../apr/include"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug
# ADD LINK32 kernel32.lib advapi32.lib /nologo /base:"0x6EE50000" /subsystem:windows /dll /incremental:no /debug /out:"x64\Debug\libapriconv-1.dll" /pdb:"x64\Debug\libapriconv-1.pdb" /implib:"x64\Debug\libapriconv-1.lib" /MACHINE:X64
# Begin Special Build Tool
TargetPath=x64\Debug\libapriconv-1.dll
SOURCE="$(InputPath)"
PostBuild_Desc=Embed .manifest
PostBuild_Cmds=if exist $(TargetPath).manifest mt.exe -manifest $(TargetPath).manifest -outputresource:$(TargetPath);2
# End Special Build Tool

!ENDIF 

# Begin Target

# Name "libapriconv - Win32 Release"
# Name "libapriconv - Win32 Debug"
# Name "libapriconv - x64 Release"
# Name "libapriconv - x64 Debug"
# Begin Group "Source Files"

# PROP Default_Filter ".c"
# Begin Group "lib"

# PROP Default_Filter ""
# Begin Source File

SOURCE=.\lib\iconv.c
# ADD CPP /Yc"iconv.h"
# End Source File
# Begin Source File

SOURCE=.\lib\iconv_ces.c
# End Source File
# Begin Source File

SOURCE=.\lib\iconv_ces_euc.c
# End Source File
# Begin Source File

SOURCE=.\lib\iconv_ces_iso2022.c
# End Source File
# Begin Source File

SOURCE=.\lib\iconv_int.c
# End Source File
# Begin Source File

SOURCE=.\lib\iconv_module.c
# End Source File
# Begin Source File

SOURCE=.\lib\iconv_uc.c
# End Source File
# End Group
# End Group
# Begin Group "Header Files"

# PROP Default_Filter ".h"
# Begin Source File

SOURCE=.\include\api_version.h
# End Source File
# Begin Source File

SOURCE=.\include\apr_iconv.h
# End Source File
# Begin Source File

SOURCE=.\lib\charset_alias.h
# End Source File
# Begin Source File

SOURCE=.\lib\iconv.h
# End Source File
# End Group
# Begin Source File

SOURCE=.\libapriconv.rc
# End Source File
# End Target
# End Project
