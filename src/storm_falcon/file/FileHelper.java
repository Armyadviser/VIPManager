package storm_falcon.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

	/**
	 * �����ļ�
	 * @param strUrl ����url
	 * @param pathToSave ���ر���·��
	 * @return
	 */
	public static boolean download(String strUrl, String pathToSave) {
		try {
			URL url = new URL(strUrl);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			
			File file = createFile(pathToSave);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buffer);
			
			fos.flush();
			fos.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * ���ֽڶ�ȡ�ļ�
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static byte[] readFile(File file) throws IOException {
		long len = file.length();
		byte data[] = new byte[(int) len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(data);
		if (r != len)
			throw new IOException("Only read " + r + " of " + len + " for " + file);
		fin.close();
		return data;
	}
	
	/**
	 * ���ֽڶ�ȡ�ļ�
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	static public byte[] readFile(String filename) throws IOException {
		File file = new File(filename);
		return readFile(file);
	}

	/**
	 * ��byteд���ļ�
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void writeFile(File file, byte[] data) throws IOException {
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(data);
		fout.close();
	}
	
	/**
	 * ��byteд���ļ�
	 * @param filename
	 * @param data
	 * @throws IOException
	 */
	public static void writeFile(String filename, byte data[]) throws IOException {
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(data);
		fout.close();
	}

	/**
	 * ����Ŀ¼
	 * @param dirPath Ŀ��Ŀ¼
	 * @param isFullName �Ƿ�Ϊ����·��
	 * @param suffixes ɸѡ��׺���ļ���ʽ
	 * @return ���з�ϸ�ʽ���ļ���
	 */
	public static List<String> traversalDir(String dirPath, boolean isFullName, String... suffixes) {
		List<String> fileList = new ArrayList<String>();
		File root = new File(dirPath);
		addFileToList(root, fileList, isFullName, suffixes);
		return fileList;
	}

	private static void addFileToList(File dirFile, List<String> fileList, boolean isFullName, String... suffixes) {
		File[] fs = dirFile.listFiles();
		
		for (File file : fs) {
			boolean flag = false;
			String path = file.getAbsolutePath();
			
			//�ж��ļ���ʽ
			if (suffixes.length == 0) {
				flag = true;
			}
			for (String suffix : suffixes) {
				if (path.endsWith(suffix)) {
					flag = true;
					break;
				}
			}
			
			if (flag && !file.isDirectory()) {
				if (isFullName) {
					fileList.add(file.getAbsolutePath());
				} else {
					fileList.add(file.getName());
				}
			} else if (file.isDirectory()) {
				addFileToList(file, fileList, isFullName, suffixes);
			}
		}
	}
	
	public static void createIfNotExist(String filePath) {
		if (new File(filePath).exists()) {
			return;
		}
		createFile(filePath);
	}
	
	public static File createFile(String strFilePath) {
		int endIndex = strFilePath.lastIndexOf("/");
		if (endIndex == -1) {
			endIndex = strFilePath.lastIndexOf("\\");
		}
		String path = strFilePath.substring(0, endIndex);
		File dir = new File(path);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(strFilePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static BufferedWriter openFileBufferedWriterStream(File file, boolean isAppend, String encoding) {
		try {
			return new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(file, isAppend), encoding));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
