/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bayesclassifierfancy;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 *
 * @author Administrator
 */
public class ChineseTokenizer {


	public static Map<String,Integer> segStr (String content){
		//分词
		Reader input = new StringReader(content);
		//智能分词关闭（对分词的精度影响很大）
		IKSegmenter iks = new IKSegmenter(input, true);
		Lexeme lexeme = null;
		Map<String, Integer> words = new LinkedHashMap<String, Integer>();
		try{
			while((lexeme = iks.next())!=null){
				//计算文章中的单个词在文章中出现的次数
				if(words.containsKey(lexeme.getLexemeText())){
					words.put(lexeme.getLexemeText(), words.get(lexeme.getLexemeText())+1);
				}else{
					words.put(lexeme.getLexemeText(), 1);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return words;
	}
}
