package LL1;

import java.util.Stack;

public class LL1Analyze {
	String inputStr;
	Stack<Character> stack;
	LL1Analyze(String str){
		inputStr = str;
		stack = new Stack();
		stack.push('$');
		stack.push('E');
	}
	public boolean isVT(char str) {
		PredictiveTable table = new PredictiveTable();
		for(char s : table.VT) {
			if(s == str) {
				return true;
			}
		}
		return false;
	}
	public String isInTable(char vn,char vt){
		PredictiveTable table = new PredictiveTable();
		int m = 0;
		int n = 0;
		
		for(int i = 0; i < table.VN.length; i ++) {
			if(vn == table.VN[i]) {
				m = i;
				break;
			}
		}
		for(int i = 0; i < table.VT.length; i ++) {
			if(vt == table.VT[i]) {
				n = i;
				break;
			}	
		}
		if(table.predictTable[m][n].length() > 0) {
			return table.predictTable[m][n];
		}else {
			return "false";
		}
	}
	public void analyze(){
		int ip = 0;
		char stackTop;
		char strFirst;
		while(!stack.empty()) {
			stackTop = stack.peek();
			strFirst = inputStr.charAt(ip);
//			String  vt = stack.peek();
			if(stack.peek() == '#') {//µ¯³ö¿Õ´®
				stack.pop();
			}else {
				if(isVT(stack.peek())) {//Õ»¶¥ÊÇVT
					if(stackTop == strFirst) {
						System.out.println("Æ¥Åä" + stackTop);
						stack.pop();
						ip ++;
					}else {//´íÎó3 Õ»¶¥ÖÕ½á·ûÓëÊäÈë·û²»Í¬
						System.out.println("µ¯³öÕ»¶¥ÖÕ½á·û");
						stack.pop();
					}
				}else {//Õ»¶¥ÊÇVN
					String part = isInTable(stackTop,strFirst);
					if(part!="false"){
						stack.pop();
						if(part != "synch") {
							String rightPart = part.split("->")[1];
							for(int i = rightPart.length()-1; i >= 0 ;i --) {//ÓÒ²¿µ¹ÐòÈëÕ»
								stack.push(rightPart.charAt(i));
							}
							System.out.println(part);//Êä³ö²úÉúÊ½
						}else {//´íÎó2 ²éµ½synch
							System.out.println("ÒÑµ¯³öÕ»¶¥A");
							stack.pop();
						}	
					}else {//´íÎó1 ²éÑ¯·ÖÎö±íÎª¿Õ
						System.out.println("Ìø¹ýÊäÈë·ûºÅa");
						ip ++;
					}
				}
			}
		}
		System.out.println("·ÖÎöÍê±Ï£¡");
	}
	
	public static void main(String[] args ){
		// TODO Auto-generated method stub		
		LL1Analyze LL1 = new LL1Analyze("i*(i+i)$");
//		System.out.println(LL1.stack.peek());
		LL1.analyze();

		
	
		}
}
 class PredictiveTable{
	char[] VN = {'E','e','T','t','F','A','M'};
	char[] VT = {'(',')','+','-','*','/','i','$'};
	String[][] predictTable = {	{"E->Te" ,"synch",""      ,""      ,""      ,""      ,"E->Te","synch"},
								{""      ,"e->#" ,"e->ATe","e->ATe",""      ,""      ,""     ,"e->#" },
								{"T->Ft" ,"synch",""      ,""      ,""      ,""      ,"T->Ft","synch"},
								{""      ,"t->#" ,"t->#"  ,"t->#"  ,"t->MFt","t->MFt",""     ,"t->#" },
								{"F->(E)","synch",""      ,""      ,""      ,""      ,"F->i" ,"synch"},
								{"synch" ,""     ,"A->+"  ,"A->-"  ,""      ,""      ,"synch",""     },
								{"synch" ,""     ,""      ,""      ,"M->*"  ,"M->/"  ,"synch",""     }
							};
	
}