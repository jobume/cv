package se.sogeti.umea.cvconverter.application;

/**
 * Exception that signals an error with transformation from one media to
 * another. A ConversionException always has a source exception.
 * 
 * @author joparo
 */
public class ConversionException extends Exception {
	
	private static final long serialVersionUID = -4892815048919377659L;
	
	public ConversionException(Exception source){
		super(source);		
	}
	
	public ConversionException(String message, Exception source){
		super(message, source);		
	}

}
