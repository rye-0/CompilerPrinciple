package lexicalAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class Analyze {
	String[] inputLines;
	String[] keyWord= {"begin","end","if","then","else","for","while","do","and","or","not",
						"switch","case","int"};
	
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
				||str.charAt(0)=='_') {//��һ��Ϊ�»��߻���ĸ
			for(int i = 0; i < str.length(); i ++) {
				 if((str.charAt(i)>='0'&&str.charAt(i)<='9')
				 	||(str.charAt(0)>='a'&&str.charAt(0)<='z')
					||(str.charAt(0)>='A'&& str.charAt(0)<='Z') 
					||str.charAt(0)=='_') {
					 continue;
				 }else {
					 return false;
				 }
			}
			return true;
		}else {	
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
	
	public String determineType(String str, int j) throws IOException {
//		System.out.println(str);
		if(Character.isDigit(str.charAt(0))) {//����
			if(isNum(str)) {
				System.out.println("< "+ str +" , digit>");
				return "< "+ str +" , digit>";
			}else {
				System.out.println("��"+(j+1)+"�� ���ִ���");
				return "��"+(j+1)+"�� ���ִ���";
			}
		}else {
			if(isID(str)) {
				if(isKeyWord(str)) {//keyword
					System.out.println("< "+ str +" , key word>");
					return "< "+ str +" , key word>";
				}else {//ID
					System.out.println("< "+ str +" , ID>");
					return "< "+ str +" , ID>";
				}
			}else {
				System.out.println("��"+(j+1)+"��ID����");
				return "��"+(j+1)+"��ID����";
			}
		}
	}
	public String scan(String str, int j) throws IOException{
//		System.out.println(str);
		WriteFile w = new WriteFile();
		if(str.length()>0) {//��ֹ�մ�
			int temp = 0;
			String sTemp;
			for(int i = 0; i < str.length(); i ++) {
				switch(str.charAt(i)) {
					case '*':
					case '/':
					case '=':
						sTemp =str.substring(temp,i) ;
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						System.out.println("< "+str.charAt(i)+" , �����>");
						w.write("< "+str.charAt(i)+" , �����>");
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
					case ','://��Ŀ�����
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						System.out.println("< "+str.charAt(i)+" , �ֽ��>");
						w.write("< "+str.charAt(i)+" , �ֽ��>");
						temp = i + 1 ;
						break;
					case '#':
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						System.out.println("< # , ע�ͷ�>");
						w.write(" # , ע�ͷ�>");
						sTemp =str.substring(i+1,str.length());
						return " "+ sTemp;
					case '+':
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						if(i+1 > str.length()) {//��ֹ����Խ��
							if(str.charAt(i+1) == '+') {//˫Ŀ++								
								System.out.println("< ++ " + ", �����>");
								w.write("< ++ " + ", �����>");
								i ++;
							}else {//��Ŀ+
								System.out.println("< + ��" + ", �����>");
								w.write("< + ��" + ", �����>");
							}	
						}
						temp = i + 1;
						break;
					case '-'://--				
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '-') {//˫Ŀ--								
								System.out.println("< -- " + ", �����>");
								w.write("< -- " + ", �����>");
								i ++;
							}else {//��Ŀ-
								System.out.println("< - ��" + ", �����>");
								w.write("< - ��" + ", �����>");
							}	
						}
						temp = i + 1;
						break;
					case '>':
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '=') {//˫Ŀ>=								
								System.out.println("< >= " + ", �����>");
								w.write("< >= " + ", �����>");
								i ++;
							}else {//��Ŀ>
								System.out.println("< > ��" + ", �ָ���>");
								w.write("< > ��" + ", �ָ���>");
							}	
						}
						temp = i + 1;
						break;
					case '<'://<= //<>
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '=') {//˫Ŀ<=								
								System.out.println("< <= " + ", �����>");
								w.write("< <= " + ", �����>");
								i ++;
							}else if(str.charAt(i+1) == '=') {//˫Ŀ<>								
								System.out.println("< <> " + ", �����>");
								w.write("< <> " + ", �����>");
								i ++;
							}else{//��Ŀ<
								System.out.println("< < ��" + ", �ָ���>");
								w.write("< < ��" + ", �ָ���>");
							}	
						}
						temp = i + 1;
						break;
					case ':'://:=
						sTemp =str.substring(temp,i);
						if(sTemp.length() > 0) {
							w.write(determineType(sTemp, j));
						}
						if(i+1 > str.length()) {
							if(str.charAt(i+1) == '=') {//˫Ŀ:=								
								System.out.println("< := " + ", �����>");
								w.write("< := " + ", �����>");
								i ++;
							}else {
								System.out.println("< : ��" + ", �ָ���>");
								w.write("< : ��" + ", �ָ���>");
							}	
						}
						temp = i + 1;
						break;
				}		
			}
			if(temp == 0) {//�÷��Ŵ�û��������ͷָ���
				w.write(determineType(str, j));;
			}
		}
		w.close();
		return "";
	}
	public static void remark(String str) throws IOException {
		
		WriteFile w = new WriteFile();
		w.write("<"+ str +",ע������ >");
		System.out.println("<"+ str +",ע������ >");
		w.close();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub		
		ReadFile readFile = new ReadFile();
		Analyze analyze = new Analyze(readFile.lines);
		
		for(int i = 0; i < analyze.inputLines.length; i ++) {
//			System.out.println(analyze.inputLines[i]);
			String lines = analyze.inputLines[i].replaceAll("\\s+", " ");//��ÿ�пհ��ÿո����
			String [] symbolStr = lines.split(" ");
//			System.out.println("��"+(i+1)+"��");
			String annotation = null;
			for(int j = 0; j < symbolStr.length; j ++) {//ĳ�е�ĳ�����Ŵ�
//				System.out.print(symbolStr[j]+" ");
				annotation = analyze.scan(symbolStr[j], i);
				if(annotation.length()>0) {
					for(int k = j+1; k < symbolStr.length; k ++) {
						annotation +=symbolStr[k];
					}
					remark(annotation);
					break;
				}
			}
			
		}
	}
}
class ReadFile{
	public String[] lines;
	ReadFile() {//���ж�ȡinput�ļ�                
		BufferedReader reader = null;
		try {
			FileReader fileReader = new FileReader("input.txt");//��ȡ�ļ�����
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            lineNumberReader.skip(Long.MAX_VALUE);
            int nums = lineNumberReader.getLineNumber() + 1;
            fileReader.close();
            lineNumberReader.close();
            lines = new String[nums];
            
            reader = new BufferedReader(new FileReader("input.txt"));
			String tempString;
			int line = 0;
		    while ((tempString = reader.readLine()) != null){//BufferedReader readLine()���ж�ȡ		    	
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
	WriteFile() {
		try {
			output = new BufferedWriter(new FileWriter("output.txt",true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void write(String type) throws IOException {
		output.write(type);
		output.newLine();
	}
	public void close() throws IOException {
		output.flush();
		output.close();
	}
}
