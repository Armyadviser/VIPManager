package storm_falcon.bean;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import storm_falcon.file.FileReader;
import storm_falcon.file.FileWriter;

public abstract class BaseManager<T extends BaseVO> {

	protected String filePath;
	
	protected String mMaxNo;//最大序列号
	
	protected List<T> mDataList;
	
	protected BaseManager(String fileName) {
		String basePath = System.getProperty("user.dir") + "/";
		filePath = basePath + fileName;
		loadDataFile();
	}
	
	private void loadDataFile() {
		mDataList = FileReader.mapForEach(filePath, "UTF-8", fileParser())
				.collect(Collectors.toList());
		
		try {
			mMaxNo = formatNo(mDataList.size());
		} catch (Exception e) {
			mMaxNo = "0000";
		}
	}
	
	/**
	 * 数据文件的行解析器
	 * @return
	 */
	protected abstract BiFunction<Integer, String, T> fileParser();
	
	private String formatNo(int no) {
		String sNo = String.valueOf(no);
		int time = 4 - sNo.length();
		for (int i = 0; i < time; i++) {
			sNo = "0" + sNo;
		}
		return sNo;
	}
	
	public String getNextNo() {
		int maxNo = Integer.parseInt(mMaxNo);
		maxNo++;
		mMaxNo = formatNo(maxNo);
		return mMaxNo;
	}
	
	public void add(T t) {
		mDataList.add(t);
		flush();
	}
	
	public void modifyByNo(String no, T t) {
		mDataList = mDataList.stream()
			.filter(item -> !item.no.equals(no))
			.collect(Collectors.toList());
		mDataList.add(t);
	}
	
	public void delete(String no) {
		mDataList = mDataList.stream()
			.map(t -> {
				if (no.equals(t.no)) {
					t.delsign = 0;
				}
				return t;
			}).collect(Collectors.toList());
		flush();
	}
	
	public List<T> search(String keyword) {
		Collections.sort(mDataList, (t1, t2) -> t1.no.compareTo(t2.no));
		return mDataList.stream()
			.filter(t -> t.getKeyword().contains(keyword))
			.collect(Collectors.toList());
	}
	
	public T getByNo(String no) {
		return mDataList.stream()
			.filter(item -> item.no.equals(no))
			.findFirst()
			.get();
	}
	
	public void flush() {
		FileWriter.writeAll(filePath, "UTF-8", mDataList);
	}
	
	public void reload() {
		loadDataFile();
	}
	
}
