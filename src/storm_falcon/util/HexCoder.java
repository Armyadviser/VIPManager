package storm_falcon.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HexCoder {
	
	public static String hexDecode(String str) {
		Hex hex = new Hex();
		try {
			byte[] decode = hex.decode(str.getBytes("UTF-8"));
			return new String(decode, "UTF-8");
		} catch (DecoderException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String hexEncode(String str) {
		Hex hex = new Hex();
		byte[] encode;
		try {
			encode = hex.encode(str.getBytes("UTF-8"));
			return new String(encode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		String url = "http://130.91.101.28:8085/sms/Send?DestTermID=15502472959&MsgContent=";
		String content = "（勿删）测试乱码01--三更车库";
		
		content = hexEncode(content);
		System.out.println(content);
		
		System.out.println(hexDecode(content));
		System.out.println(url + content);
	}
}
