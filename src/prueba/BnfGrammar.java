package prueba;
public class BnfGrammar {
 
 
public static void main(String[] args) {
 
s = args.length == 1 ? args[0] : "a=a+b-c*d";
 
if (A() && i == s.length()) {
System.out.println("The string \"" + s + "\" is in the language.");
}
else {
System.out.println("The string \"" + s + "\" is not in the language.");
}
}
 
private static boolean A() {
 
if (I()) {
if (i < s.length() && s.charAt(i) == '=') {
++i;
if (E()) {
 
return true;
 
}
}
}
 
return false;
}
 
private static boolean E() {
 
	if(T()){
		if (i < s.length() && s.charAt(i) == '+') {
			++i;
			if(E()){
 
				return true;
			}
			
		}
	}/*
	else if(T()){
		if (i < s.length() && s.charAt(i) == '-'){
			i++;
			if(E()){
 
				return true;
			}
 
		}
 
	}
	else if(T()){
 
		return true;
	}*/
 
	return false;
}
 
private static boolean T(){
	if (P()){
		if (i < s.length() && s.charAt(i) == '*'){
			i++;
			if(T()){
 
				return true;
			}
		if(i < s.length() && s.charAt(i) == '/'){
			i++;
			if(T()){
	 			return true;
				}
			}
		}
		else{
			i++;
			return true;
		}
	}/*
	else if(P()){
		if(i < s.length() && s.charAt(i) == '/'){
			i++;
			if(T()){
 
				return true;
			}
		}
	}
	else if(P()){
 
		return true;
	}*/

	return false;
}
 
private static boolean P(){
if(I()){
return true;
}
else if (L()){
return true;
}
else if(i < s.length() && s.charAt(i) == '('){
++i;
if (E()){
if (i < s.length() && s.charAt(i) == ')') {
++i;
return true;
}
}
}
 
 
return false;
}
 
private static boolean I() {
 
if (i < s.length() && s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
++i;
return true;
}
 
return false;
}
 
private static boolean L(){
if (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
++i;
return true;
}
 
return false;
 
}
 
 
private static String s;
private static int i;
 
 
 
 
 
 
}