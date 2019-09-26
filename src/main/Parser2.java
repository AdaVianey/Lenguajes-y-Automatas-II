package main;

import java.util.ArrayList;

public class Parser2 {

	ArrayList<Componente> compo;
	Componente cp;
	String salida = "";
	public static String salida2 ="";
	private int idx = 0;
	private int contador = 0,conIF = 0,conDV = 0;
	private boolean imprime,avanza = false;
	private ArrayList<Identificador> ide;
	public Parser2(ArrayList<Componente >c){
		compo = c;
		cp = compo.get(idx);
		ide = new ArrayList<>();
	}
	
	public String Sintactico(){
		salida2 = "";
		CD();
		return salida;
	}
	/*private void Acomodar(int tipo ,String to,String s){
		//salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+s+"\n-------------------------------------------\n";
		if(cp.getTipo() == tipo && cp.getToken().matches(to)){
			if(idx < compo.size() - 1) idx++;
			try {
				cp = compo.get(idx);
			} catch (IndexOutOfBoundsException e) {
				idx--;
				Componente caux = compo.get(idx);
				cp = new Componente(19, "", caux.getColumna(), caux.getFila());
				error(tipo,s);
			}
		}else{
			error(tipo,s);
			/*if(idx < compo.size() - 1) idx++;
			try {
				cp = compo.get(idx);
			} catch (IndexOutOfBoundsException e) {
				idx--;
				Componente caux = compo.get(idx);
				cp = new Componente(19, "", caux.getColumna(), caux.getFila());
				error(tipo,s);
			}
		}
	}*/
	private void Acomodar(int tipo ,String s){
		if(cp.getTipo() == tipo && cp.getToken().equals(s)){
			Avanza();
		}else{
			error(tipo,s);
		}
	}
	private void Avanza(){
		salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+cp.getToken()+"\n-------------------------------------------\n";
		if(idx < compo.size() - 1) idx++;
		try {
			cp = compo.get(idx);
		} catch (IndexOutOfBoundsException e) {
			idx--;
			Componente caux = compo.get(idx);
			cp = new Componente(19, "", caux.getColumna(), caux.getFila());
			//error(tipo,s);
		}
	}
	private void error(int t,String to){
		switch (t) {
		case Componente.PR:
			switch (to) {
			case "class":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"class\"\t"+cp.getToken()+"\n";
				break;
			case "if":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"if\"\t"+cp.getToken()+"\n";
				break;
			case "while":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"while\"\t"+cp.getToken()+"\n";
				break;
			}
			break;
		case Componente.SE:
			switch (to) {
			case "{":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case "}":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case "(":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case ")":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			case ";":
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \""+to+"\"\t"+cp.getToken()+"\n";	
				break;
			default:
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un Simbolo especial\t"+cp.getToken()+"\n";
				break;
			}
			break;
		case Componente.OP:
			if(to.equals("arit"))
				salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un operador aritmetico\t"+cp.getToken()+"\n";
			else
			break;
		case Componente.TIPO:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"int\" o \"boolean\"\t"+cp.getToken()+"\n";
			break;
		case Componente.MOD:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un \"public\" o \"private\"\t"+cp.getToken()+"\n";
			break;
		case Componente.DIG:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se espeba un digito\t"+cp.getToken()+"\n";
			break;
		case Componente.VAL:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba \"true\" o \"false\"\t"+cp.getToken()+"\n";
			break;
		case Componente.ID:
			salida +="Error Sintactico, Fila: "+cp.getFila()+" se esperaba un identificador\t"+cp.getToken()+"\n";
			break;
		}
		salida2 += "Token obtenido:"+cp.getToken()+"\n"+"Token Esperado: "+to+"\n-------------------------------------------\n";
		
	}
	private void CD(){
		Componente c = cp,cs = compo.get(idx + 1);
		if(!c.getToken().equals("class")){
			M();
		}
		c = cp;
		Acomodar(Componente.PR,"class");
			
		c = cp;
		ID();
		c = cp;
		Acomodar(Componente.SE,"{");
		
		//-----------------FD
		c = cp;
		//if(c.getTipo() == Componente.MOD || c.getTipo() == Componente.TIPO )
		FD();
		//-----------------S
		S();
		Acomodar(Componente.SE,"}");
	}
	private void FD(){
		Componente c = cp;
		if(c.getTipo() == Componente.MOD || c.getTipo() == Componente.TIPO){
			VDN();
			c = cp;
			Acomodar(Componente.SE,";");
		}
	}
	private void VDN(){
		conDV++;
		Componente c= null ,caux = null;
		c = cp;
		if(c.getTipo() != Componente.TIPO)
			M();
		T();
		ID();
		c = cp;
		if(c.getToken().equals("=")){
			Avanza();
			VDR();
		}
		contador ++;
	}
	private void VDR(){
		Componente c,cauxa;
		c = cp;
		if(c.getTipo() == Componente.DIG)
			IL();
		else if(c.getTipo() == Componente.VAL)
			BL();
	}
	private void E(){
		TE();
	}
	private void TE(){
		Componente c = null,caux = null;
		c = cp;
		if(c.getTipo() == Componente.DIG)
			IL();
		else
			ID();
		c = cp;
		if(c.getToken().matches("[>|<|>=|<=|==|!=]"))
			Avanza();
		else
			error(Componente.OP,"log");
		c = cp;
		if(c.getTipo() == Componente.DIG)
			IL();
		else
			ID();
	}
	private void S(){
		Componente c = null,caux = null;
		c = cp;
		if(c.getToken().equals("if")){
			Avanza();
			IS();
		}else if(c.getToken().equals("while")){
			Avanza();
			WS();
		}else if(c.getTipo() == Componente.MOD || c.getTipo() == Componente.TIPO){
			VDN();
		}
	}
	private void WS(){
		Componente c=null,caux = null,cauxa = null;
		c = cp;
		Acomodar(Componente.SE,"(");
		E();
		Acomodar(Componente.SE,")");
		Acomodar(Componente.SE,"{");
		S();
		Acomodar(Componente.SE,"}");
	}
	private void IS(){
		Componente c;
		c = cp;
		Acomodar(Componente.SE,"(");
		E();
		Acomodar(Componente.SE,")");
		Acomodar(Componente.SE,"{");
		AE();
		Acomodar(Componente.SE,"}");
		
		S();
	}
	private void T(){
		TS();
	}
	private void TS(){
		Componente c = null, caux = null;
		c = cp;
		//if(c.getToken().matches("(int|boolean)"))
		if(c.getToken().equals("int"))
			Avanza();
		else if(c.getToken().equals("boolean"))
			Avanza();
		else
			error(Componente.TIPO, "");
	}
	private void M(){
		Componente c = null,caux = null;
		c = cp;
		if(c.getToken().equals("public")) 
			Avanza();
		else if(c.getToken().equals("private")) 
			Avanza();
		else
			error(Componente.MOD, "");
	}
	private void IL(){
		Acomodar(Componente.DIG, cp.getToken());
	}
	private void BL(){
		Componente c;
		Avanza();
	}
	private void ID(){
		Componente c,caux,cauxa;
		String men = "";
		c = cp;
		men = cp.getToken();
		Acomodar(Componente.ID,c.getToken());
		men = cp.getToken();
	}
	private void AE(){
		Componente c;
		c = cp;
		ID();
		
		Acomodar(Componente.SE,"=");
		
		IL();
		c = cp;
		if(c.getToken().matches("[\\+|-|/|\\*]"))
			Avanza();
		else
			error(Componente.OP, "arit");
		IL();
		
		Acomodar(Componente.SE,";");
	}
	private int contador(String t){
		int c = 0;
		/*Nodo<Componente> aux = componentes.inicio();
		while(aux != null){
			if(aux.valor.getToken().equals(t))
				c++;
			aux = aux.sig;
			
		}*/
		return c;
	}
	public ArrayList<Identificador> r(){
		return ide;
	}
}
