package se.sogeti.umea.configuration;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class JndiPrinter {

	private final Context context;

	private StringBuffer buf = new StringBuffer();

	public JndiPrinter(Context context) {
		this.context = context;
	}

	public String getJndiTree() {
		buf = new StringBuffer();
		printJNDITree("");
		return buf.toString();
	}
	
	private void printJNDITree(String ct) {
		try {			
			printNE(context.list(ct), ct);
		} catch (NamingException e) {
			// ignore leaf node exception
		}		
	}

	private void printNE(@SuppressWarnings("rawtypes") NamingEnumeration ne,
			String parentctx) throws NamingException {
		while (ne.hasMoreElements()) {
			NameClassPair next = (NameClassPair) ne.nextElement();
			printEntry(next);
			increaseIndent();
			printJNDITree((parentctx.length() == 0) ? next.getName()
					: parentctx + "/" + next.getName());
			decreaseIndent();
		}
	}

	private void printEntry(NameClassPair next) {
		try {
			System.out.println(printIndent() + "-->" + next);
			buf.append(printHtmlIndent()).append("-->").append(next).append("<br/>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int indentLevel = 0;

	private void increaseIndent() {
		indentLevel += 4;
	}

	private void decreaseIndent() {
		indentLevel -= 4;
	}

	private String printHtmlIndent() {
		StringBuffer buf = new StringBuffer(indentLevel);
		for (int i = 0; i < indentLevel; i++) {
			buf.append("&nbsp;");
		}
		return buf.toString();
	}

	private String printIndent() {
		StringBuffer buf = new StringBuffer(indentLevel);
		for (int i = 0; i < indentLevel; i++) {
			buf.append(" ");
		}
		return buf.toString();
	}

}
