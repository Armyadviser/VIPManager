package storm_falcon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Storm_Falcon on 2015/10/10.
 * Some method do help to convert string.
 */
public class StringHelper {

    /**
     * 截取字符串
     * @param source 源字符串
     * @param sBegin 开头
     * @param sEnd 结尾
     * @param count 第几次匹配
     * @return 第count次匹配，sBegin和sEnd中间的内容
     */
    public static String getContent(String source, String sBegin, String sEnd, int count) {
        //截取开头和结尾坐标
        int nBeginIndex = 0, nEndIndex;

        for (int i = 0; i < count; i++) {
            nBeginIndex = source.indexOf(sBegin, nBeginIndex + 1);
        }

        nBeginIndex += sBegin.length();

        nEndIndex = source.indexOf(sEnd, nBeginIndex + 1);

        try {
            return source.substring(nBeginIndex, nEndIndex);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("data error,source:" + source + ",nBeginIndex:" + nBeginIndex + ",nEndIndex:" + nEndIndex);
            return "";
        }
    }

    /**
     * 去掉所有空格
     * @param source
     * @return
     */
    public static String trim(String source) {
        return source.replaceAll(" ", "");
    }

    /**
     * 将连续多个空格变成1个空格
     * @param source
     * @return
     */
    public static String cutSpace(String source) {
        return source.replaceAll("  ", " ");
    }

	/**
	 * 生成随机串，由数字和字母组合
	 * @param length 长度
	 * @return
	 */
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int charType = random.nextInt(2);
			
			//判断生成的数字类型
			switch (charType) {
			case 0://字母
				//大小写
				int caseType = random.nextInt(2);
				int ch = random.nextInt(26);
				if (caseType == 0) {//大写
					ch = ch + 65;
				} else {//小写
					ch = ch + 97;
				}
				sb.append((char) ch);
				break;
			case 1://数字
				int num = random.nextInt(10);
				sb.append(num);
				break;
			}
		}
		return sb.toString();
	}

    /**
     * 获取指定位置后一个字符
     * @param source
     * @param index
     * @return
     */
    public static char getNextChar(String source, int index) {
        if (index >= source.length()) {
            return '\0';
        }
        return source.charAt(index + 1);
    }

    /**
     * 获取指定位置前一个字符
     * @param source
     * @param index
     * @return
     */
    public static char getPrevChar(String source, int index) {
        if (index <= 1) {
            return '\0';
        }
        return source.charAt(index - 1);
    }

    public static String[] split(String source, String delim) {
        List<String> list = new ArrayList<>();
        int nIndex = source.indexOf(delim);
        while (nIndex != -1) {
            list.add(source.substring(0, nIndex));
            source = source.substring(nIndex + 1);
            nIndex = source.indexOf(delim);
        }
        list.add(source);

        String[] res = new String[list.size()];
        list.toArray(res);
        return res;
    }

    public static void main(String[] args) {
        int i = 0;
        while (i++ < 100) {
            System.out.println(generateRandomString(5));
        }
    }
}
