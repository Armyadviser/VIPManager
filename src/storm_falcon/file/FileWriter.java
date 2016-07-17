package storm_falcon.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileWriter {

	private File mFile = null;
	
	private BufferedWriter mWriter = null;
	
	private final static String LINE_SEP = System.getProperty("line.seprator", "\n");
	
	public boolean open(String strFilePath) {
		mFile = new File(strFilePath);
		if (!mFile.exists()) {
			mFile = FileHelper.createFile(strFilePath);
		}
		
		try {
			mWriter = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(mFile), "GBK"));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean open(String strFilePath, String encoding) {
		mFile = new File(strFilePath);
		if (!mFile.exists()) {
			mFile = FileHelper.createFile(strFilePath);
		}
		
		try {
			mWriter = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(mFile), encoding));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public synchronized void writeLine(String line) {
		try {
			mWriter.write(line + LINE_SEP);
		} catch (IOException e) {}
	}
	
	public void close() {
		try {
			mWriter.flush();
			mWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void writeAll(String strFilePath, String encoding, List<T> list) {
		FileWriter writer = new FileWriter();
		writer.open(strFilePath, encoding);
		list.stream()
			.map(Object::toString)
			.forEach(writer::writeLine);
		writer.close();
	}
}
