package org.jboss.monitoring.GridControl.managers;

/**
 * 
 * @author cvarga
 *
 */
public class CacheManagerException extends Throwable{
	//TODO Implement a better exception manager
	
	/**
	 *  
	 */
	private static final long serialVersionUID = 2997515606813388968L;
	
	private String 		_errorMessage;
	private Throwable 	_ex;
	
	public CacheManagerException(String message)
	{
		super(message);
		this._errorMessage = message;
	}
	
	public CacheManagerException(Throwable ex)
	{
		super(ex);
		this._ex = ex;
	}
	
	public CacheManagerException(String message, Throwable ex)
	{
		super(message,ex);
		this._ex = ex;
		this._errorMessage = message;
		
	}
	
	@Override
	public String getMessage() {
		
		String errorMessage = (_errorMessage == null ? "Exception" : _errorMessage);
		
		if(_ex != null)
			errorMessage = errorMessage + " - Cause:" + _ex.getMessage();
		
		if(errorMessage == null || errorMessage.isEmpty())
			return "CacheManager exception";
		
		return errorMessage;
				
	}
	
}
