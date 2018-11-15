cd C:\Users\Dreams\Jenkins\workspace\YC_Desktop\YOSI_Desktop_App2
set ProjectPath=C:\Users\Dreams\Jenkins\workspace\YC_Desktop\YOSI_Desktop_App2
echo %ProjectPath%
set classpath=%ProjectPath%\bin;%ProjectPath%\lib\*
echo %classpath%
java org.testng.TestNG %ProjectPath%\testng.xml