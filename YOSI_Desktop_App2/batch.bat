cd D:\Automation_testing\YOSI_Desktop_App2
set ProjectPath=D:\Automation_testing\YOSI_Desktop_App2
echo %ProjectPath%
set classpath=%ProjectPath%\bin;%ProjectPath%\lib\*
echo %classpath%
java org.testng.TestNG %ProjectPath%\testng.xml