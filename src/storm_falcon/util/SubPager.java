package storm_falcon.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubPager {

	private SubPager() {}
	
	/**
	 * @param size 每页数量
	 * @param total 总数
	 * @return
	 */
	public static int[] getPages(int size, int total) {
		int totalPage = total / size + (total % size == 0 ? 0 : 1);
		return IntStream.range(0, totalPage).map(x -> x + 1).toArray();
	}
	
	public static <T> List<T> subList(List<T> list, int page, int size) {
		int from = (page - 1) * size;
		int to = from + size;
		
		if (to > list.size()) {
			to = list.size();
		}
		return list.subList(from, to);
	}
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(getPages(10, 17)));
		
		List<Integer> list = IntStream.rangeClosed(1, 10)
			.boxed()
			.collect(Collectors.toList());
		
		System.out.println(list);
		list = subList(list, 4, 3);
		System.out.println(list);
	}
}
