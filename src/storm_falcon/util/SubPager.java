package storm_falcon.util;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SubPager {

	private SubPager() {}
	
	/**
	 * @param size 每页数量
	 * @param total 总数
	 * @return
	 */
	public static int[] getPages(int size, int total) {
		int totalPage = total / size + total % size;
		return IntStream.range(1, totalPage).toArray();
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(getPages(10, 101)));
	}
}
