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
