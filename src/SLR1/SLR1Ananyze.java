package SLR1;

import java.util.Stack;


public class SLR1Ananyze {
	String[] inputStr;
	Stack<String> stack;
	SLR1Ananyze(String[] str){
		inputStr = str;
		stack = new Stack();
		stack.push("0");
	}
	public String actionTable(String num, String vt) {
		Table table = new Table();
		int m = 0;
		int n = 0;
		
		for(int i = 0; i < table.n.length; i ++) {
//			System.out.println("num="+num+" n="+table.n[i]);
//			System.out.println(num.equals(table.n[i]));
			if(num.equals(table.n[i])) {
				m = i;
				break;
			}
		}
		for(int i = 0; i < table.VT.length; i ++) {
			if(vt.equals(table.VT[i])) {
				n = i;
				break;
			}	
		}
		if(table.Action[m][n].length() > 0) {
			return table.Action[m][n];
		}else {
			return "false";
		}
	}
	public String gotoTable(String num, String vn) {
		Table table = new Table();
		int m = 0;
		int n = 0;
		
		for(int i = 0; i < table.n.length; i ++) {
			if(num.equals(table.n[i])) {
				m = i;
				break;
			}
		}
		for(int i = 0; i < table.VN.length; i ++) {
			if(vn.equals(table.VN[i])) {
				n = i;
				break;
			}	
		}
		if(table.Goto[m][n].length() > 0) {
			return table.Goto[m][n];
		}else {
			return "false";
		}
	}
	public void analyze() {
		int ip = 0;
		String stackTop;
		String strFirst;
		while(true) {
			stackTop = stack.peek();
			strFirst = inputStr[ip];
			String str = actionTable(stackTop,strFirst);
			System.out.println(str);
			if(str.charAt(0)=='S') {
				stack.push(strFirst);//输入串首
				stack.push(str.substring(1));//S?
				ip ++;
			}else if(str.charAt(0)=='R'){//R
				Part p = new Part();
				int n = Integer.parseInt(str.substring(1));//Reduce 第n条产生式
				int popNum = p.getRightLen(n)*2;
				for(int i = 0; i < popNum; i ++){//弹栈 2*|P右部|
					stack.pop();
				}
				String Sm_r = stack.peek();
				String vn = p.getLeftPart(n);
				stack.push(vn);						//push P左部
				String gotoS = gotoTable(Sm_r, vn); 
				stack.push(gotoS);					//push Goto
				System.out.println(p.getPart(n));
			}else if(actionTable(stackTop,strFirst) == "ACC"){
				System.out.println("结束！");
				return;
			}
				
		}	
	}
	
	
	public static void main(String[] args ){
		// TODO Auto-generated method stub
		String[] str = {"i","=","i","$"};
		SLR1Ananyze slr = new SLR1Ananyze(str);
		slr.analyze();
		
		}
}
class Part{
	String[] part= {"S->i=E","E->E+T","E->E-T","E->T","T->T*F","T->T/F","T->F","F->(E)","F->i"};
	int getRightLen(int n){
		n--;
		return part[n].split("->")[1].length();
	}
	String getLeftPart(int n) {
		n--;
		return part[n].split("->")[0];
	}
	String getPart(int n) {
		n--;
		return part[n];
	}
}
class Table{
	String[] n = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
	String[] VT = {"=","i","+","-","*","/","(",")","$"};
	String[] VN = {"S","E","T","F"};
	String[][] Action= {{""  ,"S2",""  ,""   ,""   ,""   ,""  ,""   ,""   },
						{""  ,""  ,""  ,""   ,""   ,""   ,""  ,""   ,"ACC"},
						{"S3",""  ,""  ,""   ,""   ,""   ,""  ,""   ,""   },
						{""  ,"S7",""  ,""   ,""   ,""   ,"S8",""   ,""   },
						{""  ,""  ,"S9","S11",""   ,""   ,""  ,""   ,"R1" },
						{""  ,""  ,"R7","R7" ,"R7" ,"R7" ,""  ,"R7" ,"R7" },
						{""  ,""  ,"R4","R4" ,"S13","S14",""  ,"R4" ,"R4" },
						{""  ,""  ,"R9","R9" ,"R9" ,"R9" ,""  ,"R9" ,"R9" },
						{""  ,""  ,""  ,""   ,""   ,""   ,""  ,""   ,""   },
						{""  ,""  ,""  ,""   ,""   ,""   ,""  ,""   ,""   },
						{""  ,""  ,"R2","R2" ,"S13","S14",""  ,"R2" ,"R2" },
						{""  ,""  ,""  ,""   ,""   ,""   ,""  ,""   ,""   },
						{""  ,""  ,"R3","R3" ,"S13","S14",""  ,"R3" ,"R3" },
						{""  ,"S7",""  ,""   ,""   ,""   ,"S8",""   ,""   },
						{""  ,"S7",""  ,""   ,""   ,""   ,"S8",""   ,""   },
						{""  ,""  ,"R5","R5" ,"R5" ,"R5" ,""  ,"R5" ,"R5" },
						{""  ,""  ,"R6","R6" ,"R6" ,"R6" ,""  ,"R6" ,"R6" },
						{""  ,""  ,"S9","S11",""   ,""   ,""  ,"S18",""   },
						{""  ,""  ,"R8","R8" ,"R8" ,"R8" ,""  ,"R8" ,"R8" },
					};
		String[][] Goto= {	{"1",""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
							{"" ,"4" ,"6" ,"5" },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
							{"" ,"17","6" ,""  },
							{"" ,""  ,"10","5" },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,"12","5" },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,"15"},
							{"" ,""  ,""  ,"16"},
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
							{"" ,""  ,""  ,""  },
						};
}