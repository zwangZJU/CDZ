package utils;

import org.apache.commons.lang3.StringUtils;


public class WordFilterTest {
	public static void main(String[] args) {
		String[] keywords = new String[] { "中国", "越南", "日本", "投诉","天朝" };
		FilterService filterService = new FilterService(keywords);
		String str = filterService.filter("test中国人民共和国，参与越南战争，日本XXXXXXX，头投诉没人理会啦。天朝人");
		String str_=filterService.filterKeyWord("test中国人民共和国，参与越南战争，日本XXXXXXX，头投诉没人理会啦。天朝人");
		System.out.println(StringUtils.substringBeforeLast(str_, ","));
		System.out.println(str);
	}
}
