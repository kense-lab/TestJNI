public class PrintJavaLibPath {

	public static void main(String[] args) {
		String[] split = System.getProperty("java.library.path").split(":");
		for (String string : split) {
			System.out.println(string);
		}
	}
}
