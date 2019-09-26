package prueba;

/**
 * A recursive descent parser.  Recognizes strings that can be produced by
 * the grammar
 *
 * S -> W | $ tail1
 * tail1 -> S | e
 * W -> a tail2
 * tail2 -> b b | W b b
 *
 * which was obtained by left factoring the following grammar to make it LL(1).
 *
 * S -> $ | W | $ S
 * W -> a b b | a W b b
 *
 * (Carrano & Prichard, Data Abstraction and Problem Solving with Java)
 */

public class P2
{
    // Throughout these methods, toParse is the input string left
    // to parse.
    // 
    // The match method is used when we think we know what terminal
    // character is coming.  For example, when we expand using rule
    // tail2 -> W b b, after we complete the parsing of the W part we
    // expect to see "bb" as the next two characters of input.  The
    // method removes the matched character from the input string, or
    // displays an error message and leaves the input unchanged if the
    // charatcer does not match (perhaps it should throw an exception
    // instead).
    //
    // If we are in method parseXXX, XXX represents the nonterminal
    // that we would be trying to expand if we were constructing the parse
    // tree by hand.  The parse tree (at least the nonterminals therein)
    // is equivalent to the recursion tree, so the parse tree can
    // be built by keeping track of which methods call which other methods.
    //
    // Groups of case statements inside the parseXXX methods correspond
    // to the predict sets for the rules of the grammar.
    //
    // The code given below assumes that a period ('.') has been added to the
    // end of the input.  This simply makes the code to check for the
    // end of input easier: instead of checking if toParse.length == 0,
    // we can use a case '.' in the switch statements.

    private static String parseS(StringBuffer toParse)
    {
	switch (toParse.charAt(0))
	    {
	    case '$':
		match(toParse, '$');
		return "(S $ " + parseTail1(toParse) + ")";
		
	    case 'a':
		return "(S " + parseW(toParse) + ")";

	    default:
		System.err.println("parse error");
		return "error";
	    }
    }

    private static String parseTail1(StringBuffer toParse)
    {
	switch (toParse.charAt(0))
	    {
	    case '.':
		return "(tail1)";
		
	    case 'a':
	    case '$':
		return "(tail1 " + parseS(toParse) + ")";

	    default:
		System.err.println("parse error");
		return "error";
	    }
    }

    private static String parseW(StringBuffer toParse)
    {
	match(toParse, 'a');
	return "(W a " + parseTail2(toParse) + ")";
    }

    private static String parseTail2(StringBuffer toParse)
    {
	switch (toParse.charAt(0))
	    {
	    case 'a':
		String wPart = parseW(toParse);
		match(toParse, 'b');
		match(toParse, 'b');
		return "(tail2 " + wPart + " b b)";

	    case 'b':
		match(toParse, 'b');
		match(toParse, 'b');
		return "(tail2 b b)";

	    default:
		System.err.println("parse error");
		return "error";
	    }
    }

    private static void match(StringBuffer s, char c)
    {
	if (s.length() > 0 && s.charAt(0) == c)
	    {
		s.deleteCharAt(0);
	    }
	else
	    {
		System.err.println("parse error " + s + " " + c);
	    }
    }

    public static void main(String[] args)
    {
	StringBuffer buf = new StringBuffer("ab"+ ".");

	String parseTree = parseS(buf);
	match(buf, '.');

	System.out.println(parseTree);
		System.out.println();
    }
}