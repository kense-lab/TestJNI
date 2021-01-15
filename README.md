JNI 使用教程
===

编写 Java 测试类
---
```java
public class TestJNI {

    // 定义 native 方法
    private native void say();

    static {
        // 加载动态库
        // 从当前路径或 java.library.path 查找, 去掉文件扩展名, macos 需要去掉 lib 前缀
        System.loadLibrary("TestJNI");

        // 或者使用文件绝对路径加载
        // System.load( "/path/to/TestJNI.so");
    }

    public static void main(String[] args) {
        new TestJNI().say();
    }
}
```

利用 Java 类生成 C++ 头文件
---
```bash
javah TestJNI
```

修改 `TestJNI.h` 中 `#include <jni.h>` -> `#include "jni.h"`
这样会优先从当前目录寻找 `jni.h` 头文件

编写 `TestJNI.cpp` 文件
---
```cpp
#include "TestJNI.h"

JNIEXPORT void JNICALL Java_TestJNI_say (JNIEnv *env, jobject obj) {
    printf("hello world");
}
```

编译 C++ 代码, 生成动态链接库
---
1. 拷贝依赖的 JNI 相关头文件到当前目录, 或者使用 gcc 编译时指定参数 `-I $JAVA_HOME/include -I $JAVA_HOME/include/darwin`
    ```bash
    cp $JAVA_HOME/include/jni.h .
    cp $JAVA_HOME/include/darwin/jni_md.h .
    ```

2. 编译
    * Linux
    ```bash
    gcc -shared TestJNI.cpp -o TestJNI.so
    ```

    * MacOS
    ```bash
    gcc -dynamiclib TestJNI.cpp -o libTestJNI.dylib
    ```

    * Windows
    ```bash
    gcc -shared TestJNI.cpp -o TestJNI.dll
    ```

编译运行 Java Class
---
```bash
javac TestJNI.java
java TestJNI
```

附
---
如何查看 `java.library.path` 地址
```java
public class PrintJavaLibPath {

    public static void main(String[] args) {
        String[] split = System.getProperty("java.library.path").split(":");
        for (String string : split) {
            System.out.println(string);
        }
    }
}
```

参考
---
* [Java深入JVM源码核心探秘Unsafe(含JNI完整使用流程)](https://blog.csdn.net/huangzhilin2015/article/details/101158137)
* [Java - 如何使用dylib文件？](https://www.coder.work/article/1417794)
* [Java加载dll或so库文件的路径 java.library.path](https://blog.csdn.net/daylight_1/article/details/70199452)
