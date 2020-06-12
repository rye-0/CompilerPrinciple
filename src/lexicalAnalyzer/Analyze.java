package lexicalAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class Analyze {
	String[] inputLines;
	String[] keyWord= {"begin","end","if","then","else","for","while","do","and","or","not"};
	
	Analyze (String[] lines) {
		inputLines = lines;
	}
	
	public boolean  isNum(String str){
		for(int i = 0; i < str.length(); i ++) {
			if (!Character.isDigit(str.charAt(i))){
			           return false;
			}
		}
		return true;
	}
	
	public boolean isID(String str){
		if((str.charAt(0)>='a'&&str.charAt(0)<='z')
				||(str.charAt(0)>='A'&& str.charAt(0)<='Z') 
				||str.charAt(0)=='_') {//第一个为下划线或字母
			for(int i = 0; i < str.length(); i ++) {
				 if((str.charAt(i)>='0'&&str.charAt(i)<='9')
				 	||(str.charAt(0)>='a'&&str.charAt(0)<='z')
					||(str.charAt(0)>='A'&& str.charAt(0)<='Z') 
					||str.charAt(0)=='_') {
					 continue;
				 }else {
					 System.out.println("不是ID");
					 return false;
				 }
			}
			return true;
		}else {	
			System.out.println("第一个字母不是_字母出错！");
			return false;
		}	
	}
	
	public boolean  isKeyWord(String str){
		for(int i = 0; i < keyWord.length; i ++) {
			if (keyWord[i] == str){
				return true;
			}
		}
		return false;
	}
	
	public void determineType(String str) throws IOException {
//		System.out.println(str);
		if(Character.isDigit(str.charAt(0))) {//数字
			if(isNum(str)) {
				System.out.println("< "+ str +" , digit>");
				new WriteFile(String.valueOf(str),"digital");
				return;
			}else {
				System.out.println("数字错误！");
				return;
			}
		}else {
			if(isID(str)) {
				if(isKeyWord(str)) {//keyword
					System.out.println("< "+ str +" , key word>");
					new WriteFile(String.valueOf(str),"key word");
					return;
				}else {//ID
					System.out.println("< "+ str +" , ID>");
					new WriteFile(String.valueOf(str),"ID");
					return;
				}
			}else {
				System.out.println("ID错误！");
				return;
			}
		}
	}
	public void scan(String str) throws IOException{
//		System.out.println(str);
		if(str.length()>0) {//防止空串
			int temp = 0;
			String sTemp;
			for(int i = 0; i < str.length(); i ++) {
				switch(str.charAt(i)) {
					case '*':
					case '/':
					case '=':
						sTemp =str.substring(temp,i) ;
						if(sTemp.length() > 0) {
							determineType(sTemp);
						}
						System.out.println("< "+str.charAt(i)+" , 运算符>");
						new WriteFile(String.valueOf(str.charAt(i)),"运算符");
						temp = i + 1;
						break;
					case '[':
					case ']':
					case '{':
					case '}':
					case '(':
					case ')':
					case ';':
					case '\'':
					case '\"':
					case ','://单目运算符
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							determineType(sTemp);
						}
						System.out.println("< "+str.charAt(i)+" , 分界符>");
						new WriteFile(String.valueOf(str.charAt(i)),"分界符");
						temp = i + 1 ;
						break;
					case '+':
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							determineType(sTemp);
						}
						if(i+1 > str.length()) {//防止数组越界
							if(str.charAt(i+1) == '+') {//双目++								
								System.out.println("< ++ " + ", 运算符>");
								new WriteFile(String.valueOf("++"),"运算符");
								i ++;
							}else {//单目+
								System.out.println("< + ，" + ", 运算符>");
								new WriteFile(String.valueOf("++"),"运算符");
							}	
						}
						temp = i + 1;
						break;
					case '-'://--				
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							determineType(sTemp);
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '-') {//双目--								
								System.out.println("< -- " + ", 运算符>");
								new WriteFile(String.valueOf("--"),"运算符");
								i ++;
							}else {//单目-
								System.out.println("< - ，" + ", 运算符>");
								new WriteFile(String.valueOf("-"),"运算符");
							}	
						}
						temp = i + 1;
						break;
					case '>':
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							determineType(sTemp);
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '=') {//双目>=								
								System.out.println("< >= " + ", 运算符>");
								new WriteFile(String.valueOf(">="),"运算符");
								i ++;
							}else {//单目>
								System.out.println("< > ，" + ", 分隔符>");
								new WriteFile(String.valueOf(">"),"运算符");
							}	
						}
						temp = i + 1;
						break;
					case '<'://<= //<>
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							determineType(sTemp);
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '=') {//双目<=								
								System.out.println("< <= " + ", 运算符>");
								new WriteFile(String.valueOf("<="),"运算符");
								i ++;
							}else if(str.charAt(i+1) == '=') {//双目<>								
								System.out.println("< <> " + ", 运算符>");
								new WriteFile(String.valueOf("<>"),"运算符");
								i ++;
							}else{//单目<
								System.out.println("< < ，" + ", 分隔符>");
								new WriteFile(String.valueOf("<"),"分隔符");
							}	
						}
						temp = i + 1;
						break;
					case ':'://:=
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							determineType(sTemp);
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '=') {//双目:=								
								System.out.println("< := " + ", 运算符>");
								new WriteFile(String.valueOf(":="),"运算符");
								i ++;
							}else {
								System.out.println("< : ，" + ", 分隔符>");
								new WriteFile(String.valueOf(":"),"分隔符");
							}	
						}
						temp = i + 1;
						break;
				}		
			}
			if(temp == 0) {//该符号串没有运算符和分隔符
				determineType(str);
			}			
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub		
		ReadFile readFile = new ReadFile();
		Analyze analyze = new Analyze(readFile.lines);
//		analyze.writeFile'
		
		for(int i = 0; i < analyze.inputLines.length; i ++) {
//			System.out.println(analyze.inputLines[i]);
			String lines = analyze.inputLines[i].replaceAll("\\s+", " ");//将每行空白用空格代替
			String [] symbolStr = lines.split(" ");
//			System.out.println("第"+(i+1)+"行");
			for(int j = 0; j < symbolStr.length; j ++) {//某行的某个符号串
//				System.out.print(symbolStr[j]+" ");
				analyze.scan(symbolStr[j]);
			}
		}
	}
}
class ReadFile{
	public String[] lines;
	ReadFile() {//按行读取input文件                
		BufferedReader reader = null;
		try {
			FileReader fileReader = new FileReader("input.txt");//获取文件行数
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            lineNumberReader.skip(Long.MAX_VALUE);
            int nums = lineNumberReader.getLineNumber() + 1;
            fileReader.close();
            lineNumberReader.close();
            lines = new String[nums];
            
            reader = new BufferedReader(new FileReader("input.txt"));
			String tempString;
			int line = 0;
		    while ((tempString = reader.readLine()) != null){//BufferedReader readLine()按行读取		    	
		 	    lines[line] = tempString;
		    	line++;
		    }
		}catch(IOException e){
	        e.printStackTrace();
	    }finally{
	        if(reader != null){
	            try{
	                reader.close();
	            }catch(IOException e){
	            }
	        }
	    }
	}
}

class WriteFile{
	BufferedWriter output;
	WriteFile(String str,String type) {
		try {
			output = new BufferedWriter(new FileWriter("output.txt",true ));
			output.write("< "+ str +" , "+ type +  " >");
			output.newLine();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
