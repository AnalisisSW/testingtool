package testingtool.app;

import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class CodeAnalyzer  {
	
	// voy a tener una instancia de esta clase por cada ARCHIVO que selecciono
	
	private Map<String, String> classMap;
	private Map<String, String> methodMap;
	private double commentPercentage;
	private int codeLines;
	private int commentLines;
	private int cyclomaticComplexity;
	private int fanIn;
	private int fanOut;	
	private int halsteadLength;
	private double halsteadVolume;

	
	public Map<String, String> getClassMap() {		
		return classMap;		
	}
	
	public Map<String, String> getMethodMap() {		
		return methodMap;		
	}
	
	public int getCodeLines() {		
		return codeLines;		
	}
	
	public int getCommentLines() {
		return commentLines;
	}

	public double getCommentPercentage() {
		return commentPercentage;
	}
	
	public int getCyclomaticComplexity() {
		return cyclomaticComplexity;
	}

	public int getFanIn() {
		return fanIn;
	}
	
	public int getFanOut() {
		return fanOut;
	}
	
	public int getHalsteadLength() {
		return halsteadLength;
	}
	
	public double getHalsteadVolume() {
		return halsteadVolume;
	}
	
	public void parseFile(RandomAccessFile file) {
		
		try {
			
			String className = null;
			String line 	 = null;			
			String content   = "";
			
			int openBrackets = 0;			
			int openBracketIndex = -1;
			int closeBrackets = 0;
			int closeBracketIndex = -1;
			int commentIndex = -1;
			
			classMap = new LinkedHashMap<String, String>();
					
			// utilizo expresion regular para matchear nombre de la clase
			Pattern classPattern = Pattern.compile("class\\s+(\\w+)");  
		
			Matcher classMatcher = null;
			
			file.seek(0);

			line = file.readLine();
			
			while (line != null) {
				
				classMatcher = classPattern.matcher(line);
				
				if (classMatcher.find() == true) {
					
					// guardo el nombre de la clase
					className = classMatcher.group(1);
					
					openBrackets = 0;
					closeBrackets = 0;
					content = "";
											
					while ( (openBrackets == 0 && closeBrackets == 0) || (openBrackets != closeBrackets) ) {						
																								
						if (line.contains("{")) {
							
							// me fijo si esta comentada
							if (line.contains("//")) { /** OJO: no estoy teniendo en cuenta si esta dentro de un comentario multilinea **/
							
								// obtengo el indice en el cual se encuentra la llave de apertura
								openBracketIndex = line.indexOf("{");
								
								// obtengo el indice en el cual se encuentra la apertura de comentario
								commentIndex = line.indexOf("//");
								
								// si el indice del comentario esta antes de la llave, entonces esta comentada
								// no la tengo que tener en cuenta
								if (commentIndex < openBracketIndex)
									openBrackets--;
							
							}							
														
							openBrackets++;
							
						} 
						
						if (line.contains("}")) {
							
							// me fijo si esta comentada
							if (line.contains("//")) { /** OJO: no estoy teniendo en cuenta si esta dentro de un comentario multilinea **/
							
								// obtengo el indice en el cual se encuentra la llave de cierre
								closeBracketIndex = line.indexOf("}");
								
								// obtengo el indice en el cual se encuentra la apertura de comentario
								commentIndex = line.indexOf("//");
								
								// si el indice del comentario esta antes de la llave, entonces esta comentada
								// no la tengo que tener en cuenta
								if (commentIndex < closeBracketIndex)
									closeBrackets--;
							
							}							
														
							closeBrackets++;
							
						}
													
						content += line + System.getProperty("line.separator");
						
						line = file.readLine();
						
					} // END while ( (openBrackets == 0 && closeBrackets == 0) || (openBrackets != closeBrackets) )
					
					// guardo la clase leida en el mapa de clases
					classMap.put(className, content);					
					
				} // END if (classMatcher.find() == true)				

				// tengo que seguir leyendo para verificar si encuentro otra clase				
				line = file.readLine();				
				
			} // END while (line != null)			
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error parseando código. Verificar archivo seleccionado.", "ERROR",
					JOptionPane.ERROR_MESSAGE, null);
			
			e.printStackTrace();
			
		}		
		
	}
	
	
	public void parseClass(String selectedClassName) {
		
	try {
			
			String methodName = null;
			String line = null;
			String content   = "";
						
			int openBrackets = 0;			
			int openBracketIndex = -1;
			int closeBrackets = 0;
			int closeBracketIndex = -1;
			int commentIndex = -1;
			int i = 0;
			
			methodMap = new LinkedHashMap<String, String>(); 
			
			String classString = classMap.get(selectedClassName);
		
			// utilizo expresion regular para matchear nombre de metodo
			Pattern methodPattern = Pattern.compile("(public|private|protected|static|final|native|synchronized|abstract|transient|\\s+)\\s+[\\w\\<\\>\\[\\]]+\\s+(\\w+)\\s*\\([^\\)]*\\)\\s*(\\{?|[^;])");  
		
			Matcher methodMatcher = null;

			// divido el string en lineas para poder parsearlo mejor
			String lines[] = classString.split(System.getProperty("line.separator"));
			
			// inicializo indice para recorrer array con las lineas del metodo
			i = 0;
			
			while (i < lines.length) {
				
				line = lines[i];
				
				methodMatcher = methodPattern.matcher(line);
				
				if (methodMatcher.find() == true) {
					
					// guardo el nombre del metodo
					methodName = methodMatcher.group(2);
					
					openBrackets = 0;
					closeBrackets = 0;
					content = "";
											
					while ( (openBrackets == 0 && closeBrackets == 0) || (openBrackets != closeBrackets) ) {
																								
						if (line.contains("{")) {
							
							// me fijo si esta comentada
							if (line.contains("//")) { /** OJO: no estoy teniendo en cuenta si esta dentro de un comentario multilinea **/
							
								// obtengo el indice en el cual se encuentra la llave de apertura
								openBracketIndex = line.indexOf("{");
								
								// obtengo el indice en el cual se encuentra la apertura de comentario
								commentIndex = line.indexOf("//");
								
								// si el indice del comentario esta antes de la llave, entonces esta comentada
								// no la tengo que tener en cuenta
								if (commentIndex < openBracketIndex)
									openBrackets--;
							
							}
														
							openBrackets++;
							
						} 
						
						if (line.contains("}")) {
							
							// me fijo si esta comentada
							if (line.contains("//")) { /** OJO: no estoy teniendo en cuenta si esta dentro de un comentario multilinea **/
							
								// obtengo el indice en el cual se encuentra la llave de cierre
								closeBracketIndex = line.indexOf("}");
								
								// obtengo el indice en el cual se encuentra la apertura de comentario
								commentIndex = line.indexOf("//");
								
								// si el indice del comentario esta antes de la llave, entonces esta comentada
								// no la tengo que tener en cuenta
								if (commentIndex < closeBracketIndex)
									closeBrackets--;
							
							}							
														
							closeBrackets++;
							
						}
													
						content += line + System.getProperty("line.separator");
						
						i++;
						
						line = lines[i];
						
					} // END while (openBrackets != closeBrackets)
					
					// guardo el metodo leido en el mapa
					methodMap.put(methodName, content);
					
				} // END if (methodMatcher.find() == true)
				
				i++;
				
			} // END while (i < lines.length)						
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error parseando código de clase \"" + selectedClassName + "\". Verificar entrada.", "ERROR",
					JOptionPane.ERROR_MESSAGE, null);
			
			e.printStackTrace();
			
		}		
		
		
	}
	
	
	public void analyzeMethod(String selectedMethodName) {
		
		int conditions = 0;
		int comments = 0;
		int fanOutCount = 0;
		int totalOperatorOcurrences = 0;
		int totalOperandOcurrences = 0;		
		
		boolean multiLineCommentOn = false;		
		boolean stillMoreConditions = false;
		boolean commentsOn = false;
		
		// declaro sets para guardar operadores y operandos
		// son sets y no listas porque no permiten repetidos, lo cual me sirve para obtener
		// la cantidad de operadores y operandos UNICOS
		Set<String> operatorsSet = new HashSet<String>();
		Set<String> operandsSet = new HashSet<String>();
		
		String method = methodMap.get(selectedMethodName);
		
		// regex para matchear operadores y keywords (OPERADORES de Halstead)
		Pattern operatorPattern = Pattern.compile("(byte\\s+|short\\s+|int\\s+|long\\s+|float\\s+|double\\s+|boolean\\s+|char\\s+|new|assert|break|case|catch|const|continue|default|\\s+do\\s+|else\\s+|false|try|finally|for|goto|if\\s+|instanceof|while|null|return|static|strictfp|super|switch|this|throw|true|\\s{1}\\+\\s{1}|\\s{1}\\-\\s{1}|\\s{1}\\<\\s{1}|\\s{1}\\>\\s{1}|\\s{1}\\*\\s{1}|\\s{1}\\=\\s{1}|\\s{1}\\/\\s{1}|\\+{2}|\\-{2}|\\={2}|\\!\\=|\\>\\=|\\<\\=|\\+\\=|\\-\\=|\\*\\=|\\/\\=|\\&{2}|\\|{2}|\\{|\\})");
		Matcher operatorMatcher = null;
		
		// regex para matchear numeros (OPERANDOS de Halstead)
		Pattern numberPattern = Pattern.compile("[0-9]+");
		Matcher numberMatcher = null;
		
		// regex para matchear variables (OPERANDOS de Halstead)
		Pattern variablePattern = Pattern.compile("(?:byte|short|int|long|float|double|boolean|char)?[\\w]+(?:\\[[\\s]*\\])?\\s+(\\w+)\\s+=");
		Matcher variableMatcher = null;
	
		// regex para matchear keyword IF
		Pattern ifPattern = Pattern.compile("if\\s*\\(");
		Matcher ifMatcher = null;
		
		// regex para matchear keyword WHILE
		Pattern whilePattern = Pattern.compile("while\\s*\\(");
		Matcher whileMatcher = null;
		
		// regex para matchear keyword CATCH
		Pattern catchPattern = Pattern.compile("catch\\s*\\(");
		Matcher catchMatcher = null;
		
		// regex para matchear los simbolos de OR y AND para contar condiciones
		Pattern conditionPattern = Pattern.compile("[\\||\\&]{2}");
		Matcher conditionMatcher = null;
		
		// regex para matchear llamados a metodos (tambien se utiliza para OPERANDOS de Halstead)
		Pattern methodCallingPattern = Pattern.compile("([a-zA-Z][a-zA-Z0-9_$]+)\\s*\\(\\s*([a-zA-Z0-9_$]*(?:\\s*,\\s*[a-zA-Z0-9_$]+)*)\\s*\\)");
		Matcher methodCallingMatcher = null;
		
		String lines[] = method.split(System.getProperty("line.separator"));
		
		boolean firstLine = true;
		
		for (String line : lines) {
			
			/** COMENTARIOS **/
			
			if (line.contains("/*"))
				multiLineCommentOn = true;

			if (line.contains("*/"))
				multiLineCommentOn = false;					
				
			commentsOn = false;
			if (multiLineCommentOn || line.contains("//")) {				
				commentsOn = true;
				comments++;
			}
			
			
			/** COMPLEJIDAD CICLOMATICA **/
			
			ifMatcher = ifPattern.matcher(line);
			
			whileMatcher = whilePattern.matcher(line);
			
			catchMatcher = catchPattern.matcher(line);
			
			if (commentsOn == false && (stillMoreConditions || ifMatcher.find() == true || whileMatcher.find() == true || catchMatcher.find() == true) ){
				
				// si no hay la misma cantidad de parentesis de apertura y de cierre, quiere decir que
				// el IF esta repartido en varias lineas
				
				long openParentheses = line.chars().filter(ch -> ch == '(').count();
				long closedParentheses = line.chars().filter(ch -> ch == ')').count();
				
				if (stillMoreConditions == true) {
					
					// en el caso de que me encuentre en un IF repartido en varias lineas, solo sabre que 
					// es el final cuando tenga un parentesis de cierre de mas
					if (closedParentheses == openParentheses + 1)
						stillMoreConditions = false;
					
				} else if (openParentheses != closedParentheses) {
					
					stillMoreConditions = true;
				
				} else {
					
					// solo en este caso puedo llegar a tener mas condiciones sobre la misma linea				
					conditionMatcher = conditionPattern.matcher(line);
					
					while (conditionMatcher.find())
						conditions++;	
										
				}
				
				conditions++;
	
			} // END if (commentsOn == false && ...			
						
			
			/** FAN-OUT **/
			
			methodCallingMatcher = methodCallingPattern.matcher(line);
			
			if (commentsOn == false && firstLine == false && methodCallingMatcher.find())
				fanOutCount++;
			
			
			/** HALSTEAD **/
						
			// en la primera linea esta la declaracion del metodo, evito buscar operandos y operadores ahi
			if (commentsOn == false && firstLine == false) { 
				
				/*** HALSTEAD - OPERADORES ***/
				
				operatorMatcher = operatorPattern.matcher(line);				
				while (operatorMatcher.find()) {
					
					totalOperatorOcurrences++;
					operatorsSet.add(operatorMatcher.group());
					
				}
				
				methodCallingMatcher = methodCallingPattern.matcher(line);			
				if (methodCallingMatcher.find()) {
					
					totalOperatorOcurrences++;
					operatorsSet.add(methodCallingMatcher.group(1));
					
				}
				

				/*** HALSTEAD - OPERANDOS ***/
			
				numberMatcher = numberPattern.matcher(line);
				while (numberMatcher.find()) {
					
					totalOperandOcurrences++;
					operandsSet.add(numberMatcher.group());
					
				}
									
				variableMatcher = variablePattern.matcher(line);
				if (variableMatcher.find()) {
					
					totalOperandOcurrences++;
					operandsSet.add(variableMatcher.group(1));
					
				}
					
			} // END if (commentsOn == false && !firstLine) {

			
			if (firstLine)
				firstLine = false;
			
		} // END for (String line : lines)				
		
		codeLines = lines.length;
		
		commentLines = comments;
		
		commentPercentage = ((double)commentLines/codeLines) * 100;
		
		cyclomaticComplexity = conditions + 1;
		
		/** FAN-IN **/
		
		// para obtener el fan-in, tengo que recorrer todos los demas metodos de la clase en busca del nombre del metodo seleccionado
		
		fanIn = getFanInCount(selectedMethodName);
		
		fanOut = fanOutCount;
		
		halsteadLength = totalOperatorOcurrences + totalOperandOcurrences;
		
		int n = operatorsSet.size() + operandsSet.size();
		
		halsteadVolume = halsteadLength * (Math.log(n)/Math.log(2));
		
	}

	private int getFanInCount(String methodName) {
		
		Pattern methodCallingPattern = Pattern.compile("([a-zA-Z][a-zA-Z0-9_$]+)\\s*\\(\\s*([a-zA-Z0-9_$]*(?:\\s*,\\s*[a-zA-Z0-9_$]+)*)\\s*\\)");
		Matcher methodCallingMatcher = null;
		
		int count = 0;
		
		for (Map.Entry<String, String> entry : methodMap.entrySet()) {
			
			// si no es el propio metodo que estoy analizando...			
			if (entry.getKey().equals(methodName) == false) {
				
				// divido el metodo en lineas, es decir, lo vuelvo a su forma original en el archivo 
				// esto hace que sea mas facil encontrar un llamado al metodo que estoy analizando
		
				String lines[] = entry.getValue().split(System.getProperty("line.separator"));
				
				for (String line : lines) {
					
					methodCallingMatcher = methodCallingPattern.matcher(line);
					
					// si matchea con la regex de llamados a metodos y ademas esa linea contiene
					// el nombre del metodo en cuestion, entonces incremento el contador
					
					if (methodCallingMatcher.find() && line.contains(methodName))
						count++;
					
				} // END for (String line : lines)
												
			} // END if (entry.getKey().equals(methodName) == false)
						
		} // END for (Map.Entry<String, String> entry : methodMap.entrySet())
		
		return count;
				
	}


}
