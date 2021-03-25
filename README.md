# AidlConnnection
## 提醒
* 在aidl包下进行接口方法的增删后，最好进行rebuild
* build.gradle下 在android域内加上 
  sourceSets{
        main{
            java.srcDirs = ['src/main/java','src/main/aidl']
        }
    }
    确保能够识别到aidl包下的java文件
