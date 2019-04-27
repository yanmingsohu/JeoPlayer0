// CatfoOD 2009-5-13 ионГ07:28:04

package jeo.decoder;

public class DecoderException extends Exception {
	public DecoderException(String s) {
		super(s);
	}
	
	public DecoderException(Exception e) {
		super(e);
	}
	
	public DecoderException() {}
}
