/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bayesclassifierfancy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Administrator
 */
public class TrainSampleDataManager {
	//训练目录的路径
	public static String TRAIN_DIR = null;

	/**
	 * 所有单词数统计Map
	 */
	public  static Map<String,Map<String,Integer> > allWordsMap=new HashMap<String,Map<String,Integer> >();
	/**
	 *
	* @Title: readDirs
	* @Description: 递归获取文件
	* @param @param filepath,fileList
	* @param @return List<String>
	* @param @throws FileNotFoundException
	* @param @throws IOException
	* @return List<String>
	* @throws
	 */
    public static List<String> readDirs(String filepath,List<String> fileList) throws FileNotFoundException, IOException {

        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("输入的参数应该为[文件夹名]");
                System.out.println("filepath: " + file.getAbsolutePath());
                fileList.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + File.separator + filelist[i]);
                    if (!readfile.isDirectory()) {
                        fileList.add(readfile.getAbsolutePath());
                    } else if (readfile.isDirectory()) {
                        readDirs(filepath + File.separator + filelist[i],fileList);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public static List<String> readDirs(File file,List<String> fileList) throws FileNotFoundException, IOException {
    	String filePah=file.getAbsolutePath();
    	return readDirs(filePah, fileList);
    }

    /**
     *
    * @Title: readFile
    * @Description: 读取文件转化成string
    * @param @param file
    * @param @return String
    * @param @throws FileNotFoundException
    * @param @throws IOException
    * @return String
    * @throws
     */
	public static String readFile(String file) throws FileNotFoundException, IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader is = new InputStreamReader(new FileInputStream(file), "gbk");
        BufferedReader br = new BufferedReader(is);
        String line = br.readLine();
        while (line != null) {
            sb.append(line).append("\r\n");
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }

	/**
	 * @Title:process
	 * @Description: 对训练文本进行处理
	 * @param none
	 * @return void
	 * @throws 
	 */
	public static void process(){
		try{
			File sampleDataDir = new File(TRAIN_DIR);
			File[] fileList = sampleDataDir.listFiles();
			if(fileList == null){
				throw new IllegalArgumentException("训练目录不存在");
			}
			for(File file : fileList){
				  List<String> classFileList = readDirs(file, new ArrayList<String>());
//				for(String article : classFileList){
//					String content = readFile(article);
//					Map<String, Long> wordsMap = ChineseTokenizer.segStr(content);
//					Set<String> keySet = wordsMap.keySet();
//					if(allWordsMap.containsKey(file.getName())){
//						TreeMap<String, Long> classifierValue = (TreeMap<String, Long>) allWordsMap.get(file.getName());
//						for(String key:keySet){
//							if(classifierValue.containsKey(key)){
//								classifierValue.put(key, classifierValue.get(key) + wordsMap.get(key));
//							}else{
//								classifierValue.put(key, wordsMap.get(key));
//							}
//						}
////						 Set<Entry<String, Long>> set = classifierValue.entrySet();
////						 Iterator<Entry<String, Long>> iterator = set.iterator();
////						 while (iterator.hasNext()) {
////							Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
////						 }
//
//						allWordsMap.put(file.getName(), classifierValue);
//					}else{
//						TreeMap<String, Long> classifierValue = new TreeMap<String, Long>();
//						for(String key:keySet){
//							if(classifierValue.containsKey(key)){
//								classifierValue.put(key, classifierValue.get(key) + wordsMap.get(key));
//							}else{
//								classifierValue.put(key, wordsMap.get(key));
//							}
//						}
//						Set<Entry<String, Long>> set = classifierValue.entrySet();
//						 Iterator<Entry<String, Long>> iterator = set.iterator();
//						 while (iterator.hasNext()) {
//							Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
//						 }
//						allWordsMap.put(file.getName(), classifierValue);
//					}
				Map<String, Integer> classifierValue = new HashMap<String, Integer>();
				for(String article : classFileList){
					String content = readFile(article);
					Map<String, Integer> wordsMap = ChineseTokenizer.segStr(content);
					Set<String> keySet = wordsMap.keySet();
					for(String key:keySet){
						if(classifierValue.containsKey(key)){
							classifierValue.put(key, classifierValue.get(key) + wordsMap.get(key));
						}else{
							classifierValue.put(key, wordsMap.get(key));
						}
					}
				}
				 List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(classifierValue.entrySet());
				Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>(){
					public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
						return (o2.getValue() - o1.getValue());
					}
				});
				Map<String, Integer> classifierValueNew = new LinkedHashMap<String, Integer>();
				for(Map.Entry<String, Integer> in : infoIds){
					classifierValueNew.put(in.getKey(), in.getValue());
				}
				allWordsMap.put(file.getName(), classifierValueNew);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Map.Entry[] getSortedHashtableByValue(Map h){
		Set set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator(){
			public int compare(Object arg0, Object arg1){
				Long key1 = Long.valueOf(((Map.Entry) arg0).getValue().toString());
				Long key2 = Long.valueOf(((Map.Entry) arg1).getValue().toString());
				return key1.compareTo(key2);
			}

		});
		return entries;
	}

}
